package com.nus.edu.se.user.service;

import com.nus.edu.se.user.dto.UserRole;
import com.nus.edu.se.user.dto.UsersResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final WebClient.Builder webClientBuilder;

    public UsersResponse getUserById(UUID userId, String token){
        UsersResponse usersResponse = webClientBuilder.build().get()
                .uri("http://user-service/user/getUserById/{id}", userId)
                .header("Authorization", "Bearer " + token)
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

    public UserRole getUserRoleByUserId(UUID userID, String token) {
        UsersResponse currentUser = getUserById(userID, token);

        if (currentUser!=null)
        {
            System.out.println("found user");
            return switch (currentUser.getRole()) {
                case "customer" -> UserRole.CUSTOMER;
                case "delivery" -> UserRole.DELIVERY_STAFF;
                case "restaurant" -> UserRole.RESTAURANT_STAFF;
                default -> UserRole.NA;
            };
        } else {
            return UserRole.NA;
        }
    }

}
