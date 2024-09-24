package com.nus.edu.se.groupfoodorder.service.timer;

import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.orderstatus.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TimerService {

    private Map<String, Integer> orderTimers = new HashMap<>();

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    public Map<String, Integer> getOrderTimers() {
        return orderTimers;
    }

    public void startTimerForOrder(String orderId) {
        if (!orderTimers.containsKey(orderId)) {
            orderTimers.put(orderId, 10 * 60); // Start a timer for 10 minutes (600 seconds) for the order
            System.out.println("Timer started for order " + orderId + " for 10 minutes.");
        } else {
            System.out.println("Timer already exists for order " + orderId + ".");
        }
    }

    @Scheduled(fixedRate = 1000) // Update timers every second
    public void updateTimers() {
        for (String orderId : orderTimers.keySet()) {
            int timer = orderTimers.get(orderId);
            if (ifOrderPendingToJoin(orderId)) {
                if (timer > 0) {
                    orderTimers.put(orderId, timer - 1); // Decrement timer value
                    System.out.println("Timer for order " + orderId + ": " + timer);
                } else {
                    // Timer has expired, remove it from the map
                    orderTimers.remove(orderId);
                    System.out.println("Timer expired for order " + orderId + ".");

                    orderStatusService.submittedToRestaurant(orderId);
                }
            } else {
                orderTimers.remove(orderId);
                System.out.println("Order status changed.Timer removed for order " + orderId + ".");
            }

        }
    }

    private boolean ifOrderPendingToJoin(String orderId) {
        GroupFoodOrder order = groupOrderRepository.findById(UUID.fromString(orderId)).orElseThrow(() -> new DataNotFoundException("Group Order Not Found."));
        return order.getStatus() == GroupFoodOrder.Status.PENDING_USER_JOIN;
    }
}
