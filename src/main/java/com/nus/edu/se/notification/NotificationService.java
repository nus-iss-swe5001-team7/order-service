package com.nus.edu.se.notification;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    NotificationInterface notificationInterface;

    @Autowired
    private OrderRepository orderRepository;

    public void sendNotification(GroupFoodOrder groupFoodOrder, StatusEnum status) {
        List<Order> orderList = orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(groupFoodOrder);
        int notificationsSent = 0;

        for (Order order : orderList) {
            MessageRequest messageRequest = new MessageRequest(order.getUserId().toString(), order.getId().toString(), status);

            ResponseEntity<String> response = notificationInterface.publish(messageRequest);

            if (response.getStatusCode() == HttpStatus.OK) {
                notificationsSent++;
            }
        }

        System.out.println(String.format("Notifications sent successfully for %d orders.", notificationsSent));
    }

}
