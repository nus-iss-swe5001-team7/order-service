package com.nus.edu.se.order.dao;

import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> findOrderDetailsByOrderItemId(UUID order);
}
