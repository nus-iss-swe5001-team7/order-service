package com.nus.edu.se.menu.service;

import com.nus.edu.se.menu.dto.MenuResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MenuService {
    private final WebClient.Builder webClientBuilder;
    public MenuResponse findById(String menuId){
        MenuResponse menuResponse = webClientBuilder.build().get()
                .uri("http://restaurant-service/menuAPI/getMenuById/{id}", menuId)
//                .uri("http://localhost:8090/restaurants/getRestaurantById/{id}", restaurantById)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8090/restaurants/getRestaurantById").queryParam("id",restaurantById).build())
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    return Mono.error(new RuntimeException("MenuService getMenuById Client error: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("MenuService getMenuById Server error: " + response.statusCode()));
                })
                .bodyToMono(MenuResponse.class).block();
        return menuResponse;
    }

}

