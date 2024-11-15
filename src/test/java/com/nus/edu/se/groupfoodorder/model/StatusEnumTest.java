package com.nus.edu.se.groupfoodorder.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusEnumTest {

    @Test
    void testEnumValues() {
        // Assert all expected enum values are present
        StatusEnum[] expectedValues = {
                StatusEnum.PENDING_USER_JOIN,
                StatusEnum.ORDER_CANCEL,
                StatusEnum.SUBMITTED_TO_RESTAURANT,
                StatusEnum.KITCHEN_PREPARING,
                StatusEnum.READY_FOR_DELIVERY,
                StatusEnum.ON_DELIVERY,
                StatusEnum.DELIVERED
        };

        assertArrayEquals(expectedValues, StatusEnum.values());
    }

    @Test
    void testValueOf_ValidValues() {
        // Assert valid enum value strings return the correct enum constants
        assertEquals(StatusEnum.PENDING_USER_JOIN, StatusEnum.valueOf("PENDING_USER_JOIN"));
        assertEquals(StatusEnum.ORDER_CANCEL, StatusEnum.valueOf("ORDER_CANCEL"));
        assertEquals(StatusEnum.SUBMITTED_TO_RESTAURANT, StatusEnum.valueOf("SUBMITTED_TO_RESTAURANT"));
        assertEquals(StatusEnum.KITCHEN_PREPARING, StatusEnum.valueOf("KITCHEN_PREPARING"));
        assertEquals(StatusEnum.READY_FOR_DELIVERY, StatusEnum.valueOf("READY_FOR_DELIVERY"));
        assertEquals(StatusEnum.ON_DELIVERY, StatusEnum.valueOf("ON_DELIVERY"));
        assertEquals(StatusEnum.DELIVERED, StatusEnum.valueOf("DELIVERED"));
    }

    @Test
    void testValueOf_InvalidValue() {
        // Assert that invalid value throws an exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> StatusEnum.valueOf("INVALID_STATUS"));
        assertEquals("No enum constant com.nus.edu.se.groupfoodorder.model.StatusEnum.INVALID_STATUS", exception.getMessage());
    }
}
