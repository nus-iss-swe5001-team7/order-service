package com.nus.edu.se.payment.boundary;

import com.nus.edu.se.payment.boundary.PayLahAdapter;
import com.nus.edu.se.payment.boundary.PayLahPaymentInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayLahAdapterTest {

    @Mock
    private PayLahPaymentInterface mockPayLahPaymentInterface;

    private PayLahAdapter payLahAdapter;

    // To capture System.out
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Initialize the PayLahAdapter with the mocked PayLahPaymentInterface
        payLahAdapter = new PayLahAdapter(mockPayLahPaymentInterface);

        // Redirect System.out to capture printed output
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testProcessPayment_callsCheckPhoneNumForPayLah() {
        // Act: Call the method to process payment
        payLahAdapter.processPayment();

        // Assert: Verify that checkPhoneNumForPayLah was called on the mock
        verify(mockPayLahPaymentInterface).checkPhoneNumForPayLah();
    }

}

