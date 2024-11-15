package com.nus.edu.se.order.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nus.edu.se.groupfoodorder.dto.OrderResponse;
import com.nus.edu.se.order.dto.OrderDetailDTO;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderMapperTest {

    @InjectMocks
    private OrderMapper orderMapper;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    private Order order;
    private List<OrderDetail> orderDetails;
    private String token;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialize test data
        token = "dummyToken";
        order = new Order();
        order.setId(UUID.randomUUID());
        order.setGroupFoodOrder(new com.nus.edu.se.groupfoodorder.model.GroupFoodOrder());
        order.getGroupFoodOrder().setId(UUID.randomUUID());
        order.getGroupFoodOrder().setDeliveryLocation("City Center");
        order.getGroupFoodOrder().setDeliveryAddress("123 Main Street");
        order.getGroupFoodOrder().setDeliveryLatitude("1.2345");
        order.getGroupFoodOrder().setDeliveryLongitude("103.5678");
        order.setRestaurantId("restaurant123");
        order.setUserId(UUID.randomUUID());
        order.setCreatedTime(new Date());
        order.setDeliveryFee(15.0f);
        order.setPaymentStatus(Order.PaymentStatus.PROCESSING);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setMenuId("menu123");
        orderDetail.setQuantity(2);
        orderDetails = Collections.singletonList(orderDetail);
    }

    @Test
    void testFromOrderToOrderDTO_Success() throws JsonProcessingException {
        // Arrange
        when(orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails, token))
                .thenReturn(Collections.singletonList(new OrderDetailDTO()));

        // Act
        OrderResponse orderResponse = orderMapper.fromOrderToOrderDTO(order, orderDetails, token);

        // Assert
        assertNotNull(orderResponse);
        assertEquals(order.getId().toString(), orderResponse.getId());
        assertEquals(order.getGroupFoodOrder().getId().toString(), orderResponse.getGroupFoodOrderId());
        assertEquals(order.getRestaurantId(), orderResponse.getRestaurantId());
        assertEquals(order.getUserId().toString(), orderResponse.getUserId());
        assertEquals(order.getCreatedTime(), orderResponse.getCreatedTime());
        assertEquals(order.getGroupFoodOrder().getDeliveryLocation(), orderResponse.getLocation());
        assertEquals(order.getGroupFoodOrder().getDeliveryAddress(), orderResponse.getDeliveryAddress());
        assertEquals(order.getGroupFoodOrder().getDeliveryLatitude(), orderResponse.getDeliveryLatitude());
        assertEquals(order.getGroupFoodOrder().getDeliveryLongitude(), orderResponse.getDeliveryLongitude());
        assertEquals(order.getDeliveryFee(), orderResponse.getDeliveryFee());
        assertEquals(order.getPaymentStatus(), orderResponse.getPaymentStatus());

        verify(orderDetailMapper, times(1)).fromOrderDetailToOrderDetailDTO(orderDetails, token);
    }

    @Test
    void testCovertOrderDetailsToJson_Success() throws JsonProcessingException {
        // Arrange
        List<OrderDetailDTO> orderDetailDTOs = Collections.singletonList(new OrderDetailDTO());
        when(orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails, token)).thenReturn(orderDetailDTOs);

        // Act
        String jsonResult = orderMapper.covertOrderDetailsToJson(orderDetails, token);

        // Assert
        assertNotNull(jsonResult);
        assertTrue(jsonResult.startsWith("["));
        verify(orderDetailMapper, times(1)).fromOrderDetailToOrderDetailDTO(orderDetails, token);
    }

    @Test
    void testCovertOrderDetailsToJson_JsonProcessingException() {
        // Arrange
        when(orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails, token)).thenThrow(new RuntimeException("Mocked Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> orderMapper.covertOrderDetailsToJson(orderDetails, token));
        verify(orderDetailMapper, times(1)).fromOrderDetailToOrderDetailDTO(orderDetails, token);
    }
}
