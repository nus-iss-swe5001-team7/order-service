package com.nus.edu.se.payment.boundary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nus.edu.se.exception.DataNotFoundException;
import com.nus.edu.se.groupfoodorder.dao.GroupOrderRepository;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.service.GroupOrdersService;
import com.nus.edu.se.groupfoodorder.service.JwtTokenInterface;
import com.nus.edu.se.order.dao.OrderRepository;
import com.nus.edu.se.order.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.websocket.AuthenticationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentResourceTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private PaymentResource paymentResource;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private GroupOrderRepository groupOrderRepository;

    @Mock
    private GroupOrdersService groupOrdersService;

    @Mock
    private JwtTokenInterface jwtTokenInterface;

    @Mock
    private HttpServletRequest request;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private UUID validOrderId;
    private UUID invalidOrderId;
    private UUID validGroupOrderId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validOrderId = UUID.randomUUID();
        invalidOrderId = UUID.randomUUID();
        validGroupOrderId = UUID.randomUUID();
    }

    @Test
    void testUpdatePaymentStatus_Success() throws Exception {
        // Prepare JSON payload with valid selectedPaymentMethod
        String payload = """
        {
            "orderItemId": "3c7416be-83e7-4b7e-8e58-a6f0495cac27",
            "paymentStatus": "COMPLETED",
            "isForShow": false,
            "paymentType": "creditCard"
        }
        """;

        // Mock token validation
        when(jwtTokenInterface.validateToken(anyString())).thenReturn(ResponseEntity.ok(true));
        when(groupOrdersService.resolveToken(any(HttpServletRequest.class))).thenReturn("valid-token");

        // Mock order retrieval
        UUID orderId = UUID.fromString("3c7416be-83e7-4b7e-8e58-a6f0495cac27");
        Order mockOrder = new Order();
        mockOrder.setId(orderId);
        mockOrder.setPaymentStatus(Order.PaymentStatus.PENDING);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(mockOrder));

        // Call the API
        ResponseEntity<String> response = paymentResource.updatePaymentStatus(mapper.readTree(payload), mock(HttpServletRequest.class));

        // Assertions
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payment status updated successfully", response.getBody());
    }


    @Test
    void testUpdatePaymentStatus_OrderNotFound() throws Exception {
        // Prepare JSON payload
        String payload = """
        {
            "orderItemId": "5fcf7e6f-24b1-4a4c-9c7b-264716a4161d",
            "paymentStatus": "COMPLETED",
            "paymentType": "creditCard"
        }
        """;

        // Mock token validation
        when(jwtTokenInterface.validateToken(anyString())).thenReturn(ResponseEntity.ok(true));
        when(groupOrdersService.resolveToken(any())).thenReturn("valid-token");

        // Mock order not found
        when(orderRepository.findById(UUID.fromString("5fcf7e6f-24b1-4a4c-9c7b-264716a4161d"))).thenReturn(Optional.empty());

        // Call the API
        ResponseEntity<String> response = paymentResource.updatePaymentStatus(mapper.readTree(payload), mock(HttpServletRequest.class));

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Order not found", response.getBody());
    }


    @Test
    void testUpdatePaymentStatus_InvalidToken() {
        // Arrange
        ObjectNode paymentDetails = objectMapper.createObjectNode();
        paymentDetails.put("orderItemId", validOrderId.toString());
        paymentDetails.put("paymentStatus", "COMPLETED");

        when(groupOrdersService.resolveToken(request)).thenReturn("invalidToken");
        when(jwtTokenInterface.validateToken("invalidToken")).thenReturn(ResponseEntity.ok(false));

        // Act & Assert
        Exception exception = assertThrows(org.apache.tomcat.websocket.AuthenticationException.class, () -> {
            paymentResource.updatePaymentStatus(paymentDetails, request);
        });

        assertEquals("User is not authenticated to updatePaymentStatus!", exception.getMessage());
    }

    @Test
    void testUpdatePaymentStatus_UpdateStatusWithSwitch() {
        // Arrange
        Order order = new Order();
        order.setId(validOrderId);
        order.setPaymentStatus(Order.PaymentStatus.PENDING);
        when(orderRepository.findById(validOrderId)).thenReturn(Optional.of(order));

        String newPaymentStatus = "{\"paymentStatus\":\"COMPLETED\"}";

        // Act
        ResponseEntity<Order> response = paymentResource.updatePaymentStatus(validOrderId, newPaymentStatus);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Order.PaymentStatus.COMPLETED, response.getBody().getPaymentStatus());
    }

    @Test
    void testUpdatePaymentStatus_InvalidOrderIdFormat() throws Exception {
        // Prepare JSON payload with invalid UUID format
        String payload = """
        {
            "orderItemId": "invalid-uuid-format",
            "paymentStatus": "COMPLETED",
            "paymentType": "creditCard"
        }
        """;

        // Mock token validation
        when(jwtTokenInterface.validateToken(anyString())).thenReturn(ResponseEntity.ok(true));
        when(groupOrdersService.resolveToken(any())).thenReturn("valid-token");

        // Call the API
        ResponseEntity<String> response = paymentResource.updatePaymentStatus(mapper.readTree(payload), mock(HttpServletRequest.class));

        // Assertions
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid orderItemId format", response.getBody());
    }

    

}
