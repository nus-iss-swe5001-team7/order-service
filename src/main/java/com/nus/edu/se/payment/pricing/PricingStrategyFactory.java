package com.nus.edu.se.payment.pricing;

// FoodCart will call PricingStrategyFacotry
// Purpose of this factory is to instantiate the concrete strategy

public class PricingStrategyFactory {

    public static PricingStrategy getPricingStrategy(boolean isGroupFoodOrderStrategy) {
        if (isGroupFoodOrderStrategy) {
            return new GroupFoodOrderPricingStrategy();
        } else {
            return new NonGroupFoodOrderPricingStrategy();
        }
    }

}




