package com.nus.edu.se.groupfoodorder.dao;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GroupOrderRepositoryTest {

    @Autowired
    private GroupOrderRepository groupOrderRepository;

    private GroupFoodOrder savedPendingOrder;
    private GroupFoodOrder savedOnDeliveryOrder;

    @BeforeEach
    void setUp() {
        // Prepare test data
        GroupFoodOrder pendingOrder = new GroupFoodOrder();
        pendingOrder.setId(UUID.randomUUID());
        pendingOrder.setStatus(StatusEnum.PENDING_USER_JOIN);

        GroupFoodOrder onDeliveryOrder = new GroupFoodOrder();
        onDeliveryOrder.setId(UUID.randomUUID());
        onDeliveryOrder.setStatus(StatusEnum.ON_DELIVERY);

        // Save to the database
        savedPendingOrder = groupOrderRepository.save(pendingOrder);
        savedOnDeliveryOrder = groupOrderRepository.save(onDeliveryOrder);
    }

    @Test
    void testFindGroupFoodOrderById_Success() {
        // Act
        Optional<GroupFoodOrder> foundOrder = groupOrderRepository.findGroupFoodOrderById(savedPendingOrder.getId());

        // Assert
        assertTrue(foundOrder.isPresent());
        assertEquals(savedPendingOrder.getId(), foundOrder.get().getId());
        assertEquals(StatusEnum.PENDING_USER_JOIN, foundOrder.get().getStatus());
    }

    @Test
    void testFindGroupFoodOrderById_NotFound() {
        // Act
        Optional<GroupFoodOrder> foundOrder = groupOrderRepository.findGroupFoodOrderById(UUID.randomUUID());

        // Assert
        assertFalse(foundOrder.isPresent());
    }

    @Test
    void testFindGroupFoodOrderByStatus_PendingUserJoin() {
        // Act
        List<GroupFoodOrder> pendingOrders = groupOrderRepository.findGroupFoodOrderByStatus(StatusEnum.PENDING_USER_JOIN);

        // Assert
        assertNotNull(pendingOrders);
        assertEquals(1, pendingOrders.size());
        assertEquals(savedPendingOrder.getId(), pendingOrders.get(0).getId());
        assertEquals(StatusEnum.PENDING_USER_JOIN, pendingOrders.get(0).getStatus());
    }

    @Test
    void testFindGroupFoodOrderByStatus_OnDelivery() {
        // Act
        List<GroupFoodOrder> onDeliveryOrders = groupOrderRepository.findGroupFoodOrderByStatus(StatusEnum.ON_DELIVERY);

        // Assert
        assertNotNull(onDeliveryOrders);
        assertEquals(1, onDeliveryOrders.size());
        assertEquals(savedOnDeliveryOrder.getId(), onDeliveryOrders.get(0).getId());
        assertEquals(StatusEnum.ON_DELIVERY, onDeliveryOrders.get(0).getStatus());
    }

    @Test
    void testFindGroupFoodOrderByStatus_NoResults() {
        // Act
        List<GroupFoodOrder> canceledOrders = groupOrderRepository.findGroupFoodOrderByStatus(StatusEnum.ORDER_CANCEL);

        // Assert
        assertNotNull(canceledOrders);
        assertTrue(canceledOrders.isEmpty());
    }
}
