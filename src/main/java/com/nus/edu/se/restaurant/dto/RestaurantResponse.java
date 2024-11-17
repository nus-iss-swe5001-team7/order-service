package com.nus.edu.se.restaurant.dto;

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
    private String id;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantLatitude;
    private String restaurantLongitude;
    private String cuisineType;
    private String location;
    private float rating;
    private String restaurantImgURL;

}
