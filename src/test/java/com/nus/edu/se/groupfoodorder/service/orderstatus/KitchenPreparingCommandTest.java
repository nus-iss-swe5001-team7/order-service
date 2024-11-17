package com.nus.edu.se.groupfoodorder.service.orderstatus;

import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KitchenPreparingCommandTest {

    @InjectMocks
    private KitchenPreparingCommand kitchenPreparingCommand;

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
        kitchenPreparingCommand.execute(validOrderId);

        // Assert
        verify(groupOrdersService, times(1)).updateStatus(validOrderId, StatusEnum.KITCHEN_PREPARING);
    }

    @Test
    void testExecute_WithNullOrderId() {
        // Act and Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                kitchenPreparingCommand.execute(nullOrderId)
        );

        assertEquals("Order ID cannot be null", exception.getMessage());
        verify(groupOrdersService, times(0)).updateStatus(anyString(), any(StatusEnum.class));
    }
}

