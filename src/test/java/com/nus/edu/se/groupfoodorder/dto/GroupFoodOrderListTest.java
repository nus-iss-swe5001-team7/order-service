package com.nus.edu.se.groupfoodorder.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupFoodOrderListTest {

    private GroupFoodOrderList groupFoodOrderList;

    @BeforeEach
    void setUp() {
        groupFoodOrderList = new GroupFoodOrderList();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default values
        assertNull(groupFoodOrderList.getGroupFoodOrderId());
        assertNull(groupFoodOrderList.getDeliveryTime());
        assertNull(groupFoodOrderList.getOrderTime());
        assertNull(groupFoodOrderList.getOrderStatus());
        assertNull(groupFoodOrderList.getRestaurantName());
        assertNull(groupFoodOrderList.getRestaurantId());
        assertNull(groupFoodOrderList.getLocation());
        assertNull(groupFoodOrderList.getRating());
        assertNull(groupFoodOrderList.getImgUrl());
        assertNull(groupFoodOrderList.getDeliveryLocation());
        assertNull(groupFoodOrderList.getDeliveryAddress());
        assertNull(groupFoodOrderList.getDeliveryLatitude());
        assertNull(groupFoodOrderList.getDeliveryLongitude());
        assertNull(groupFoodOrderList.getRestaurantAddress());
        assertNull(groupFoodOrderList.getRestaurantLatitude());
        assertNull(groupFoodOrderList.getRestaurantLongitude());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID groupFoodOrderId = UUID.randomUUID();
        Date deliveryTime = new Date();
        Date orderTime = new Date();
        String orderStatus = "PENDING";
        String restaurantName = "Pizza Palace";
        String restaurantId = "restaurant123";
        String location = "City Center";
        String rating = "4.5";
        String imgUrl = "http://example.com/image.jpg";
        String deliveryLocation = "123 Main Street";
        String deliveryAddress = "456 Elm Street";
        String deliveryLatitude = "1.23456";
        String deliveryLongitude = "103.98765";
        String restaurantAddress = "789 Oak Street";
        String restaurantLatitude = "1.54321";
        String restaurantLongitude = "103.65432";

        // Act
        groupFoodOrderList.setGroupFoodOrderId(groupFoodOrderId);
        groupFoodOrderList.setDeliveryTime(deliveryTime);
        groupFoodOrderList.setOrderTime(orderTime);
        groupFoodOrderList.setOrderStatus(orderStatus);
        groupFoodOrderList.setRestaurantName(restaurantName);
        groupFoodOrderList.setRestaurantId(restaurantId);
        groupFoodOrderList.setLocation(location);
        groupFoodOrderList.setRating(rating);
        groupFoodOrderList.setImgUrl(imgUrl);
        groupFoodOrderList.setDeliveryLocation(deliveryLocation);
        groupFoodOrderList.setDeliveryAddress(deliveryAddress);
        groupFoodOrderList.setDeliveryLatitude(deliveryLatitude);
        groupFoodOrderList.setDeliveryLongitude(deliveryLongitude);
        groupFoodOrderList.setRestaurantAddress(restaurantAddress);
        groupFoodOrderList.setRestaurantLatitude(restaurantLatitude);
        groupFoodOrderList.setRestaurantLongitude(restaurantLongitude);

        // Assert
        assertEquals(groupFoodOrderId, groupFoodOrderList.getGroupFoodOrderId());
        assertEquals(deliveryTime, groupFoodOrderList.getDeliveryTime());
        assertEquals(orderTime, groupFoodOrderList.getOrderTime());
        assertEquals(orderStatus, groupFoodOrderList.getOrderStatus());
        assertEquals(restaurantName, groupFoodOrderList.getRestaurantName());
        assertEquals(restaurantId, groupFoodOrderList.getRestaurantId());
        assertEquals(location, groupFoodOrderList.getLocation());
        assertEquals(rating, groupFoodOrderList.getRating());
        assertEquals(imgUrl, groupFoodOrderList.getImgUrl());
        assertEquals(deliveryLocation, groupFoodOrderList.getDeliveryLocation());
        assertEquals(deliveryAddress, groupFoodOrderList.getDeliveryAddress());
        assertEquals(deliveryLatitude, groupFoodOrderList.getDeliveryLatitude());
        assertEquals(deliveryLongitude, groupFoodOrderList.getDeliveryLongitude());
        assertEquals(restaurantAddress, groupFoodOrderList.getRestaurantAddress());
        assertEquals(restaurantLatitude, groupFoodOrderList.getRestaurantLatitude());
        assertEquals(restaurantLongitude, groupFoodOrderList.getRestaurantLongitude());
    }

    @Test
    void testEqualityAndHashCode() {
        // Arrange
        UUID groupFoodOrderId = UUID.randomUUID();
        groupFoodOrderList.setGroupFoodOrderId(groupFoodOrderId);

        GroupFoodOrderList anotherGroupFoodOrderList = new GroupFoodOrderList();
        anotherGroupFoodOrderList.setGroupFoodOrderId(groupFoodOrderId);

        // Assert
        assertEquals(groupFoodOrderList, anotherGroupFoodOrderList);
        assertEquals(groupFoodOrderList.hashCode(), anotherGroupFoodOrderList.hashCode());
    }
}
