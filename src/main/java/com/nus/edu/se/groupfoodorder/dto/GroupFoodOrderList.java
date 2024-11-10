package com.nus.edu.se.groupfoodorder.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GroupFoodOrderList {

    private UUID groupFoodOrderId;

    Date deliveryTime;

    Date orderTime;

    String orderStatus;

    String restaurantName;

    String restaurantId;

    private String location;

    private String rating;

    private String imgUrl;

    private String deliveryLocation;

    private String deliveryAddress;

    private String deliveryLatitude;

    private String deliveryLongitude;

    String restaurantAddress;

    String restaurantLatitude;

    String restaurantLongitude;

    public UUID getGroupFoodOrderId() {
        return groupFoodOrderId;
    }

    public void setGroupFoodOrderId(UUID groupFoodOrderId) {
        this.groupFoodOrderId = groupFoodOrderId;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public String getRestaurantLatitude() {
        return restaurantLatitude;
    }

    public void setRestaurantLatitude(String restaurantLatitude) {
        this.restaurantLatitude = restaurantLatitude;
    }

    public String getRestaurantLongitude() {
        return restaurantLongitude;
    }

    public void setRestaurantLongitude(String restaurantLongitude) {
        this.restaurantLongitude = restaurantLongitude;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryLatitude() {
        return deliveryLatitude;
    }

    public void setDeliveryLatitude(String deliveryLatitude) {
        this.deliveryLatitude = deliveryLatitude;
    }

    public String getDeliveryLongitude() {
        return deliveryLongitude;
    }

    public void setDeliveryLongitude(String deliveryLongitude) {
        this.deliveryLongitude = deliveryLongitude;
    }
}
