package com.nus.edu.se.restaurant.service;

import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RestaurantService {


    private final WebClient.Builder webClientBuilder;
    public RestaurantResponse getRestaurantById(String restaurantById, String token){
        System.out.println("restaurantById:"+restaurantById);
        RestaurantResponse restaurantResponse = webClientBuilder.build().get()
                .uri("http://restaurant-service/restaurants/getRestaurantById/{id}", restaurantById)
                .header("Authorization", "Bearer " + token)
//                .uri("http://localhost:8090/restaurants/getRestaurantById/{id}", restaurantById)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8090/restaurants/getRestaurantById").queryParam("id",restaurantById).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getRestaurantById Client error: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getRestaurantById Server error: " + response.statusCode()));
                })
                .bodyToMono(RestaurantResponse.class).block();
        return restaurantResponse;
    }

}
