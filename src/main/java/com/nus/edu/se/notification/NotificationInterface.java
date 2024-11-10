package com.nus.edu.se.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATION")
public interface NotificationInterface {
    @PostMapping("messages")
    ResponseEntity<String> publish(@RequestBody MessageRequest request);

}
