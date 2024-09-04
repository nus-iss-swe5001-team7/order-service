package com.nus.edu.se.grouopfoodorder.dao;

import com.nus.edu.se.grouopfoodorder.model.GroupFoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupOrderRepository extends JpaRepository<GroupFoodOrder, UUID> {

    Optional<GroupFoodOrder> findGroupFoodOrderById(UUID id);
}
