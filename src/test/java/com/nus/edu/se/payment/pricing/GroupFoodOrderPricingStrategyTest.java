package com.nus.edu.se.payment.pricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupFoodOrderPricingStrategyTest {

    @Test
    void testCalculatePrice() {
        // Arrange: Create an instance of GroupFoodOrderPricingStrategy
        PricingStrategy pricingStrategy = new GroupFoodOrderPricingStrategy();

        // Act: Calculate the price for a given total price
        float originalPrice = 100.0f; // Example total price
        float discountedPrice = pricingStrategy.calculatePrice(originalPrice);

        // Assert: Verify that the price is correctly discounted by 2%
        assertEquals(98.0f, discountedPrice, 0.01f, "Price should be discounted by 2%");
    }

    @Test
    void testCalculatePriceWithZero() {
        // Arrange: Create an instance of GroupFoodOrderPricingStrategy
        PricingStrategy pricingStrategy = new GroupFoodOrderPricingStrategy();

        // Act: Calculate the price when the original price is zero
        float originalPrice = 0.0f; // Example total price
        float discountedPrice = pricingStrategy.calculatePrice(originalPrice);

        // Assert: Verify that the discounted price is zero
        assertEquals(0.0f, discountedPrice, 0.01f, "Price should remain zero if the original price is zero");
    }

    @Test
    void testCalculatePriceWithLargeValue() {
        // Arrange: Create an instance of GroupFoodOrderPricingStrategy
        PricingStrategy pricingStrategy = new GroupFoodOrderPricingStrategy();

        // Act: Calculate the price for a large total price
        float originalPrice = 1_000_000.0f; // Large total price
        float discountedPrice = pricingStrategy.calculatePrice(originalPrice);

        // Assert: Verify that the price is correctly discounted by 2%
        assertEquals(980_000.0f, discountedPrice, 0.01f, "Price should be correctly discounted by 2%");
    }
}
