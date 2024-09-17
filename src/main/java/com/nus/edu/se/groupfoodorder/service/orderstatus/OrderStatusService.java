package com.nus.edu.se.groupfoodorder.service.orderstatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Invoker
@Service
public class OrderStatusService {
    @Autowired
    private final SubmittedToRestaurantCommand submittedToRestaurantCommand;
    @Autowired
    private final OrderCancelCommand orderCancelCommand;
    @Autowired
    private final KitchenPreparingCommand kitchenPreparingCommand;
    @Autowired
    private final ReadyForDeliveryCommand readyForDeliveryCommand;
    @Autowired
    private final OnDeliveryCommand onDeliveryCommand;
    @Autowired
    private final DeliveredCommand deliveredCommand;

    public OrderStatusService(SubmittedToRestaurantCommand submittedToRestaurantCommand, OrderCancelCommand orderCancelCommand, KitchenPreparingCommand kitchenPreparingCommand, ReadyForDeliveryCommand readyForDeliveryCommand, OnDeliveryCommand onDeliveryCommand, DeliveredCommand deliveredCommand) {
        this.submittedToRestaurantCommand = submittedToRestaurantCommand;
        this.orderCancelCommand = orderCancelCommand;
        this.kitchenPreparingCommand = kitchenPreparingCommand;
        this.readyForDeliveryCommand = readyForDeliveryCommand;
        this.onDeliveryCommand = onDeliveryCommand;
        this.deliveredCommand = deliveredCommand;
    }

    public void submittedToRestaurant(String orderId) {
        submittedToRestaurantCommand.execute(orderId);
    }

    public void orderCancel(String orderId) {
        orderCancelCommand.execute(orderId);
    }

    public void kitchenPreparing(String orderId) {
        kitchenPreparingCommand.execute(orderId);
    }

    public void readyForDelivery(String orderId) {
        readyForDeliveryCommand.execute(orderId);
    }

    public void onDelivery(String orderId) {
        onDeliveryCommand.execute(orderId);
    }

    public void delivered(String orderId) {
        deliveredCommand.execute(orderId);
    }

}
