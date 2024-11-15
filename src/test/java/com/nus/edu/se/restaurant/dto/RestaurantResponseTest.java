package com.nus.edu.se.restaurant.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantResponseTest {

    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        restaurantResponse = new RestaurantResponse();
    }

    @Test
    void testDefaultConstructor() {
        // Verify default constructor initializes fields to null or default values
        assertNull(restaurantResponse.getId());
        assertNull(restaurantResponse.getRestaurantName());
        assertNull(restaurantResponse.getRestaurantAddress());
        assertNull(restaurantResponse.getRestaurantLatitude());
        assertNull(restaurantResponse.getRestaurantLongitude());
        assertNull(restaurantResponse.getCuisineType());
        assertNull(restaurantResponse.getLocation());
        assertEquals(0.0f, restaurantResponse.getRating());
        assertNull(restaurantResponse.getRestaurantImgURL());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String id = "123";
        String restaurantName = "Test Restaurant";
        String restaurantAddress = "123 Test Street";
        String restaurantLatitude = "1.23456";
        String restaurantLongitude = "103.98765";
        String cuisineType = "Italian";
        String location = "Test City";
        float rating = 4.5f;
        String restaurantImgURL = "http://example.com/image.jpg";

        // Act
        RestaurantResponse restaurantResponse = new RestaurantResponse(
                id,
                restaurantName,
                restaurantAddress,
                restaurantLatitude,
                restaurantLongitude,
                cuisineType,
                location,
                rating,
                restaurantImgURL
        );

        // Assert
        assertEquals(id, restaurantResponse.getId());
        assertEquals(restaurantName, restaurantResponse.getRestaurantName());
        assertEquals(restaurantAddress, restaurantResponse.getRestaurantAddress());
        assertEquals(restaurantLatitude, restaurantResponse.getRestaurantLatitude());
        assertEquals(restaurantLongitude, restaurantResponse.getRestaurantLongitude());
        assertEquals(cuisineType, restaurantResponse.getCuisineType());
        assertEquals(location, restaurantResponse.getLocation());
        assertEquals(rating, restaurantResponse.getRating());
        assertEquals(restaurantImgURL, restaurantResponse.getRestaurantImgURL());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        String id = "456";
        String restaurantName = "Another Restaurant";
        String restaurantAddress = "456 Another Street";
        String restaurantLatitude = "2.34567";
        String restaurantLongitude = "104.87654";
        String cuisineType = "Mexican";
        String location = "Another City";
        float rating = 3.8f;
        String restaurantImgURL = "http://example.com/another-image.jpg";

        // Act
        restaurantResponse.setId(id);
        restaurantResponse.setRestaurantName(restaurantName);
        restaurantResponse.setRestaurantAddress(restaurantAddress);
        restaurantResponse.setRestaurantLatitude(restaurantLatitude);
        restaurantResponse.setRestaurantLongitude(restaurantLongitude);
        restaurantResponse.setCuisineType(cuisineType);
        restaurantResponse.setLocation(location);
        restaurantResponse.setRating(rating);
        restaurantResponse.setRestaurantImgURL(restaurantImgURL);

        // Assert
        assertEquals(id, restaurantResponse.getId());
        assertEquals(restaurantName, restaurantResponse.getRestaurantName());
        assertEquals(restaurantAddress, restaurantResponse.getRestaurantAddress());
        assertEquals(restaurantLatitude, restaurantResponse.getRestaurantLatitude());
        assertEquals(restaurantLongitude, restaurantResponse.getRestaurantLongitude());
        assertEquals(cuisineType, restaurantResponse.getCuisineType());
        assertEquals(location, restaurantResponse.getLocation());
        assertEquals(rating, restaurantResponse.getRating());
        assertEquals(restaurantImgURL, restaurantResponse.getRestaurantImgURL());
    }
}
