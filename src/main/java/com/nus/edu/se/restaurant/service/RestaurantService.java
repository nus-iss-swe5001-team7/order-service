package com.nus.edu.se.restaurant.service;

import com.nus.edu.se.exception.ServiceNotAvailableException;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RestaurantService {

    private final WebClient.Builder webClientBuilder;
    private static final String SERVICE_NAME = "GetRestaurantById";
    private static final String FALLBACK_METHOD = "fallbackGetRestaurantById";

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = FALLBACK_METHOD)
    public RestaurantResponse getRestaurantById(String restaurantById, String token){
        System.out.println("restaurantById:"+restaurantById);
        RestaurantResponse restaurantResponse = webClientBuilder.build().get()
                .uri("http://restaurant-service/restaurants/getRestaurantById/{id}", restaurantById)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(RestaurantResponse.class).block();
        return restaurantResponse;
    }

    public RestaurantResponse fallbackGetRestaurantById(String restaurantId, String token, Throwable t) {
//        RestaurantResponse fallbackResponse = new RestaurantResponse();
//        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(fallbackResponse);
        throw new ServiceNotAvailableException("Restaurant service is currently unavailable. Please try again later.", t);
    }
}
