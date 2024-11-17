package com.nus.edu.se.payment.pricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HappyDealsPromoDecoratorTest {

    @Test
    void testCalculatePrice() {
        // Arrange: Create a base pricing strategy and wrap it with the HappyDealsPromoDecorator
        PricingStrategy baseStrategy = price -> price; // A base strategy that does not change the price
        PricingStrategy promoDecorator = new HappyDealsPromoDecorator(baseStrategy);

        // Act: Calculate the price after applying the HappyDealsPromoDecorator
        float originalPrice = 200.0f; // Example total price
        float discountedPrice = promoDecorator.calculatePrice(originalPrice);

        // Assert: Verify that the price is correctly discounted by 5%
        assertEquals(190.0f, discountedPrice, 0.01f, "Price should be discounted by 5%");
    }

    @Test
    void testCalculatePriceWithZero() {
        // Arrange: Create a base pricing strategy and wrap it with the HappyDealsPromoDecorator
        PricingStrategy baseStrategy = price -> price; // A base strategy that does not change the price
        PricingStrategy promoDecorator = new HappyDealsPromoDecorator(baseStrategy);

        // Act: Calculate the price when the original price is zero
        float originalPrice = 0.0f;
        float discountedPrice = promoDecorator.calculatePrice(originalPrice);

        // Assert: Verify that the discounted price is zero
        assertEquals(0.0f, discountedPrice, 0.01f, "Price should remain zero if the original price is zero");
    }

    @Test
    void testCalculatePriceWithBaseStrategy() {
        // Arrange: Create a base pricing strategy with a 10% discount and wrap it with the HappyDealsPromoDecorator
        PricingStrategy baseStrategy = price -> price * 0.90f; // Base strategy applies a 10% discount
        PricingStrategy promoDecorator = new HappyDealsPromoDecorator(baseStrategy);

        // Act: Calculate the price with both the base strategy and the HappyDealsPromoDecorator
        float originalPrice = 100.0f;
        float discountedPrice = promoDecorator.calculatePrice(originalPrice);

        // Assert: Verify that the total discount is correctly compounded
        float expectedPrice = originalPrice * 0.90f * 0.95f; // First 10% discount, then 5% discount
        assertEquals(expectedPrice, discountedPrice, 0.01f, "Price should reflect compounded discounts of 10% and 5%");
    }
}
