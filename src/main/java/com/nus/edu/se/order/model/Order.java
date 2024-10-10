package com.nus.edu.se.order.model;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.UuidGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "order_items")
public class Order {

    public enum PaymentStatus {PENDING, PROCESSING, COMPLETED, FAILED};

    @Id
    @UuidGenerator
    @Column(name = "order_item_id", nullable = false)
    private UUID id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false, updatable = false)
//    private UsersModel usersModel;
    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "group_food_order_id", nullable = false, updatable = false)
    private GroupFoodOrder groupFoodOrder;

    @Column(name = "order_created_time", nullable = false)
    private Date createdTime;

 /*   @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false, updatable = false)
    private Restaurant restaurant;*/
    @Column(name = "restaurant_id", nullable = false, updatable = false)
    private String restaurantId;

    @Column(name = "delivery_fee", nullable = false)
    private float deliveryFee;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    public Order() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public GroupFoodOrder getGroupFoodOrder() {
        return groupFoodOrder;
    }

    public void setGroupFoodOrder(GroupFoodOrder groupFoodOrder) {
        this.groupFoodOrder = groupFoodOrder;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public float getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(float deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

}
