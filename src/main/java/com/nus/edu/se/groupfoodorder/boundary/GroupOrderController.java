package com.nus.edu.se.groupfoodorder.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.exception.AuthenticationException;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderResponse;
import com.nus.edu.se.groupfoodorder.dto.OrderRequest;
import com.nus.edu.se.groupfoodorder.dto.OrderResponse;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrderFilterStrategy;
import com.nus.edu.se.groupfoodorder.service.GroupOrderFilterStrategyFactory;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import com.nus.edu.se.groupfoodorder.service.JwtTokenInterface;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private UsersService usersService;
    @Autowired
    private GroupOrderFilterStrategyFactory strategyFactory;
//    private static final String SERVICE_NAME = "CreateGroupFoodOrder";
    @Autowired
    JwtTokenInterface jwtTokenInterface;

    @PostMapping("/groupFoodOrder")
//    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackCreateGroupFoodOrder")
    public ResponseEntity<OrderResponse> createGroupFoodOrder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) throws JsonProcessingException, org.apache.tomcat.websocket.AuthenticationException {
        String token = groupOrdersService.resolveToken(request);
        if (Boolean.TRUE.equals(jwtTokenInterface.validateToken(token).getBody())) {

            GroupFoodOrder groupFoodOrder = new GroupFoodOrder();
            if (orderRequest.getGroupFoodOrderId()!= null) {
                groupFoodOrder = groupOrderRepository.findById(UUID.fromString(orderRequest.getGroupFoodOrderId())).orElseThrow(() -> new DataNotFoundException("Group Order Not Found."));
                if (groupOrdersService.groupFoodOrderNotValidToJoin(groupFoodOrder)) {
                    throw new AuthenticationException("Group Order not valid to join, please join other group orders!");
                }
            } else {
                groupFoodOrder.setGroupOrderCreateTime(new Date());
                groupFoodOrder.setDeliveryFee(orderRequest.getDeliveryFee());
                groupFoodOrder.setDeliveryLocation(orderRequest.getLocation());
                groupFoodOrder = groupOrderRepository.save(groupFoodOrder);
            }

            // call user-service
            UsersResponse usersResponse = usersService.getUserById(UUID.fromString(orderRequest.getUserId()), token);

            // call restaurant-service
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
        } else {
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to createGroupFoodOrder!");
        }
    }

//    public ResponseEntity<OrderResponse> fallbackCreateGroupFoodOrder(OrderRequest orderRequest, Throwable throwable) {
//        // Handle the fallback logic here
//        // For example, return a default response or an error message
//        OrderResponse fallbackResponse = new OrderResponse();
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackResponse);
//    }

    private List<OrderDetail> saveOrderDetails(String jsonString, Order order, String token) throws org.apache.tomcat.websocket.AuthenticationException {
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
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to saveOrderDetails!");
        }
    }

    @GetMapping("/getAllGroupOrders")
    public ResponseEntity<?> getAllGroupOrders(HttpServletRequest request) throws org.apache.tomcat.websocket.AuthenticationException {
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
            throw new org.apache.tomcat.websocket.AuthenticationException("User is not authenticated to getAllPendingJoinGroupOrders!");
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
}
