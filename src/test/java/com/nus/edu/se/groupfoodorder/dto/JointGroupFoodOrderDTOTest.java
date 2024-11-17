package com.nus.edu.se.groupfoodorder.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JointGroupFoodOrderDTOTest {

    private JointGroupFoodOrderDTO jointGroupFoodOrderDTO;

    @BeforeEach
    void setUp() {
        jointGroupFoodOrderDTO = new JointGroupFoodOrderDTO();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default constructor initializes fields to null or default values
        assertNull(jointGroupFoodOrderDTO.getStatus());
        assertNull(jointGroupFoodOrderDTO.getRestaurantId());
        assertNull(jointGroupFoodOrderDTO.getDeliveryLocation());
        assertNull(jointGroupFoodOrderDTO.getNumberOfUsers());
        assertEquals(0.0f, jointGroupFoodOrderDTO.getGroupOrderDeliveryFee());
        assertNull(jointGroupFoodOrderDTO.getMainOrderId());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        String status = "DELIVERED";
        String restaurantId = "restaurant123";
        String deliveryLocation = "123 Main Street";
        Integer numberOfUsers = 5;
        float groupOrderDeliveryFee = 10.5f;
        String mainOrderId = "mainOrder123";

        // Act
        jointGroupFoodOrderDTO.setStatus(status);
        jointGroupFoodOrderDTO.setRestaurantId(restaurantId);
        jointGroupFoodOrderDTO.setDeliveryLocation(deliveryLocation);
        jointGroupFoodOrderDTO.setNumberOfUsers(numberOfUsers);
        jointGroupFoodOrderDTO.setGroupOrderDeliveryFee(groupOrderDeliveryFee);
        jointGroupFoodOrderDTO.setMainOrderId(mainOrderId);

        // Assert
        assertEquals(status, jointGroupFoodOrderDTO.getStatus());
        assertEquals(restaurantId, jointGroupFoodOrderDTO.getRestaurantId());
        assertEquals(deliveryLocation, jointGroupFoodOrderDTO.getDeliveryLocation());
        assertEquals(numberOfUsers, jointGroupFoodOrderDTO.getNumberOfUsers());
        assertEquals(groupOrderDeliveryFee, jointGroupFoodOrderDTO.getGroupOrderDeliveryFee());
        assertEquals(mainOrderId, jointGroupFoodOrderDTO.getMainOrderId());
    }

}
