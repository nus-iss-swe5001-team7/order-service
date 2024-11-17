package com.nus.edu.se.payment.pricing;

public class GroupFoodOrderPricingStrategy implements PricingStrategy {
    
    @Override
    public float calculatePrice(float totalPrice) {
        System.out.println("******* GroupFoodOrderPricingStrategy Discount 2% *******");
        // totalPrice = totalPrice - 2;
        totalPrice = totalPrice * 0.98f;
        return totalPrice;
    }

}