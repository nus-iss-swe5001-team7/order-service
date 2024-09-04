package com.nus.edu.se.grouopfoodorder.service;

import com.nus.edu.se.grouopfoodorder.dto.RestaurantResponse;
import com.nus.edu.se.grouopfoodorder.dto.UsersResponse;
import com.nus.edu.se.grouopfoodorder.model.GroupFoodOrder;
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
public class GroupOrdersService {

    private final WebClient.Builder webClientBuilder;

    public boolean groupFoodOrderNotValidToJoin(GroupFoodOrder groupFoodOrder) {
        return groupFoodOrder.getStatus() == GroupFoodOrder.Status.SUBMITTED_TO_RESTAURANT || (groupFoodOrder.getStatus() == GroupFoodOrder.Status.ORDER_CANCEL);
    }

    public UsersResponse getUserById(UUID userId){
         UsersResponse usersResponse = webClientBuilder.build().get()
//                .uri("http://localhost:8080/user/getUserById/{id}", userId)
                .uri("http://user-service/user/getUserById/{id}", userId)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8080/user/getUserById")
//                        .queryParam("id",userId)
//                        .build())
//                 .uri("http://localhost:8080/user/getUserById/{id}", uriBuilder -> uriBuilder
//                         .queryParam("id",userId)
//                         .build() )
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getUserById Client error: " + response.statusCode()));
                })
                .onStatus(status -> status.is5xxServerError(), response -> {
                    return Mono.error(new RuntimeException("GroupOrdersService getUserById Server error: " + response.statusCode()));
                })
        .bodyToMono(UsersResponse.class).block();
        return usersResponse;
    }

    public RestaurantResponse getRestaurantById(UUID restaurantById){
        RestaurantResponse restaurantResponse = webClientBuilder.build().get()
                .uri("http://restaurant-service/restaurant/getRestaurantById/{id}", restaurantById)
//                .uri("http://localhost:8090/restaurant/getRestaurantById/{id}", restaurantById)
//                .uri(uriBuilder -> uriBuilder.path("http://localhost:8090/restaurant/getRestaurantById").queryParam("id",restaurantById).build())
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
