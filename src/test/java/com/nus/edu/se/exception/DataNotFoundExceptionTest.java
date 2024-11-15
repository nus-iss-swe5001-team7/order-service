package com.nus.edu.se.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataNotFoundExceptionTest {

    @Test
    void testDataNotFoundExceptionMessage() {
        // Arrange
        String errorMessage = "Data not found";

        // Act
        DataNotFoundException exception = new DataNotFoundException(errorMessage);

        // Assert
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    void testDataNotFoundExceptionWithoutMessage() {
        // Act
        DataNotFoundException exception = new DataNotFoundException(null);

        // Assert
        assertNotNull(exception);
        assertNull(exception.getMessage());
    }
}
