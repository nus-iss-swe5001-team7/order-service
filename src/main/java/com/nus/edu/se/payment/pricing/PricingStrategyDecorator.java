package com.nus.edu.se.payment.pricing;

public abstract class PricingStrategyDecorator implements PricingStrategy {

    protected PricingStrategy toBeDecoratedStrategy;

    public PricingStrategyDecorator(PricingStrategy toBeDecoratedStrategy) {
        this.toBeDecoratedStrategy = toBeDecoratedStrategy;
    }

    @Override
    public float calculatePrice(float totalPrice) {
        return toBeDecoratedStrategy.calculatePrice(totalPrice);
    }

}
