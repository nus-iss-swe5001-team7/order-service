package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RestaurantStaffFilterStrategy implements GroupOrderFilterStrategy {
    @Override
    public List<GroupFoodOrderList> filter(List<GroupFoodOrderList> orders, String restaurantId) {

        return orders.stream()
                .filter(order -> order != null &&
                        order.getRestaurantId() != null && order.getRestaurantId().equals(restaurantId) &&
                        ("SUBMITTED_TO_RESTAURANT".equals(order.getOrderStatus()) || "KITCHEN_PREPARING".equals(order.getOrderStatus())))
                .collect(Collectors.toList());
    }
}

