package com.nus.edu.se.exception;

public class ServiceNotAvailableException extends RuntimeException {

    public ServiceNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotAvailableException(String message) {
        super(message);
    }
}
