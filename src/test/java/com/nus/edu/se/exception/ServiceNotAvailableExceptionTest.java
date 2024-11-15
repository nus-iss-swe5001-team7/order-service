package com.nus.edu.se.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceNotAvailableExceptionTest {

    @Test
    void testServiceNotAvailableExceptionWithMessage() {
        // Arrange
        String errorMessage = "Service is not available";

        // Act
        ServiceNotAvailableException exception = new ServiceNotAvailableException(errorMessage);

        // Assert
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    void testServiceNotAvailableExceptionWithMessageAndCause() {
        // Arrange
        String errorMessage = "Service is not available";
        Throwable cause = new RuntimeException("Underlying cause");

        // Act
        ServiceNotAvailableException exception = new ServiceNotAvailableException(errorMessage, cause);

        // Assert
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testServiceNotAvailableExceptionWithoutMessage() {
        // Act
        ServiceNotAvailableException exception = new ServiceNotAvailableException(null);

        // Assert
        assertNotNull(exception);
        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
