package com.nus.edu.se.payment.pricing;

public class LoyaltyPromoDecorator extends PricingStrategyDecorator {

    public LoyaltyPromoDecorator(PricingStrategy toBeDecoratedStrategy) {
        super(toBeDecoratedStrategy);
    }

    @Override
    public float calculatePrice(float totalPrice) {
        System.out.println("******* LoyaltyPromoDecorator 10% Discount wow *******");
        totalPrice = totalPrice * 0.9f;
        return super.calculatePrice(totalPrice);
    }

}