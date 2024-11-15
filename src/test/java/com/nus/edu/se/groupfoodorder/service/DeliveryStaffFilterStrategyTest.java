package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryStaffFilterStrategyTest {

    @Test
    void testFilterByLocationAndStatus() {
        // Setup test orders
        GroupFoodOrderList order1 = createOrder("Central", "READY_FOR_DELIVERY");
        GroupFoodOrderList order2 = createOrder("Central", "ON_DELIVERY");
        GroupFoodOrderList order3 = createOrder("Central", "KITCHEN_PREPARING");  // should not be included
        GroupFoodOrderList order4 = createOrder("North", "READY_FOR_DELIVERY");    // should not be included due to location

        List<GroupFoodOrderList> orders = Arrays.asList(order1, order2, order3, order4);

        // Create an instance of the strategy
        DeliveryStaffFilterStrategy strategy = new DeliveryStaffFilterStrategy();

        // Execute the filter method
        List<GroupFoodOrderList> filteredOrders = strategy.filter(orders, "Central");

        // Assertions
        assertEquals(2, filteredOrders.size(), "Should filter out exactly two orders.");
        assertTrue(filteredOrders.contains(order1), "Order1 should be included.");
        assertTrue(filteredOrders.contains(order2), "Order2 should be included.");
        assertFalse(filteredOrders.contains(order3), "Order3 should not be included.");
        assertFalse(filteredOrders.contains(order4), "Order4 should not be included because of location mismatch.");
    }

    private GroupFoodOrderList createOrder(String location, String orderStatus) {
        GroupFoodOrderList order = new GroupFoodOrderList();
        order.setLocation(location);
        order.setOrderStatus(orderStatus);
        return order;
    }
}
