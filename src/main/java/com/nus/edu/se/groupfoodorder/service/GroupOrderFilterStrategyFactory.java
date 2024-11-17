package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.user.dto.UserRole;
import org.springframework.stereotype.Component;

@Component
public class GroupOrderFilterStrategyFactory {
    public GroupOrderFilterStrategy getStrategy(UserRole role) {
        switch (role) {
            case CUSTOMER:
                return new CustomerGroupOrderFilterStrategy();
            case RESTAURANT_STAFF:
                return new RestaurantStaffFilterStrategy();
            case DELIVERY_STAFF:
                return new DeliveryStaffFilterStrategy();
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}
