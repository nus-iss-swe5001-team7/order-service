package com.nus.edu.se.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationExceptionTest {

    @Test
    void testExceptionMessage() {
        String message = "Authentication failed";
        AuthenticationException exception = new AuthenticationException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void testExceptionMessageAndCause() {
        String message = "Authentication failed";
        Throwable cause = new IllegalArgumentException("Invalid token");
        AuthenticationException exception = new AuthenticationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testExceptionWithNullMessage() {
        AuthenticationException exception = new AuthenticationException(null);

        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithNullMessageAndCause() {
        AuthenticationException exception = new AuthenticationException(null, null);

        assertNull(exception.getMessage());
        assertNull(exception.getCause());
    }
}
