package com.nus.edu.se.groupfoodorder.dto;

import com.nus.edu.se.order.dto.OrderDetailDTO;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupFoodOrderResponse {

    private UUID orderId;

    private UUID groupFoodOrderId;

    List<OrderDetailDTO> orderDetailDtoList;

    Date deliveryTime;

    Date orderTime;

    String orderStatus;

    float totalPrice;

    //restaurantLocation
    private String location;

    private float rating;

    private String deliveryLocation;

    private float deliveryFee;

    private String restaurantName;

    private List<String> orderIdsList;

    private String deliveryAddress;

    private String deliveryLatitude;

    private String deliveryLongitude;

    private String restaurantAddress;

    private String restaurantLatitude;

    private String restaurantLongitude;

}
