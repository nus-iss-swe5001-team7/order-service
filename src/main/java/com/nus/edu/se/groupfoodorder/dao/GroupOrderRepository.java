package com.nus.edu.se.groupfoodorder.dao;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupOrderRepository extends JpaRepository<GroupFoodOrder, UUID> {

    Optional<GroupFoodOrder> findGroupFoodOrderById(UUID id);

    List<GroupFoodOrder> findGroupFoodOrderByStatus(StatusEnum status);
}
