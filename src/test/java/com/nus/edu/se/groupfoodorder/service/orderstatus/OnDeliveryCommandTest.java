package com.nus.edu.se.groupfoodorder.service.orderstatus;

import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OnDeliveryCommandTest {

    @InjectMocks
    private OnDeliveryCommand onDeliveryCommand;

    @Mock
    private GroupOrdersService groupOrdersService;

    private String validOrderId;
    private String nullOrderId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validOrderId = "123e4567-e89b-12d3-a456-426614174000"; // Example UUID string
        nullOrderId = null;
    }

    @Test
    void testExecute_WithValidOrderId() {
        // Act
        onDeliveryCommand.execute(validOrderId);

        // Assert
        verify(groupOrdersService, times(1)).updateStatus(validOrderId, StatusEnum.ON_DELIVERY);
    }

    @Test
    void testExecute_WithNullOrderId() {
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                onDeliveryCommand.execute(nullOrderId)
        );

        assertEquals("Order ID cannot be null", exception.getMessage());
        verify(groupOrdersService, times(0)).updateStatus(anyString(), any(StatusEnum.class));
    }
}
