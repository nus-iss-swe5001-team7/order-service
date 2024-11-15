package com.nus.edu.se.payment.boundary;

import com.nus.edu.se.payment.boundary.PayNowAdapter;
import com.nus.edu.se.payment.boundary.PayNowPaymentInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayNowAdapterTest {

    @Mock
    private PayNowPaymentInterface mockPayNowPaymentInterface;

    private PayNowAdapter payNowAdapter;
    // To capture System.out
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize the PayLahAdapter with the mocked PayNowPaymentInterface
        payNowAdapter = new PayNowAdapter(mockPayNowPaymentInterface);

        // Redirect System.out to capture printed output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testProcessPayment_callsCheckPhoneNumForPayNow() {
        // Act: Call the method to process payment
        payNowAdapter.processPayment();

        // Assert: Verify that checkPhoneNumForPayNow was called on the mock
        verify(mockPayNowPaymentInterface).checkPhoneNumForPayNow();

    }
}

