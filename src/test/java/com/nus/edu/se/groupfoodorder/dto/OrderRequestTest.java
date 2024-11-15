package com.nus.edu.se.groupfoodorder.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderRequestTest {

    private OrderRequest orderRequest;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
    }

    @Test
    void testDefaultConstructor() {
        // Verify that the default constructor initializes all fields to null or default values
        assertNull(orderRequest.getId());
        assertNull(orderRequest.getUserId());
        assertNull(orderRequest.getGroupFoodOrderId());
        assertNull(orderRequest.getRestaurantId());
        assertNull(orderRequest.getLocation());
        assertNull(orderRequest.getDeliveryAddress());
        assertNull(orderRequest.getDeliveryLatitude());
        assertNull(orderRequest.getDeliveryLongitude());
        assertEquals(0.0f, orderRequest.getDeliveryFee());
        assertNull(orderRequest.getOrderDetails());
        assertNull(orderRequest.getCreatedTime());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String id = "order123";
        String userId = "user123";
        String groupFoodOrderId = "groupOrder123";
        String restaurantId = "restaurant123";
        String location = "City Center";
        String deliveryAddress = "123 Main Street";
        String deliveryLatitude = "1.2345";
        String deliveryLongitude = "103.5678";
        float deliveryFee = 15.0f;
        String orderDetails = "Order details as JSON";
        String createdTime = "2024-11-15T15:30:00";

        // Act
        OrderRequest orderRequest = new OrderRequest(
                id, userId, groupFoodOrderId, restaurantId, location,
                deliveryAddress, deliveryLatitude, deliveryLongitude,
                deliveryFee, orderDetails, createdTime
        );

        // Assert
        assertEquals(id, orderRequest.getId());
        assertEquals(userId, orderRequest.getUserId());
        assertEquals(groupFoodOrderId, orderRequest.getGroupFoodOrderId());
        assertEquals(restaurantId, orderRequest.getRestaurantId());
        assertEquals(location, orderRequest.getLocation());
        assertEquals(deliveryAddress, orderRequest.getDeliveryAddress());
        assertEquals(deliveryLatitude, orderRequest.getDeliveryLatitude());
        assertEquals(deliveryLongitude, orderRequest.getDeliveryLongitude());
        assertEquals(deliveryFee, orderRequest.getDeliveryFee());
        assertEquals(orderDetails, orderRequest.getOrderDetails());
        assertEquals(createdTime, orderRequest.getCreatedTime());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        String id = "order456";
        String userId = "user456";
        String groupFoodOrderId = "groupOrder456";
        String restaurantId = "restaurant456";
        String location = "Suburban Area";
        String deliveryAddress = "456 Elm Street";
        String deliveryLatitude = "2.3456";
        String deliveryLongitude = "104.6789";
        float deliveryFee = 20.0f;
        String orderDetails = "Another order details as JSON";
        String createdTime = "2024-11-15T16:30:00";

        // Act
        orderRequest.setId(id);
        orderRequest.setUserId(userId);
        orderRequest.setGroupFoodOrderId(groupFoodOrderId);
        orderRequest.setRestaurantId(restaurantId);
        orderRequest.setLocation(location);
        orderRequest.setDeliveryAddress(deliveryAddress);
        orderRequest.setDeliveryLatitude(deliveryLatitude);
        orderRequest.setDeliveryLongitude(deliveryLongitude);
        orderRequest.setDeliveryFee(deliveryFee);
        orderRequest.setOrderDetails(orderDetails);
        orderRequest.setCreatedTime(createdTime);

        // Assert
        assertEquals(id, orderRequest.getId());
        assertEquals(userId, orderRequest.getUserId());
        assertEquals(groupFoodOrderId, orderRequest.getGroupFoodOrderId());
        assertEquals(restaurantId, orderRequest.getRestaurantId());
        assertEquals(location, orderRequest.getLocation());
        assertEquals(deliveryAddress, orderRequest.getDeliveryAddress());
        assertEquals(deliveryLatitude, orderRequest.getDeliveryLatitude());
        assertEquals(deliveryLongitude, orderRequest.getDeliveryLongitude());
        assertEquals(deliveryFee, orderRequest.getDeliveryFee());
        assertEquals(orderDetails, orderRequest.getOrderDetails());
        assertEquals(createdTime, orderRequest.getCreatedTime());
    }
}
