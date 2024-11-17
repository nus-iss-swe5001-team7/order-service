package com.nus.edu.se.mapper;

import com.nus.edu.se.groupfoodorder.dto.GroupFoodOrderResponse;
import com.nus.edu.se.groupfoodorder.model.GroupFoodOrder;
import com.nus.edu.se.groupfoodorder.model.StatusEnum;
import com.nus.edu.se.order.model.Order;
import com.nus.edu.se.restaurant.dto.RestaurantResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class GroupOrderMapperTest {

    private GroupOrderMapper groupOrderMapper;

    @BeforeEach
    void setUp() {
        groupOrderMapper = new GroupOrderMapper();
    }

    @Test
    void testFromGroupFoodOrderToGroupFoodOrderDTO() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID groupFoodOrderId = UUID.randomUUID();
        String location = "Downtown";
        String rating = "4.5";
        String restaurantAddress = "123 Food Street";
        String restaurantLatitude = "1.2345";
        String restaurantLongitude = "103.5678";
        Date createdTime = new Date();

        // Mock Order
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(orderId);

        // Mock GroupFoodOrder
        GroupFoodOrder groupFoodOrder = mock(GroupFoodOrder.class);
        when(groupFoodOrder.getId()).thenReturn(groupFoodOrderId);
        when(groupFoodOrder.getStatus()).thenReturn(StatusEnum.KITCHEN_PREPARING);
        when(groupFoodOrder.getGroupOrderCreateTime()).thenReturn(createdTime);

        // Mock RestaurantResponse
        RestaurantResponse restaurantResponse = mock(RestaurantResponse.class);
        when(restaurantResponse.getLocation()).thenReturn(location);
        when(restaurantResponse.getRating()).thenReturn(Float.parseFloat(rating));
        when(restaurantResponse.getRestaurantAddress()).thenReturn(restaurantAddress);
        when(restaurantResponse.getRestaurantLatitude()).thenReturn(restaurantLatitude);
        when(restaurantResponse.getRestaurantLongitude()).thenReturn(restaurantLongitude);

        // Act
        GroupFoodOrderResponse result = groupOrderMapper.fromGroupFoodOrderToGroupFoodOrderDTO(order, groupFoodOrder, restaurantResponse);

        // Assert
        assertEquals(orderId, result.getOrderId());
        assertEquals(groupFoodOrderId, result.getGroupFoodOrderId());
        assertEquals("KITCHEN_PREPARING", result.getOrderStatus());
        assertEquals(location, result.getLocation());
        assertEquals(Float.parseFloat(rating), result.getRating());
        assertEquals(restaurantAddress, result.getRestaurantAddress());
        assertEquals(restaurantLatitude, result.getRestaurantLatitude());
        assertEquals(restaurantLongitude, result.getRestaurantLongitude());

        // Allow multiple calls to getGroupOrderCreateTime
        verify(groupFoodOrder, atLeastOnce()).getGroupOrderCreateTime();
        verify(order, times(1)).getId();
        verify(groupFoodOrder, times(1)).getId();
        verify(groupFoodOrder, times(1)).getStatus();
        verify(restaurantResponse, times(1)).getLocation();
        verify(restaurantResponse, times(1)).getRestaurantAddress();
        verify(restaurantResponse, times(1)).getRestaurantLatitude();
        verify(restaurantResponse, times(1)).getRestaurantLongitude();
    }
}
