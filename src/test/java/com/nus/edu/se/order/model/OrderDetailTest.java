package com.nus.edu.se.order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailTest {

    private OrderDetail orderDetail;

    @BeforeEach
    void setUp() {
        orderDetail = new OrderDetail();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default values
        assertNull(orderDetail.getId());
        assertNull(orderDetail.getOrder());
        assertNull(orderDetail.getMenuId());
        assertNull(orderDetail.getQuantity());
        assertNull(orderDetail.getPreferences());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID detailId = UUID.randomUUID();
        Order mockOrder = new Order();
        String menuId = "menu123";
        Integer quantity = 3;
        Map<String, String> preferences = Map.of("extraCheese", "yes", "size", "large");

        // Act
        orderDetail.setId(detailId);
        orderDetail.setOrder(mockOrder);
        orderDetail.setMenuId(menuId);
        orderDetail.setQuantity(quantity);
        orderDetail.setPreferences(preferences);

        // Assert
        assertEquals(detailId, orderDetail.getId());
        assertEquals(mockOrder, orderDetail.getOrder());
        assertEquals(menuId, orderDetail.getMenuId());
        assertEquals(quantity, orderDetail.getQuantity());
        assertEquals(preferences, orderDetail.getPreferences());
    }

    @Test
    void testBuilder() {
        // Arrange
        UUID detailId = UUID.randomUUID();
        Order mockOrder = new Order();
        String menuId = "menu456";
        Integer quantity = 2;
        Map<String, String> preferences = Map.of("spicy", "medium", "sauce", "extra");

        // Act
        OrderDetail orderDetail = OrderDetail.builder()
                .id(detailId)
                .order(mockOrder)
                .menuId(menuId)
                .quantity(quantity)
                .preferences(preferences)
                .build();

        // Assert
        assertEquals(detailId, orderDetail.getId());
        assertEquals(mockOrder, orderDetail.getOrder());
        assertEquals(menuId, orderDetail.getMenuId());
        assertEquals(quantity, orderDetail.getQuantity());
        assertEquals(preferences, orderDetail.getPreferences());
    }
}
