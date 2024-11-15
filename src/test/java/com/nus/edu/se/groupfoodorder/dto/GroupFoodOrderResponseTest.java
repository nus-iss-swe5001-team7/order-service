package com.nus.edu.se.groupfoodorder.dto;

import com.nus.edu.se.order.dto.OrderDetailDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroupFoodOrderResponseTest {

    private GroupFoodOrderResponse groupFoodOrderResponse;

    @BeforeEach
    void setUp() {
        groupFoodOrderResponse = new GroupFoodOrderResponse();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default constructor initializes fields to null or default values
        assertNull(groupFoodOrderResponse.getOrderId());
        assertNull(groupFoodOrderResponse.getGroupFoodOrderId());
        assertNull(groupFoodOrderResponse.getOrderDetailDtoList());
        assertNull(groupFoodOrderResponse.getDeliveryTime());
        assertNull(groupFoodOrderResponse.getOrderTime());
        assertNull(groupFoodOrderResponse.getOrderStatus());
        assertEquals(0.0f, groupFoodOrderResponse.getTotalPrice());
        assertNull(groupFoodOrderResponse.getLocation());
        assertEquals(0.0f, groupFoodOrderResponse.getRating());
        assertNull(groupFoodOrderResponse.getDeliveryLocation());
        assertEquals(0.0f, groupFoodOrderResponse.getDeliveryFee());
        assertNull(groupFoodOrderResponse.getRestaurantName());
        assertNull(groupFoodOrderResponse.getOrderIdsList());
        assertNull(groupFoodOrderResponse.getDeliveryAddress());
        assertNull(groupFoodOrderResponse.getDeliveryLatitude());
        assertNull(groupFoodOrderResponse.getDeliveryLongitude());
        assertNull(groupFoodOrderResponse.getRestaurantAddress());
        assertNull(groupFoodOrderResponse.getRestaurantLatitude());
        assertNull(groupFoodOrderResponse.getRestaurantLongitude());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID groupFoodOrderId = UUID.randomUUID();
        List<OrderDetailDTO> orderDetailDtoList = List.of(new OrderDetailDTO());
        Date deliveryTime = new Date();
        Date orderTime = new Date();
        String orderStatus = "DELIVERED";
        float totalPrice = 100.50f;
        String location = "City Center";
        float rating = 4.5f;
        String deliveryLocation = "123 Delivery Street";
        float deliveryFee = 10.0f;
        String restaurantName = "Food Paradise";
        List<String> orderIdsList = List.of("order1", "order2");
        String deliveryAddress = "456 Delivery Lane";
        String deliveryLatitude = "1.23456";
        String deliveryLongitude = "103.98765";
        String restaurantAddress = "789 Restaurant Blvd";
        String restaurantLatitude = "1.54321";
        String restaurantLongitude = "103.65432";

        // Act
        GroupFoodOrderResponse groupFoodOrderResponse = new GroupFoodOrderResponse(
                orderId,
                groupFoodOrderId,
                orderDetailDtoList,
                deliveryTime,
                orderTime,
                orderStatus,
                totalPrice,
                location,
                rating,
                deliveryLocation,
                deliveryFee,
                restaurantName,
                orderIdsList,
                deliveryAddress,
                deliveryLatitude,
                deliveryLongitude,
                restaurantAddress,
                restaurantLatitude,
                restaurantLongitude
        );

        // Assert
        assertEquals(orderId, groupFoodOrderResponse.getOrderId());
        assertEquals(groupFoodOrderId, groupFoodOrderResponse.getGroupFoodOrderId());
        assertEquals(orderDetailDtoList, groupFoodOrderResponse.getOrderDetailDtoList());
        assertEquals(deliveryTime, groupFoodOrderResponse.getDeliveryTime());
        assertEquals(orderTime, groupFoodOrderResponse.getOrderTime());
        assertEquals(orderStatus, groupFoodOrderResponse.getOrderStatus());
        assertEquals(totalPrice, groupFoodOrderResponse.getTotalPrice());
        assertEquals(location, groupFoodOrderResponse.getLocation());
        assertEquals(rating, groupFoodOrderResponse.getRating());
        assertEquals(deliveryLocation, groupFoodOrderResponse.getDeliveryLocation());
        assertEquals(deliveryFee, groupFoodOrderResponse.getDeliveryFee());
        assertEquals(restaurantName, groupFoodOrderResponse.getRestaurantName());
        assertEquals(orderIdsList, groupFoodOrderResponse.getOrderIdsList());
        assertEquals(deliveryAddress, groupFoodOrderResponse.getDeliveryAddress());
        assertEquals(deliveryLatitude, groupFoodOrderResponse.getDeliveryLatitude());
        assertEquals(deliveryLongitude, groupFoodOrderResponse.getDeliveryLongitude());
        assertEquals(restaurantAddress, groupFoodOrderResponse.getRestaurantAddress());
        assertEquals(restaurantLatitude, groupFoodOrderResponse.getRestaurantLatitude());
        assertEquals(restaurantLongitude, groupFoodOrderResponse.getRestaurantLongitude());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UUID orderId = UUID.randomUUID();
        UUID groupFoodOrderId = UUID.randomUUID();
        List<OrderDetailDTO> orderDetailDtoList = List.of(new OrderDetailDTO());
        Date deliveryTime = new Date();
        Date orderTime = new Date();
        String orderStatus = "PENDING";
        float totalPrice = 50.75f;
        String location = "Suburb";
        float rating = 3.8f;
        String deliveryLocation = "456 Delivery Lane";
        float deliveryFee = 5.0f;
        String restaurantName = "Gourmet Eatery";
        List<String> orderIdsList = List.of("order3", "order4");
        String deliveryAddress = "789 Delivery Lane";
        String deliveryLatitude = "1.67890";
        String deliveryLongitude = "103.12345";
        String restaurantAddress = "123 Gourmet Blvd";
        String restaurantLatitude = "1.98765";
        String restaurantLongitude = "103.45678";

        // Act
        groupFoodOrderResponse.setOrderId(orderId);
        groupFoodOrderResponse.setGroupFoodOrderId(groupFoodOrderId);
        groupFoodOrderResponse.setOrderDetailDtoList(orderDetailDtoList);
        groupFoodOrderResponse.setDeliveryTime(deliveryTime);
        groupFoodOrderResponse.setOrderTime(orderTime);
        groupFoodOrderResponse.setOrderStatus(orderStatus);
        groupFoodOrderResponse.setTotalPrice(totalPrice);
        groupFoodOrderResponse.setLocation(location);
        groupFoodOrderResponse.setRating(rating);
        groupFoodOrderResponse.setDeliveryLocation(deliveryLocation);
        groupFoodOrderResponse.setDeliveryFee(deliveryFee);
        groupFoodOrderResponse.setRestaurantName(restaurantName);
        groupFoodOrderResponse.setOrderIdsList(orderIdsList);
        groupFoodOrderResponse.setDeliveryAddress(deliveryAddress);
        groupFoodOrderResponse.setDeliveryLatitude(deliveryLatitude);
        groupFoodOrderResponse.setDeliveryLongitude(deliveryLongitude);
        groupFoodOrderResponse.setRestaurantAddress(restaurantAddress);
        groupFoodOrderResponse.setRestaurantLatitude(restaurantLatitude);
        groupFoodOrderResponse.setRestaurantLongitude(restaurantLongitude);

        // Assert
        assertEquals(orderId, groupFoodOrderResponse.getOrderId());
        assertEquals(groupFoodOrderId, groupFoodOrderResponse.getGroupFoodOrderId());
        assertEquals(orderDetailDtoList, groupFoodOrderResponse.getOrderDetailDtoList());
        assertEquals(deliveryTime, groupFoodOrderResponse.getDeliveryTime());
        assertEquals(orderTime, groupFoodOrderResponse.getOrderTime());
        assertEquals(orderStatus, groupFoodOrderResponse.getOrderStatus());
        assertEquals(totalPrice, groupFoodOrderResponse.getTotalPrice());
        assertEquals(location, groupFoodOrderResponse.getLocation());
        assertEquals(rating, groupFoodOrderResponse.getRating());
        assertEquals(deliveryLocation, groupFoodOrderResponse.getDeliveryLocation());
        assertEquals(deliveryFee, groupFoodOrderResponse.getDeliveryFee());
        assertEquals(restaurantName, groupFoodOrderResponse.getRestaurantName());
        assertEquals(orderIdsList, groupFoodOrderResponse.getOrderIdsList());
        assertEquals(deliveryAddress, groupFoodOrderResponse.getDeliveryAddress());
        assertEquals(deliveryLatitude, groupFoodOrderResponse.getDeliveryLatitude());
        assertEquals(deliveryLongitude, groupFoodOrderResponse.getDeliveryLongitude());
        assertEquals(restaurantAddress, groupFoodOrderResponse.getRestaurantAddress());
        assertEquals(restaurantLatitude, groupFoodOrderResponse.getRestaurantLatitude());
        assertEquals(restaurantLongitude, groupFoodOrderResponse.getRestaurantLongitude());
    }
}
