package com.nus.edu.se.order.dao;

import com.nus.edu.se.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
//    List<Order> findOrderByUsersModel(UsersModel usersModel);

//    List<Order> findOrderByGroupFoodOrderOrderByCreatedTimeAsc(GroupFoodOrder groupFoodOrder);

}
