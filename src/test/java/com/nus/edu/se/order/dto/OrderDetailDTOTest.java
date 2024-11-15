package com.nus.edu.se.order.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderDetailDTOTest {

    private OrderDetailDTO orderDetailDTO;

    @BeforeEach
    void setUp() {
        orderDetailDTO = new OrderDetailDTO();
    }

    @Test
    void testDefaultConstructor() {
        // Assert that all fields are null or default values
        assertNull(orderDetailDTO.getMenuId());
        assertNull(orderDetailDTO.getName());
        assertNull(orderDetailDTO.getImgUrl());
        assertNull(orderDetailDTO.getDescription());
        assertNull(orderDetailDTO.getOrderItemId());
        assertNull(orderDetailDTO.getPreferences());
        assertNull(orderDetailDTO.getQuantity());
        assertEquals(0.0f, orderDetailDTO.getPrice());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String menuId = "menu123";
        Integer quantity = 2;
        String name = "Pizza";
        float price = 15.99f;
        String imgUrl = "http://example.com/pizza.jpg";
        String description = "Delicious cheese pizza";
        UUID orderItemId = UUID.randomUUID();
        Map<String, String> preferences = Map.of("extraCheese", "yes", "size", "large");

        // Act
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO(menuId, quantity, name, price, imgUrl, description, orderItemId, preferences);

        // Assert
        assertEquals(menuId, orderDetailDTO.getMenuId());
        assertEquals(quantity, orderDetailDTO.getQuantity());
        assertEquals(name, orderDetailDTO.getName());
        assertEquals(price, orderDetailDTO.getPrice());
        assertEquals(imgUrl, orderDetailDTO.getImgUrl());
        assertEquals(description, orderDetailDTO.getDescription());
        assertEquals(orderItemId, orderDetailDTO.getOrderItemId());
        assertEquals(preferences, orderDetailDTO.getPreferences());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        String menuId = "menu123";
        Integer quantity = 3;
        String name = "Burger";
        float price = 10.49f;
        String imgUrl = "http://example.com/burger.jpg";
        String description = "Juicy beef burger";
        UUID orderItemId = UUID.randomUUID();
        Map<String, String> preferences = Map.of("noPickles", "true", "size", "medium");

        // Act
        orderDetailDTO.setMenuId(menuId);
        orderDetailDTO.setQuantity(quantity);
        orderDetailDTO.setName(name);
        orderDetailDTO.setPrice(price);
        orderDetailDTO.setImgUrl(imgUrl);
        orderDetailDTO.setDescription(description);
        orderDetailDTO.setOrderItemId(orderItemId);
        orderDetailDTO.setPreferences(preferences);

        // Assert
        assertEquals(menuId, orderDetailDTO.getMenuId());
        assertEquals(quantity, orderDetailDTO.getQuantity());
        assertEquals(name, orderDetailDTO.getName());
        assertEquals(price, orderDetailDTO.getPrice());
        assertEquals(imgUrl, orderDetailDTO.getImgUrl());
        assertEquals(description, orderDetailDTO.getDescription());
        assertEquals(orderItemId, orderDetailDTO.getOrderItemId());
        assertEquals(preferences, orderDetailDTO.getPreferences());
    }
}
