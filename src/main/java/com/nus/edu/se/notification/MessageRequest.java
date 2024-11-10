package com.nus.edu.se.notification;

import com.nus.edu.se.groupfoodorder.model.StatusEnum;

public record MessageRequest(String userId, String orderId, StatusEnum status) {
}
