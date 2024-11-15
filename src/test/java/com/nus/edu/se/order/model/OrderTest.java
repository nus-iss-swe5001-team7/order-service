package com.nus.edu.se.order.model;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default values
        assertNull(order.getId());
        assertNull(order.getUserId());
        assertNull(order.getRestaurantId());
        assertNull(order.getGroupFoodOrder());
        assertNull(order.getCreatedTime());
        assertEquals(0.0f, order.getDeliveryFee());
        assertEquals(Order.PaymentStatus.PENDING, order.getPaymentStatus());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        String restaurantId = "restaurant123";
        GroupFoodOrder groupFoodOrder = new GroupFoodOrder();
        Date createdTime = new Date();
        float deliveryFee = 12.50f;
        Order.PaymentStatus paymentStatus = Order.PaymentStatus.COMPLETED;

        // Act
        order.setId(orderId);
        order.setUserId(userId);
        order.setRestaurantId(restaurantId);
        order.setGroupFoodOrder(groupFoodOrder);
        order.setCreatedTime(createdTime);
        order.setDeliveryFee(deliveryFee);
        order.setPaymentStatus(paymentStatus);

        // Assert
        assertEquals(orderId, order.getId());
        assertEquals(userId, order.getUserId());
        assertEquals(restaurantId, order.getRestaurantId());
        assertEquals(groupFoodOrder, order.getGroupFoodOrder());
        assertEquals(createdTime, order.getCreatedTime());
        assertEquals(deliveryFee, order.getDeliveryFee());
        assertEquals(paymentStatus, order.getPaymentStatus());
    }

    @Test
    void testEnumPaymentStatus() {
        // Verify all PaymentStatus enum values
        assertEquals(Order.PaymentStatus.PENDING, Order.PaymentStatus.valueOf("PENDING"));
        assertEquals(Order.PaymentStatus.PROCESSING, Order.PaymentStatus.valueOf("PROCESSING"));
        assertEquals(Order.PaymentStatus.COMPLETED, Order.PaymentStatus.valueOf("COMPLETED"));
        assertEquals(Order.PaymentStatus.FAILED, Order.PaymentStatus.valueOf("FAILED"));
    }
}
