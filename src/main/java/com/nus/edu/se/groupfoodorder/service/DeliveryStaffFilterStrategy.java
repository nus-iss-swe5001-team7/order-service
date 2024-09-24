package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderList;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryStaffFilterStrategy implements GroupOrderFilterStrategy {
    @Override
    public List<GroupFoodOrderList> filter(List<GroupFoodOrderList> orders, String location) {
        return orders.stream()
                .filter(order ->  order.getLocation().equalsIgnoreCase(location) &&
                        ("READY_FOR_DELIVERY".equals(order.getOrderStatus()) || "ON_DELIVERY".equals(order.getOrderStatus())))
                .collect(Collectors.toList());
    }
}
