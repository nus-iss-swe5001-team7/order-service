package com.nus.edu.se.groupfoodorder.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.exception.AuthenticationException;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.exception.ServiceNotAvailableException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.*;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrderFilterStrategy;
import com.nus.edu.se.groupfoodorder.service.GroupOrderFilterStrategyFactory;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import com.nus.edu.se.groupfoodorder.service.JwtTokenInterface;
import com.nus.edu.se.groupfoodorder.service.orderstatus.OrderStatusService;
import com.nus.edu.se.order.dao.OrderDetailRepository;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.dto.OrderDetailDTO;
import com.nus.edu.se.order.mapper.OrderDetailMapper;
import com.nus.edu.se.order.mapper.OrderMapper;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import com.nus.edu.se.restaurant.service.RestaurantService;
import com.nus.edu.se.user.dto.UserRole;
import com.nus.edu.se.user.dto.UsersResponse;
import com.nus.edu.se.user.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("groupFoodOrdersAPI")
public class GroupOrderController {

    @Autowired
    private GroupOrderRepository groupOrderRepository;
    @Autowired
    private GroupOrdersService groupOrdersService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private GroupOrderFilterStrategyFactory strategyFactory;
    @Autowired
    JwtTokenInterface jwtTokenInterface;

    @PostMapping("/groupFoodOrder")
    public ResponseEntity<OrderResponse> createGroupFoodOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) throws JsonProcessingException, AuthenticationException, ServiceNotAvailableException{
        String token = groupOrdersService.resolveToken(request);
        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {

            GroupFoodOrder groupFoodOrder = new GroupFoodOrder();
            if (orderRequest.getGroupFoodOrderId()!= null) {
                groupFoodOrder = groupOrderRepository.findById(UUID.fromString(orderRequest.getGroupFoodOrderId())).orElseThrow(() -> new DataNotFoundException("Group Order Not Found."));
                if (groupOrdersService.groupFoodOrderNotValidToJoin(groupFoodOrder)) {
                    throw new AuthenticationException("Group Order not valid to join, please join other group orders!");
                }
            } else {
                groupFoodOrder.setDeliveryFee(orderRequest.getDeliveryFee());
                groupFoodOrder.setDeliveryLocation(orderRequest.getLocation());
                groupFoodOrder = groupOrderRepository.save(groupFoodOrder);
            }

            // call user-service
            UsersResponse usersResponse = usersService.getUserById(UUID.fromString(orderRequest.getUserId()), token);

            // call restaurant-service
            try {
                RestaurantResponse restaurantResponse = restaurantService.getRestaurantById(orderRequest.getRestaurantId(), token);

                Order order = new Order();
                order.setRestaurantId(restaurantResponse.getId());
                order.setGroupFoodOrder(groupFoodOrder);
                order.setCreatedTime(new Date());
                order.setUserId(usersResponse.getUserId());
                order.setDeliveryFee(orderRequest.getDeliveryFee());
                order = orderRepository.save(order);

                List<OrderDetail> orderDetailArray = saveOrderDetails(orderRequest.getOrderDetails(), order, token);

                return ResponseEntity.ok(orderMapper.fromOrderToOrderDTO(order, orderDetailArray, token));
            }catch (ServiceNotAvailableException e) {
                throw new ServiceNotAvailableException("Restaurant service is currently unavailable. Please try again later.", e);
            }
        } else {
            throw new AuthenticationException("User is not authenticated to createGroupFoodOrder!");
        }
    }

    private List<OrderDetail> saveOrderDetails(String jsonString, Order order, String token) throws AuthenticationException {
        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {

            if (jsonString == null) {
                throw new IllegalArgumentException("Order details content cannot be null");
            }
            ObjectMapper objectMapper = new ObjectMapper();
            List<OrderDetail> orderDetailList = new ArrayList<>();

            try {
                OrderDetailDTO[] orderDetails = objectMapper.readValue(jsonString, OrderDetailDTO[].class);

                for (OrderDetailDTO orderDetailDTO : orderDetails) {
                    OrderDetail orderDetail = orderDetailMapper.fromOrderDetailDTOToOrderDetail(orderDetailDTO, order,token);
                    orderDetailRepository.save(orderDetail);
                    orderDetailList.add(orderDetail);
                }

                return orderDetailList;

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new AuthenticationException("User is not authenticated to saveOrderDetails!");
        }
    }

    @GetMapping("/getAllGroupOrders")
    public ResponseEntity<?> getAllGroupOrders(HttpServletRequest request) throws AuthenticationException {
        String token = groupOrdersService.resolveToken(request);
        List<GroupFoodOrderList> orders = groupOrdersService.getAllGroupOrders(token);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/getAllPendingJoinGroupOrders")
    public ResponseEntity<List<GroupFoodOrderList>> getAllGroupFoodOrder(@RequestParam UUID userId, HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {
        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            UserRole userRole = usersService.getUserRoleByUserId(userId, token);

            // Verify the user is authorized as customer before proceeding
            if (!UserRole.CUSTOMER.equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            List<GroupFoodOrderList> allOrders = groupOrdersService.getAllGroupOrders(userId.toString(), token);
            GroupOrderFilterStrategy strategy = strategyFactory.getStrategy(userRole);
            List<GroupFoodOrderList> filteredOrders = strategy.filter(allOrders, null);
            return ResponseEntity.ok(filteredOrders);
        } else {
            throw new AuthenticationException("User is not authenticated to getAllPendingJoinGroupOrders!");
        }
    }

    @GetMapping("/groupOrders")
    public List<?> findAllByUserId(@RequestParam(value = "parameter") String userId, HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {
        String token = groupOrdersService.resolveToken(request);

        UUID userUUID = UUID.fromString(userId);
        UsersResponse usersResponse = new UsersResponse();
        usersResponse.setUserId(userUUID);
        List<GroupFoodOrderResponse> groupOrders = groupOrdersService.getGroupOrderListByUserId(usersResponse, token);

        return groupOrders;
    }

    @GetMapping("/getInfoForGroupOrder")
    public ResponseEntity<JointGroupFoodOrderDTO> getInfoForGroupOrder(@RequestParam UUID groupOrderId, HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {
        String token = groupOrdersService.resolveToken(request);

        return groupOrdersService.getInfoForGroupOrder(groupOrderId, token);
    }


    @GetMapping("/getOrdersForDeliveryStaff")
    public ResponseEntity<List<GroupFoodOrderList>> getOrdersForDeliveryStaff(@RequestParam UUID userId, @RequestParam String location, HttpServletRequest request) throws AuthenticationException {
        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            UserRole userRole = usersService.getUserRoleByUserId(userId, token);

            // Ensure the user is authorized as delivery staff before proceeding
            if (!UserRole.DELIVERY_STAFF.equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            } else {
                List<GroupFoodOrderList> allOrders = groupOrdersService.getAllGroupOrders(token);
                GroupOrderFilterStrategy strategy = strategyFactory.getStrategy(UserRole.DELIVERY_STAFF);
                List<GroupFoodOrderList> filteredOrders = strategy.filter(allOrders, location);
                return ResponseEntity.ok(filteredOrders);
            }
        } else {
            throw new AuthenticationException("User is not authenticated to getAllPendingJoinGroupOrders!");
        }
    }

    @GetMapping("/getOrdersForRestaurantStaff")
    public ResponseEntity<List<GroupFoodOrderList>> getOrdersForRestaurantStaff(@RequestParam UUID userId, @RequestParam String restaurantId, HttpServletRequest request) throws AuthenticationException {
        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            UserRole userRole = usersService.getUserRoleByUserId(userId, token);

            // Verify the user is authorized as restaurant staff before proceeding
            if (!UserRole.RESTAURANT_STAFF.equals(userRole)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            } else {
                List<GroupFoodOrderList> allOrders = groupOrdersService.getAllGroupOrders(token);
                GroupOrderFilterStrategy strategy = strategyFactory.getStrategy(UserRole.RESTAURANT_STAFF);
                List<GroupFoodOrderList> filteredOrders = strategy.filter(allOrders, restaurantId);
                return ResponseEntity.ok(filteredOrders);
            }
        } else {
            throw new AuthenticationException("User is not authenticated to getAllPendingJoinGroupOrders!");
        }
    }

    @PutMapping("/submittedToRestaurant/{orderId}")
    public ResponseEntity updateStatusToSubmittedToRestaurant(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.submittedToRestaurant(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToSubmittedToRestaurant Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

    @PutMapping("/orderCancel/{orderId}")
    public ResponseEntity updateStatusToOrderCancel(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.orderCancel(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToOrderCancel Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

    @PutMapping("/kitchenPreparing/{orderId}")
    public ResponseEntity updateStatusToKitchenPreparing(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.kitchenPreparing(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToKitchenPreparing Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

    @PutMapping("/readyForDelivery/{orderId}")
    public ResponseEntity updateStatusToReadyForDelivery(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.readyForDelivery(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToReadyForDelivery Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

    @PutMapping("/onDelivered/{orderId}")
    public ResponseEntity updateStatusToOnDelivery(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.onDelivery(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToOnDelivery Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

    @PutMapping("/delivered/{orderId}")
    public ResponseEntity updateStatusToDelivered(@PathVariable("orderId") String orderId,HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {

        String token = groupOrdersService.resolveToken(request);

        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {
            try {
                orderStatusService.delivered(orderId);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (Exception e) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("fail_message", "updateStatusToReadyForDelivery Not found GroupOrder with orderId");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to submitted order To Restaurant!");
        }
    }

}
