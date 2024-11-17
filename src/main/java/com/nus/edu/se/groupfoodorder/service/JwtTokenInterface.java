package com.nus.edu.se.groupfoodorder.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("API-GATEWAY")
public interface JwtTokenInterface {
    @PostMapping("jwt/validateToken")
    ResponseEntity<Boolean> validateToken(@RequestBody String token);
}
