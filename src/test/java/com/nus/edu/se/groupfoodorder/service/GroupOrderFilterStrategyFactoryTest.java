package com.nus.edu.se.groupfoodorder.service;

import com.nus.edu.se.user.dto.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupOrderFilterStrategyFactoryTest {

    @Test
    void getStrategy_ShouldReturnCustomerStrategy_WhenRoleIsCustomer() {
        GroupOrderFilterStrategyFactory factory = new GroupOrderFilterStrategyFactory();
        assertTrue(factory.getStrategy(UserRole.CUSTOMER) instanceof CustomerGroupOrderFilterStrategy);
    }

    @Test
    void getStrategy_ShouldReturnRestaurantStaffStrategy_WhenRoleIsRestaurantStaff() {
        GroupOrderFilterStrategyFactory factory = new GroupOrderFilterStrategyFactory();
        assertTrue(factory.getStrategy(UserRole.RESTAURANT_STAFF) instanceof RestaurantStaffFilterStrategy);
    }

    @Test
    void getStrategy_ShouldReturnDeliveryStaffStrategy_WhenRoleIsDeliveryStaff() {
        GroupOrderFilterStrategyFactory factory = new GroupOrderFilterStrategyFactory();
        assertTrue(factory.getStrategy(UserRole.DELIVERY_STAFF) instanceof DeliveryStaffFilterStrategy);
    }

    @Test
    void getStrategy_ShouldThrowIllegalArgumentException_WhenRoleIsUnsupported() {
        GroupOrderFilterStrategyFactory factory = new GroupOrderFilterStrategyFactory();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> factory.getStrategy(UserRole.NA));
        assertEquals("Unsupported role: NA", exception.getMessage());
    }
}

