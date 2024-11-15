package com.nus.edu.se.payment.boundary;

import com.nus.edu.se.payment.boundary.CreditCardAdapter;
import com.nus.edu.se.payment.boundary.CCPaymentInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class CreditCardAdapterTest {

    @Mock
    private CCPaymentInterface mockCCPaymentInterface;

    private CreditCardAdapter creditCardAdapter;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        MockitoAnnotations.openMocks(this);

        // Create an instance of the adapter with the mock dependency
        creditCardAdapter = new CreditCardAdapter(mockCCPaymentInterface);
    }

    @Test
    void testProcessPayment() {
        // Act: Call the processPayment method on the adapter
        creditCardAdapter.processPayment();

        // Assert: Verify that checkCreditCardCredentials() was called on the mock
        verify(mockCCPaymentInterface).checkCreditCardCredentials();
    }
}

