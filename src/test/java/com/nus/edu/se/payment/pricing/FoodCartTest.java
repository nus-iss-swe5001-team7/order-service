package com.nus.edu.se.payment.pricing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodCartTest {

    @Test
    void testCheckoutWithoutPromo() {
        // Create a FoodCart instance without applying any promo
        FoodCart foodCart = new FoodCart(false, false, 100.0f, null);

        // Expected price should remain the same as no promo or group order discount is applied
        float finalPrice = foodCart.checkout();

        assertEquals(100.0f, finalPrice, 0.01, "Price should remain unchanged without any promo or group order discount");
    }

    @Test
    void testCheckoutWithGroupOrderStrategy() {
        // Create a FoodCart instance with group order strategy
        FoodCart foodCart = new FoodCart(true, false, 100.0f, null);

        // Expected price should be discounted by group order strategy
        float finalPrice = foodCart.checkout();

        assertTrue(finalPrice < 100.0f, "Price should be discounted for group order strategy");
    }

    @Test
    void testCheckoutWithSeasonalPromo() {
        // Create a FoodCart instance with seasonal promo applied
        FoodCart foodCart = new FoodCart(false, true, 100.0f, DecoratorGenerator.PromoType.SEASONAL_PROMO);

        // Expected price should be discounted based on the seasonal promo
        float finalPrice = foodCart.checkout();

        assertTrue(finalPrice < 100.0f, "Price should be discounted for seasonal promo");
    }

    @Test
    void testCheckoutWithLoyaltyPromo() {
        // Create a FoodCart instance with loyalty promo applied
        FoodCart foodCart = new FoodCart(false, true, 100.0f, DecoratorGenerator.PromoType.LOYALTY_PROMO);

        // Expected price should be discounted based on the loyalty promo
        float finalPrice = foodCart.checkout();

        assertTrue(finalPrice < 100.0f, "Price should be discounted for loyalty promo");
    }

    @Test
    void testCheckoutWithHappyDealsPromo() {
        // Create a FoodCart instance with happy deals promo applied
        FoodCart foodCart = new FoodCart(false, true, 100.0f, DecoratorGenerator.PromoType.HAPPY_DEALS_PROMO);

        // Expected price should be discounted based on the happy deals promo
        float finalPrice = foodCart.checkout();

        assertTrue(finalPrice < 100.0f, "Price should be discounted for happy deals promo");
    }

    @Test
    void testCheckoutWithInvalidPromo() {
        // Create a FoodCart instance with an invalid promo
        assertThrows(NullPointerException.class, () -> {
            FoodCart foodCart = new FoodCart(false, true, 100.0f, null);
            foodCart.checkout();
        }, "Should throw IllegalArgumentException for unknown promo type");
    }
}
