package com.nus.edu.se.order.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.grouopfoodorder.dto.OrderDetailResponse;
import com.nus.edu.se.grouopfoodorder.dto.OrderResponse;
import com.nus.edu.se.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    public OrderResponse fromOrderToOrderDTO(Order order, List<OrderDetailResponse> orderDetailList) throws JsonProcessingException {
        OrderResponse orderDTO = new OrderResponse();
        orderDTO.setId(order.getId().toString());
        orderDTO.setGroupFoodOrderId(order.getGroupFoodOrder().getId().toString());
        orderDTO.setRestaurantId(order.getRestaurantId().toString());
        orderDTO.setUserId(order.getUserId().toString());
        orderDTO.setCreatedTime(order.getCreatedTime());
        orderDTO.setOrderDetails(covertOrderDetailsToJson(orderDetailList));
        orderDTO.setLocation(order.getGroupFoodOrder().getDeliveryLocation());
        orderDTO.setDeliveryFee(order.getDeliveryFee());
        orderDTO.setPaymentStatus(order.getPaymentStatus());
        return orderDTO;
    }

    public String covertOrderDetailsToJson(List<OrderDetailResponse> orderDetails) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails));
    }

}