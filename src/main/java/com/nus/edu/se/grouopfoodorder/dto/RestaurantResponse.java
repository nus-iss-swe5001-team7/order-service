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
public class RestaurantResponse {
    private UUID id;
    private String restaurantName;
    private String cuisineType;
    private String location;
    private float rating;
    private String restaurantImgURL;

}
