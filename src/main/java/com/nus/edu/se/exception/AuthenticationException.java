package com.nus.edu.se.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    // You can also add a constructor to pass the cause of the exception
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
}
