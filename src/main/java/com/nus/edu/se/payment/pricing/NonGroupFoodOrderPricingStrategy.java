package com.nus.edu.se.payment.pricing;

public class NonGroupFoodOrderPricingStrategy implements PricingStrategy {

    @Override
    public float calculatePrice(float totalPrice) {
        System.out.println("******* NonGroupFoodOrderPricingStrategy no Discount :( *******");
        return totalPrice;
    }

}