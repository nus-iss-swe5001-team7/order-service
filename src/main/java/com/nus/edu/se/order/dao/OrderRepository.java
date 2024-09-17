package com.nus.edu.se.order.dao;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findOrderByUserId(UUID userId);

    List<Order> findOrderByGroupFoodOrderOrderByCreatedTimeAsc(GroupFoodOrder groupFoodOrder);

}
