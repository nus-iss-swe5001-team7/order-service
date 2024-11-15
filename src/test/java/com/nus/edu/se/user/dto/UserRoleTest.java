package com.nus.edu.se.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {

    @Test
    void testEnumValues() {
        // Verify the enum values
        UserRole[] expectedValues = {
                UserRole.CUSTOMER,
                UserRole.RESTAURANT_STAFF,
                UserRole.DELIVERY_STAFF,
                UserRole.NA
        };

        assertArrayEquals(expectedValues, UserRole.values());
    }

    @Test
    void testEnumValueOf() {
        // Verify the valueOf method
        assertEquals(UserRole.CUSTOMER, UserRole.valueOf("CUSTOMER"));
        assertEquals(UserRole.RESTAURANT_STAFF, UserRole.valueOf("RESTAURANT_STAFF"));
        assertEquals(UserRole.DELIVERY_STAFF, UserRole.valueOf("DELIVERY_STAFF"));
        assertEquals(UserRole.NA, UserRole.valueOf("NA"));
    }

    @Test
    void testInvalidEnumValue() {
        // Verify an invalid value throws an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> UserRole.valueOf("INVALID_ROLE"));
    }
}
