package com.nus.edu.se.groupfoodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest{
    private String id;
    private String userId;
    private String groupFoodOrderId;
    private String restaurantId;
    private String location;
    private String deliveryAddress;
    private String deliveryLatitude;
    private String deliveryLongitude;
    private float deliveryFee;
    private String orderDetails;
    private String createdTime;
}
