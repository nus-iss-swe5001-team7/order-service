package com.nus.edu.se.user.service;

import com.nus.edu.se.user.dto.UserRole;
import com.nus.edu.se.user.dto.UsersResponse;
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
public class UsersService {

    private final WebClient.Builder webClientBuilder;

    public UsersResponse getUserById(UUID userId){
        UsersResponse usersResponse = webClientBuilder.build().get()
                .uri("http://user-service/user/getUserById/{id}", userId)
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

    public UserRole getUserRoleByUserId(UUID userID) {
        UsersResponse currentUser = getUserById(userID);

        if (currentUser!=null)
        {
            System.out.println("found user");
            if (currentUser.getRole().equals("customer"))
                return UserRole.CUSTOMER;
            else if (currentUser.getRole().equals("delivery"))
                return UserRole.DELIVERY_STAFF;
            else if (currentUser.getRole().equals("restaurant"))
                return UserRole.RESTAURANT_STAFF;
            else
                return UserRole.NA;
        } else {
            return UserRole.NA;
        }
    }

}
