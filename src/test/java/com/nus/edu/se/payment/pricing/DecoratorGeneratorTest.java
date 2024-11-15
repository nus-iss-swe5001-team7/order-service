package com.nus.edu.se.payment.pricing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorGeneratorTest {

    private DecoratorGenerator decoratorGenerator;

    @BeforeEach
    void setUp() {
        decoratorGenerator = new DecoratorGenerator();
    }

    @Test
    void testGenerateRandomDecorator() {
        Set<DecoratorGenerator.PromoType> generatedPromos = new HashSet<>();
        int attempts = 100;

        // Generate random promos multiple times to ensure randomness and coverage
        for (int i = 0; i < attempts; i++) {
            DecoratorGenerator.PromoType promoType = decoratorGenerator.generateRandomDecorator();
            assertNotNull(promoType, "PromoType should not be null");
            generatedPromos.add(promoType);

            // Break early if all types are generated
            if (generatedPromos.size() == DecoratorGenerator.PromoType.values().length) {
                break;
            }
        }

        // Check if all promo types were generated
        assertEquals(
                DecoratorGenerator.PromoType.values().length,
                generatedPromos.size(),
                "Should generate all promo types within " + attempts + " attempts"
        );
    }
}
