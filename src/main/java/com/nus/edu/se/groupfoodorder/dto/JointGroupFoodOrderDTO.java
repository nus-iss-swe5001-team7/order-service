package com.nus.edu.se.groupfoodorder.dto;

public class JointGroupFoodOrderDTO {
    private String status;
    private String restaurantId;
    private String deliveryLocation;
    private Integer numberOfUsers;
    private float groupOrderDeliveryFee;
    private String mainOrderId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDeliveryLocation() {
        return deliveryLocation;
    }

    public void setDeliveryLocation(String deliveryLocation) {
        this.deliveryLocation = deliveryLocation;
    }

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public float getGroupOrderDeliveryFee() {
        return groupOrderDeliveryFee;
    }

    public void setGroupOrderDeliveryFee(float groupOrderDeliveryFee) {
        this.groupOrderDeliveryFee = groupOrderDeliveryFee;
    }

    public String getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(String mainOrderId) {
        this.mainOrderId = mainOrderId;
    }
}
