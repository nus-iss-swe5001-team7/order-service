package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerGroupOrderFilterStrategyTest {

    @Test
    public void testFilterByOrderStatus() {
        // Setup
        GroupFoodOrderList order1 = createOrder("PENDING_USER_JOIN");
        GroupFoodOrderList order2 = createOrder("KITCHEN_PREPARING");
        GroupFoodOrderList order3 = createOrder("PENDING_USER_JOIN");

        List<GroupFoodOrderList> orders = Arrays.asList(order1, order2, order3);

        // Strategy
        CustomerGroupOrderFilterStrategy strategy = new CustomerGroupOrderFilterStrategy();

        // Action
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, "PENDING_USER_JOIN");

        // Assert
        assertEquals(2, filteredOrders.size(), "Should filter out exactly two orders.");
        assertTrue(filteredOrders.contains(order1), "Order1 should be included.");
        assertTrue(filteredOrders.contains(order3), "Order3 should be included.");
    }

    @Test
    public void testFilterEmptyList() {
        List<GroupFoodOrderList> emptyOrders = Arrays.asList();
        CustomerGroupOrderFilterStrategy strategy = new CustomerGroupOrderFilterStrategy();
        List<GroupFoodOrderList> filteredOrders = strategy.filter(emptyOrders, "PENDING_USER_JOIN");
        assertTrue(filteredOrders.isEmpty(), "Filtered list should be empty for an empty input list.");
    }

    @Test
    public void testFilterNoMatches() {
        GroupFoodOrderList order1 = createOrder("DELIVERED");
        GroupFoodOrderList order2 = createOrder("ON_DELIVERY");
        List<GroupFoodOrderList> orders = Arrays.asList(order1, order2);
        CustomerGroupOrderFilterStrategy strategy = new CustomerGroupOrderFilterStrategy();
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, "PENDING_USER_JOIN");
        assertTrue(filteredOrders.isEmpty(), "Filtered list should be empty when no orders match the criteria.");
    }

    @Test
    public void testFilterAllMatches() {
        GroupFoodOrderList order1 = createOrder("PENDING_USER_JOIN");
        GroupFoodOrderList order2 = createOrder("PENDING_USER_JOIN");
        List<GroupFoodOrderList> orders = Arrays.asList(order1, order2);
        CustomerGroupOrderFilterStrategy strategy = new CustomerGroupOrderFilterStrategy();
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, "PENDING_USER_JOIN");
        assertEquals(2, filteredOrders.size(), "All orders should be included when all match the criteria.");
        assertTrue(filteredOrders.containsAll(Arrays.asList(order1, order2)), "All matching orders should be included.");
    }



    private GroupFoodOrderList createOrder(String orderStatus) {
        GroupFoodOrderList order = new GroupFoodOrderList();
        order.setOrderStatus(orderStatus);
        return order;
    }
}

