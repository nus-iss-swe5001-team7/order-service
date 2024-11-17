package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerGroupOrderFilterStrategy implements GroupOrderFilterStrategy {
    @Override
    public List<GroupFoodOrderList> filter(List<GroupFoodOrderList> orders, String criteria) {

        return orders.stream()
                .filter(order -> "PENDING_USER_JOIN".equals(order.getOrderStatus()))
                .collect(Collectors.toList());
    }
}
