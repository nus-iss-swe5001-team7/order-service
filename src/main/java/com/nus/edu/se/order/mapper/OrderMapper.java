package com.nus.edu.se.order.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.groupfoodorder.dto.OrderResponse;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public OrderResponse fromOrderToOrderDTO(Order order, List<OrderDetail> orderDetailList, String token) throws JsonProcessingException {
        OrderResponse orderDTO = new OrderResponse();
        orderDTO.setId(order.getId().toString());
        orderDTO.setGroupFoodOrderId(order.getGroupFoodOrder().getId().toString());
        orderDTO.setRestaurantId(order.getRestaurantId());
        orderDTO.setUserId(order.getUserId().toString());
        orderDTO.setCreatedTime(order.getCreatedTime());
        orderDTO.setOrderDetails(covertOrderDetailsToJson(orderDetailList, token));
        orderDTO.setLocation(order.getGroupFoodOrder().getDeliveryLocation());
        orderDTO.setDeliveryFee(order.getDeliveryFee());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        return orderDTO;
    }

    public String covertOrderDetailsToJson(List<OrderDetail> orderDetails, String token) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails, token));
    }

}