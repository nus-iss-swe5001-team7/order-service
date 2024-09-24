package com.nus.edu.se.payment.pricing;


// https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
import java.util.Random;
// Decorator Generator
// using a simple generator to randomly choose a Promotion choice, 
// it is to simplify our logics
// ultimately our objective is to demonstrate how the combined design patterns worked
// so I did not focus on business logics here on the choices of promotions

public class DecoratorGenerator {

    public enum PromoType {
        SEASONAL_PROMO,
        LOYALTY_PROMO,
        HAPPY_DEALS_PROMO
    }

    public PromoType generateRandomDecorator() {

        Random random = new Random();
        int index = random.nextInt(PromoType.values().length);
        return PromoType.values()[index];
        // PromoType[] promoTypes = PromoType.values();
        // Random random = new Random();
        // return promoTypes[random.nextInt(promoTypes.length)];
    }

}


/* Testing code
DecoratorGenerator gen = new DecoratorGenerator();
DecoratorGenerator.PromoType p = gen.generateRandomDecorator();
System.out.println(p); 
*/