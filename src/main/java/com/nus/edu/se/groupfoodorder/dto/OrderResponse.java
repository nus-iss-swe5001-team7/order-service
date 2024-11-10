package com.nus.edu.se.groupfoodorder.dto;

import com.nus.edu.se.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String id;
    private String groupFoodOrderId;
    private String restaurantId;
    private String userId;
    private Date createdTime;
    private String orderDetails;
    private String location;
    private String deliveryAddress;
    private String deliveryLatitude;
    private String deliveryLongitude;
    private float deliveryFee;
    private Order.PaymentStatus paymentStatus;
}
