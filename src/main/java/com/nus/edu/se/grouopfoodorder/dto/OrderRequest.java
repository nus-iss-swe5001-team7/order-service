package com.nus.edu.se.grouopfoodorder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest{
    private String userId;
    private String groupFoodOrderId;
    private String restaurantId;
    private String location;
    private float deliveryFee;
    private String orderDetails;
}
