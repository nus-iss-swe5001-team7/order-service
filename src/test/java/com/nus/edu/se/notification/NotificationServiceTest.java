package com.nus.edu.se.notification;

import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationInterface notificationInterface;

    @Mock
    private OrderRepository orderRepository;

    private GroupFoodOrder mockGroupFoodOrder;
    private Order mockOrder1;
    private Order mockOrder2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock GroupFoodOrder
        mockGroupFoodOrder = new GroupFoodOrder();
        mockGroupFoodOrder.setId(UUID.randomUUID());

        // Mock Orders
        mockOrder1 = new Order();
        mockOrder1.setId(UUID.randomUUID());
        mockOrder1.setUserId(UUID.randomUUID());

        mockOrder2 = new Order();
        mockOrder2.setId(UUID.randomUUID());
        mockOrder2.setUserId(UUID.randomUUID());
    }

    @Test
    void testSendNotification_Success() {
        // Mock repository response
        List<Order> mockOrderList = Arrays.asList(mockOrder1, mockOrder2);
        when(orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(mockGroupFoodOrder))
                .thenReturn(mockOrderList);

        // Mock notification response
        when(notificationInterface.publish(any(MessageRequest.class)))
                .thenReturn(new ResponseEntity<>("Notification Sent", HttpStatus.OK));

        // Call the method under test
        notificationService.sendNotification(mockGroupFoodOrder, StatusEnum.DELIVERED);

        // Verify interactions
        verify(orderRepository, times(1))
                .findOrderByGroupFoodOrderOrderByCreatedTimeAsc(mockGroupFoodOrder);
        verify(notificationInterface, times(mockOrderList.size()))
                .publish(any(MessageRequest.class));
    }

    @Test
    void testSendNotification_NoOrders() {
        // Mock repository response with no orders
        when(orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(mockGroupFoodOrder))
                .thenReturn(List.of());

        // Call the method under test
        notificationService.sendNotification(mockGroupFoodOrder, StatusEnum.DELIVERED);

        // Verify no notification was sent
        verify(notificationInterface, times(0)).publish(any(MessageRequest.class));
    }

    @Test
    void testSendNotification_PartialFailure() {
        // Mock repository response
        List<Order> mockOrderList = Arrays.asList(mockOrder1, mockOrder2);
        when(orderRepository.findOrderByGroupFoodOrderOrderByCreatedTimeAsc(mockGroupFoodOrder))
                .thenReturn(mockOrderList);

        // Mock partial success in notification responses
        when(notificationInterface.publish(any(MessageRequest.class)))
                .thenReturn(new ResponseEntity<>("Notification Sent", HttpStatus.OK))
                .thenReturn(new ResponseEntity<>("Notification Failed", HttpStatus.INTERNAL_SERVER_ERROR));

        // Call the method under test
        notificationService.sendNotification(mockGroupFoodOrder, StatusEnum.DELIVERED);

        // Verify interactions
        verify(orderRepository, times(1))
                .findOrderByGroupFoodOrderOrderByCreatedTimeAsc(mockGroupFoodOrder);
        verify(notificationInterface, times(mockOrderList.size()))
                .publish(any(MessageRequest.class));
    }
}
