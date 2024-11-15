package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantStaffFilterStrategyTest {

    private RestaurantStaffFilterStrategy strategy;
    private List<GroupFoodOrderList> orders;
    private String restaurantId;

    @BeforeEach
    void setUp() {
        strategy = new RestaurantStaffFilterStrategy();
        orders = new ArrayList<>();
        restaurantId = UUID.randomUUID().toString();

        // Create mock orders
        GroupFoodOrderList order1 = new GroupFoodOrderList();
        order1.setRestaurantId(restaurantId);
        order1.setOrderStatus("SUBMITTED_TO_RESTAURANT");

        GroupFoodOrderList order2 = new GroupFoodOrderList();
        order2.setRestaurantId(restaurantId);
        order2.setOrderStatus("KITCHEN_PREPARING");

        GroupFoodOrderList order3 = new GroupFoodOrderList();
        order3.setRestaurantId("different-restaurant-id");
        order3.setOrderStatus("SUBMITTED_TO_RESTAURANT");

        GroupFoodOrderList order4 = new GroupFoodOrderList();
        order4.setRestaurantId(restaurantId);
        order4.setOrderStatus("ON_DELIVERY");

        GroupFoodOrderList order5 = new GroupFoodOrderList(); // Null restaurantId
        order5.setOrderStatus("SUBMITTED_TO_RESTAURANT");

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);
        orders.add(order5);
    }

    @Test
    void testFilter() {
        // Act
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, restaurantId);

        // Assert
        assertNotNull(filteredOrders);
        assertEquals(2, filteredOrders.size());

        assertTrue(filteredOrders.stream()
                .allMatch(order -> restaurantId.equals(order.getRestaurantId()) &&
                        ("SUBMITTED_TO_RESTAURANT".equals(order.getOrderStatus()) || "KITCHEN_PREPARING".equals(order.getOrderStatus()))));
    }

    @Test
    void testFilterWithNoMatchingOrders() {
        // Act
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, "non-existent-restaurant-id");

        // Assert
        assertNotNull(filteredOrders);
        assertTrue(filteredOrders.isEmpty());
    }

    @Test
    void testFilterWithEmptyOrders() {
        // Act
        List<GroupFoodOrderList> filteredOrders = strategy.filter(new ArrayList<>(), restaurantId);

        // Assert
        assertNotNull(filteredOrders);
        assertTrue(filteredOrders.isEmpty());
    }
}
