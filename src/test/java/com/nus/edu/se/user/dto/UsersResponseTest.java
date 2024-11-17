package com.nus.edu.se.user.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UsersResponseTest {

    private UsersResponse usersResponse;

    @BeforeEach
    void setUp() {
        usersResponse = new UsersResponse();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default constructor initializes fields to null
        assertNull(usersResponse.getUserId());
        assertNull(usersResponse.getName());
        assertNull(usersResponse.getPassword());
        assertNull(usersResponse.getEmail());
        assertNull(usersResponse.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "John Doe";
        String password = "securePassword";
        String email = "john.doe@example.com";
        String role = "CUSTOMER";

        // Act
        UsersResponse usersResponse = new UsersResponse(userId, name, password, email, role);

        // Assert
        assertEquals(userId, usersResponse.getUserId());
        assertEquals(name, usersResponse.getName());
        assertEquals(password, usersResponse.getPassword());
        assertEquals(email, usersResponse.getEmail());
        assertEquals(role, usersResponse.getRole());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID userId = UUID.randomUUID();
        String name = "Jane Smith";
        String password = "password123";
        String email = "jane.smith@example.com";
        String role = "ADMIN";

        // Act
        usersResponse.setUserId(userId);
        usersResponse.setName(name);
        usersResponse.setPassword(password);
        usersResponse.setEmail(email);
        usersResponse.setRole(role);

        // Assert
        assertEquals(userId, usersResponse.getUserId());
        assertEquals(name, usersResponse.getName());
        assertEquals(password, usersResponse.getPassword());
        assertEquals(email, usersResponse.getEmail());
        assertEquals(role, usersResponse.getRole());
    }
}
