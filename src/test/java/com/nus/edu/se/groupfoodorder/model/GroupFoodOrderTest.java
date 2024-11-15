package com.nus.edu.se.groupfoodorder.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupFoodOrderTest {

    private GroupFoodOrder groupFoodOrder;

    @BeforeEach
    void setUp() {
        groupFoodOrder = new GroupFoodOrder();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default values
        assertNull(groupFoodOrder.getId());
        assertNull(groupFoodOrder.getGroupOrderCreateTime());
        assertNull(groupFoodOrder.getGroupOrderDeliveryTime());
        assertNull(groupFoodOrder.getDeliveryLocation());
        assertNull(groupFoodOrder.getDeliveryAddress());
        assertNull(groupFoodOrder.getDeliveryLatitude());
        assertNull(groupFoodOrder.getDeliveryLongitude());
        assertEquals(0.0f, groupFoodOrder.getDeliveryFee());
        assertEquals(StatusEnum.PENDING_USER_JOIN, groupFoodOrder.getStatus());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID id = UUID.randomUUID();
        Date createTime = new Date();
        Date deliveryTime = new Date();
        String deliveryLocation = "City Center";
        String deliveryAddress = "123 Main Street";
        String deliveryLatitude = "1.2345";
        String deliveryLongitude = "103.5678";
        float deliveryFee = 15.0f;
        StatusEnum status = StatusEnum.DELIVERED;

        // Act
        groupFoodOrder.setId(id);
        groupFoodOrder.setGroupOrderCreateTime(createTime);
        groupFoodOrder.setGroupOrderDeliveryTime(deliveryTime);
        groupFoodOrder.setDeliveryLocation(deliveryLocation);
        groupFoodOrder.setDeliveryAddress(deliveryAddress);
        groupFoodOrder.setDeliveryLatitude(deliveryLatitude);
        groupFoodOrder.setDeliveryLongitude(deliveryLongitude);
        groupFoodOrder.setDeliveryFee(deliveryFee);
        groupFoodOrder.setStatus(status);

        // Assert
        assertEquals(id, groupFoodOrder.getId());
        assertEquals(createTime, groupFoodOrder.getGroupOrderCreateTime());
        assertEquals(deliveryTime, groupFoodOrder.getGroupOrderDeliveryTime());
        assertEquals(deliveryLocation, groupFoodOrder.getDeliveryLocation());
        assertEquals(deliveryAddress, groupFoodOrder.getDeliveryAddress());
        assertEquals(deliveryLatitude, groupFoodOrder.getDeliveryLatitude());
        assertEquals(deliveryLongitude, groupFoodOrder.getDeliveryLongitude());
        assertEquals(deliveryFee, groupFoodOrder.getDeliveryFee());
        assertEquals(status, groupFoodOrder.getStatus());
    }

    @Test
    void testConstructorWithAllFields() {
        // Arrange
        UUID id = UUID.randomUUID();
        Date createTime = new Date();
        Date deliveryTime = new Date();
        String deliveryLocation = "City Center";
        String deliveryAddress = "123 Main Street";
        String deliveryLatitude = "1.2345";
        String deliveryLongitude = "103.5678";
        float deliveryFee = 15.0f;
        StatusEnum status = StatusEnum.READY_FOR_DELIVERY;

        // Act
        GroupFoodOrder order = new GroupFoodOrder();
        order.setId(id);
        order.setGroupOrderCreateTime(createTime);
        order.setGroupOrderDeliveryTime(deliveryTime);
        order.setDeliveryLocation(deliveryLocation);
        order.setDeliveryAddress(deliveryAddress);
        order.setDeliveryLatitude(deliveryLatitude);
        order.setDeliveryLongitude(deliveryLongitude);
        order.setDeliveryFee(deliveryFee);
        order.setStatus(status);

        // Assert
        assertEquals(id, order.getId());
        assertEquals(createTime, order.getGroupOrderCreateTime());
        assertEquals(deliveryTime, order.getGroupOrderDeliveryTime());
        assertEquals(deliveryLocation, order.getDeliveryLocation());
        assertEquals(deliveryAddress, order.getDeliveryAddress());
        assertEquals(deliveryLatitude, order.getDeliveryLatitude());
        assertEquals(deliveryLongitude, order.getDeliveryLongitude());
        assertEquals(deliveryFee, order.getDeliveryFee());
        assertEquals(status, order.getStatus());
    }
}
