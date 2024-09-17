package com.nus.edu.se.groupfoodorder.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.exception.AuthenticationException;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.dto.*;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import com.nus.edu.se.order.dao.OrderDetailRepository;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.mapper.OrderDetailMapper;
import com.nus.edu.se.order.mapper.OrderMapper;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    private static final String SERVICE_NAME = "CreateGroupFoodOrder";

    @PostMapping("/groupFoodOrder")
//    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackCreateGroupFoodOrder")
    public ResponseEntity<OrderResponse> createGroupFoodOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
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
        UsersResponse usersResponse = groupOrdersService.getUserById(UUID.fromString(orderRequest.getUserId()));

        // call restaurant-service
        RestaurantResponse restaurantResponse = groupOrdersService.getRestaurantById(UUID.fromString(orderRequest.getRestaurantId()));
        Order order = new Order();
        order.setRestaurantId(restaurantResponse.getId());
        order.setGroupFoodOrder(groupFoodOrder);
        order.setCreatedTime(new Date());
        order.setUserId(usersResponse.getId());
        order.setDeliveryFee(orderRequest.getDeliveryFee());
        order = orderRepository.save(order);


        List<OrderDetailResponse> orderDetailArray = saveOrderDetails(orderRequest.getOrderDetails(), order);

        return ResponseEntity.ok(orderMapper.fromOrderToOrderDTO(order, orderDetailArray));
    }

    public ResponseEntity<OrderResponse> fallbackCreateGroupFoodOrder(OrderRequest orderRequest, Throwable throwable) {
        // Handle the fallback logic here
        // For example, return a default response or an error message
        OrderResponse fallbackResponse = new OrderResponse();
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackResponse);
    }

    private List<OrderDetailResponse> saveOrderDetails(String jsonString, Order order) {
        if (jsonString == null) {
            throw new IllegalArgumentException("Order details content cannot be null");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderDetailResponse> orderDetailList = new ArrayList<>();

        try {
            OrderDetailResponse[] orderDetails = objectMapper.readValue(jsonString, OrderDetailResponse[].class);

            for (OrderDetailResponse orderDetailDTO : orderDetails) {
                OrderDetail orderDetail = orderDetailMapper.fromOrderDetailDTOToOrderDetail(orderDetailDTO, order);
                orderDetailRepository.save(orderDetail);
                orderDetailList.add(new OrderDetailResponse());
            }

            return orderDetailList;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @GetMapping("/groupOrders")
//    public List<?> findAllByUserId(@RequestParam(value = "parameter") String userId) {
//        UUID userUUID = UUID.fromString(userId);
//        UsersModel usersModel = new UsersModel();
//        usersModel.setId(userUUID);
//        List<GroupFoodOrderResponse> groupOrders = groupOrdersService.getGroupOrderListByUserId(usersModel);
//
//        return groupOrders;
//    }
}
