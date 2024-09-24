package com.nus.edu.se.payment.pricing;

public class FoodCart {

    private boolean isGroupFoodOrderStrategy;
    private boolean isGetPromoDecorator;
    private float totalPrice;
    private DecoratorGenerator.PromoType selectedPromo;

    // to be edited
    public FoodCart() {
        // Default constructor
    }
    public FoodCart(boolean isGroupFoodOrderStrategy, boolean isGetPromoDecorator, float totalPrice, DecoratorGenerator.PromoType selectedPromo) {
        this.isGroupFoodOrderStrategy = isGroupFoodOrderStrategy;
        this.isGetPromoDecorator = isGetPromoDecorator;
        this.totalPrice = totalPrice;
        this.selectedPromo = selectedPromo;
    }

    public float checkout() {

        // call Factory
        PricingStrategyFactory factory = new PricingStrategyFactory();
        // factory will instantiate concrete strategy based on user's choice `isGroupFoodOrderStrategy`
        PricingStrategy concreteStrategy = factory.getPricingStrategy(this.isGroupFoodOrderStrategy);

        PricingStrategy appliedConcreteStrategy = null;

        // if user wants to apply promo avaliable
        if (isGetPromoDecorator) {
            // DecoratorGenerator gen = new DecoratorGenerator();

            // DecoratorGenerator.PromoType selectedPromo = this.gen.generateRandomDecorator();

            // System.out.println("******* Generated Promo: *******");
            // System.out.println(selectedPromo);
            // System.out.println("Type of selectedPromo: " + selectedPromo.getClass().getName());
            // System.out.println(DecoratorGenerator.PromoType.SEASONAL_PROMO);

            switch (selectedPromo) {
                case SEASONAL_PROMO:
                    appliedConcreteStrategy = new SeasonalPromoDecorator(concreteStrategy);
                    break;

                case LOYALTY_PROMO:
                    appliedConcreteStrategy = new LoyaltyPromoDecorator(concreteStrategy);
                    break;

                case HAPPY_DEALS_PROMO:
                    appliedConcreteStrategy = new HappyDealsPromoDecorator(concreteStrategy);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown promo type: " + selectedPromo);
            }
        } else {
            // case where no decorator is applied
            appliedConcreteStrategy = concreteStrategy;
        }

        this.totalPrice = appliedConcreteStrategy.calculatePrice(this.totalPrice);

        return this.totalPrice;

    }

}
