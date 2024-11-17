package com.nus.edu.se.payment.pricing;

public class HappyDealsPromoDecorator extends PricingStrategyDecorator {

    public HappyDealsPromoDecorator(PricingStrategy toBeDecoratedStrategy) {
        super(toBeDecoratedStrategy);
    }

    @Override
    public float calculatePrice(float totalPrice) {
        System.out.println("******* HappyDealsPromoDecorator Discount 5% :) *******");
        // totalPrice = totalPrice - 5;
        totalPrice = totalPrice * 0.95f;
        return super.calculatePrice(totalPrice);
    }

}