package com.nus.edu.se.payment.pricing;

public class SeasonalPromoDecorator extends PricingStrategyDecorator {

    public SeasonalPromoDecorator(PricingStrategy toBeDecoratedStrategy) {
        super(toBeDecoratedStrategy);
    }

    @Override
    public float calculatePrice(float totalPrice) {
        System.out.println("******* SeasonalPromoDecorator Discount $1 *******");
        totalPrice = totalPrice - 1;
        return super.calculatePrice(totalPrice);
    }
    
}
