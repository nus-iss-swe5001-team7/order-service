package com.nus.edu.se.order.mapper;

import com.nus.edu.se.menu.dto.MenuResponse;
import com.nus.edu.se.menu.service.MenuService;
import com.nus.edu.se.order.dto.OrderDetailDTO;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.order.model.OrderDetail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderDetailMapperTest {

    @InjectMocks
    private OrderDetailMapper orderDetailMapper;

    @Mock
    private MenuService menuService;

    private String token;
    private MenuResponse mockMenuResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        token = "dummyToken";

        mockMenuResponse = new MenuResponse();
        mockMenuResponse.setId("menu123");
        mockMenuResponse.setMenuName("Pizza");
        mockMenuResponse.setMenuPrice(10.99f);
        mockMenuResponse.setMenuImageURL("http://example.com/pizza.jpg");
        mockMenuResponse.setDescription("Delicious pizza");
    }

    @Test
    void testFromOrderDetailDTOToOrderDetail() {
        // Arrange
        Order mockOrder = new Order();
        mockOrder.setId(UUID.randomUUID());

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setMenuId("menu123");
        orderDetailDTO.setQuantity(2);
        orderDetailDTO.setPreferences(Map.of("extraCheese", "yes"));

        when(menuService.findById(orderDetailDTO.getMenuId(), token)).thenReturn(mockMenuResponse);

        // Act
        OrderDetail orderDetail = orderDetailMapper.fromOrderDetailDTOToOrderDetail(orderDetailDTO, mockOrder, token);

        // Assert
        assertEquals(mockMenuResponse.getId(), orderDetail.getMenuId());
        assertEquals(mockOrder, orderDetail.getOrder());
        assertEquals(orderDetailDTO.getQuantity(), orderDetail.getQuantity());
        assertEquals(orderDetailDTO.getPreferences(), orderDetail.getPreferences());
        verify(menuService, times(1)).findById(orderDetailDTO.getMenuId(), token);
    }

    @Test
    void testFromOrderDetailToOrderDetailDTOList() {
        // Arrange
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setMenuId("menu123");
        orderDetail.setQuantity(1);
        orderDetail.setPreferences(Map.of("noOnions", "true"));

        List<OrderDetail> orderDetails = List.of(orderDetail);

        when(menuService.findById(orderDetail.getMenuId(), token)).thenReturn(mockMenuResponse);

        // Act
        List<OrderDetailDTO> orderDetailDTOs = orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetails, token);

        // Assert
        assertEquals(1, orderDetailDTOs.size());
        OrderDetailDTO dto = orderDetailDTOs.get(0);
        assertEquals(orderDetail.getMenuId(), dto.getMenuId());
        assertEquals(mockMenuResponse.getMenuName(), dto.getName());
        assertEquals(mockMenuResponse.getMenuPrice(), dto.getPrice());
        assertEquals(orderDetail.getQuantity(), dto.getQuantity());
        assertEquals(mockMenuResponse.getDescription(), dto.getDescription());
        assertEquals(mockMenuResponse.getMenuImageURL(), dto.getImgUrl());
        assertEquals(orderDetail.getPreferences(), dto.getPreferences());
        verify(menuService, times(1)).findById(orderDetail.getMenuId(), token);
    }

    @Test
    void testFromOrderDetailToOrderDetailDTO() {
        // Arrange
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setQuantity(2);
        orderDetail.setPreferences(Map.of("spicy", "yes"));

        // Act
        OrderDetailDTO dto = orderDetailMapper.fromOrderDetailToOrderDetailDTO(orderDetail, mockMenuResponse);

        // Assert
        assertEquals(orderDetail.getQuantity(), dto.getQuantity());
        assertEquals(mockMenuResponse.getMenuName(), dto.getName());
        assertEquals(mockMenuResponse.getMenuImageURL(), dto.getImgUrl());
        assertEquals(mockMenuResponse.getDescription(), dto.getDescription());
        assertEquals(mockMenuResponse.getMenuPrice(), dto.getPrice());
        assertEquals(orderDetail.getPreferences(), dto.getPreferences());
    }
}
