package com.nus.edu.se.groupfoodorder.dto;

import com.nus.edu.se.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseTest {

    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderResponse = new OrderResponse();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default constructor initializes fields to null or default values
        assertNull(orderResponse.getId());
        assertNull(orderResponse.getGroupFoodOrderId());
        assertNull(orderResponse.getRestaurantId());
        assertNull(orderResponse.getUserId());
        assertNull(orderResponse.getCreatedTime());
        assertNull(orderResponse.getOrderDetails());
        assertNull(orderResponse.getLocation());
        assertNull(orderResponse.getDeliveryAddress());
        assertNull(orderResponse.getDeliveryLatitude());
        assertNull(orderResponse.getDeliveryLongitude());
        assertEquals(0.0f, orderResponse.getDeliveryFee());
        assertNull(orderResponse.getPaymentStatus());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String id = "order123";
        String groupFoodOrderId = "groupOrder123";
        String restaurantId = "restaurant123";
        String userId = "user123";
        Date createdTime = new Date();
        String orderDetails = "Order details JSON";
        String location = "City Center";
        String deliveryAddress = "123 Main Street";
        String deliveryLatitude = "1.2345";
        String deliveryLongitude = "103.5678";
        float deliveryFee = 15.0f;
        Order.PaymentStatus paymentStatus = Order.PaymentStatus.COMPLETED;

        // Act
        OrderResponse orderResponse = new OrderResponse(
                id, groupFoodOrderId, restaurantId, userId, createdTime, orderDetails,
                location, deliveryAddress, deliveryLatitude, deliveryLongitude, deliveryFee, paymentStatus
        );

        // Assert
        assertEquals(id, orderResponse.getId());
        assertEquals(groupFoodOrderId, orderResponse.getGroupFoodOrderId());
        assertEquals(restaurantId, orderResponse.getRestaurantId());
        assertEquals(userId, orderResponse.getUserId());
        assertEquals(createdTime, orderResponse.getCreatedTime());
        assertEquals(orderDetails, orderResponse.getOrderDetails());
        assertEquals(location, orderResponse.getLocation());
        assertEquals(deliveryAddress, orderResponse.getDeliveryAddress());
        assertEquals(deliveryLatitude, orderResponse.getDeliveryLatitude());
        assertEquals(deliveryLongitude, orderResponse.getDeliveryLongitude());
        assertEquals(deliveryFee, orderResponse.getDeliveryFee());
        assertEquals(paymentStatus, orderResponse.getPaymentStatus());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        String id = "order456";
        String groupFoodOrderId = "groupOrder456";
        String restaurantId = "restaurant456";
        String userId = "user456";
        Date createdTime = new Date();
        String orderDetails = "Another order details JSON";
        String location = "Suburban Area";
        String deliveryAddress = "456 Elm Street";
        String deliveryLatitude = "2.3456";
        String deliveryLongitude = "104.6789";
        float deliveryFee = 20.0f;
        Order.PaymentStatus paymentStatus = Order.PaymentStatus.PROCESSING;

        // Act
        orderResponse.setId(id);
        orderResponse.setGroupFoodOrderId(groupFoodOrderId);
        orderResponse.setRestaurantId(restaurantId);
        orderResponse.setUserId(userId);
        orderResponse.setCreatedTime(createdTime);
        orderResponse.setOrderDetails(orderDetails);
        orderResponse.setLocation(location);
        orderResponse.setDeliveryAddress(deliveryAddress);
        orderResponse.setDeliveryLatitude(deliveryLatitude);
        orderResponse.setDeliveryLongitude(deliveryLongitude);
        orderResponse.setDeliveryFee(deliveryFee);
        orderResponse.setPaymentStatus(paymentStatus);

        // Assert
        assertEquals(id, orderResponse.getId());
        assertEquals(groupFoodOrderId, orderResponse.getGroupFoodOrderId());
        assertEquals(restaurantId, orderResponse.getRestaurantId());
        assertEquals(userId, orderResponse.getUserId());
        assertEquals(createdTime, orderResponse.getCreatedTime());
        assertEquals(orderDetails, orderResponse.getOrderDetails());
        assertEquals(location, orderResponse.getLocation());
        assertEquals(deliveryAddress, orderResponse.getDeliveryAddress());
        assertEquals(deliveryLatitude, orderResponse.getDeliveryLatitude());
        assertEquals(deliveryLongitude, orderResponse.getDeliveryLongitude());
        assertEquals(deliveryFee, orderResponse.getDeliveryFee());
        assertEquals(paymentStatus, orderResponse.getPaymentStatus());
    }
}
