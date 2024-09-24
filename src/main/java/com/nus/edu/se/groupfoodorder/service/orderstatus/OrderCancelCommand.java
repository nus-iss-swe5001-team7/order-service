package com.nus.edu.se.groupfoodorder.service.orderstatus;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderCancelCommand implements OrderStatusCommandInterface {

    @Autowired
    private final GroupOrdersService groupOrdersService;

    public OrderCancelCommand(GroupOrdersService groupOrdersService) {
        this.groupOrdersService = groupOrdersService;
    }

    @Override
    public void execute(String orderId) {
        if (orderId == null) {
            throw new NullPointerException("Order ID cannot be null");
        }
        groupOrdersService.updateStatus(orderId, GroupFoodOrder.Status.ORDER_CANCEL);
    }
}

