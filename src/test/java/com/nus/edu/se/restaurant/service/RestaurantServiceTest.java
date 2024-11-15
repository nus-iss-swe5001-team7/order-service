package com.nus.edu.se.restaurant.service;

import com.nus.edu.se.exception.ServiceNotAvailableException;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    private String restaurantId;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        restaurantId = "restaurant123";
        token = "dummyToken";
    }

    @Test
    void testFallbackGetRestaurantById() {
        // Act & Assert
        ServiceNotAvailableException exception = assertThrows(ServiceNotAvailableException.class, () ->
                restaurantService.fallbackGetRestaurantById(restaurantId, token, new RuntimeException("Service failure"))
        );

        assertEquals("Restaurant service is currently unavailable. Please try again later.", exception.getMessage());
        assertEquals("Service failure", exception.getCause().getMessage());
    }
}
