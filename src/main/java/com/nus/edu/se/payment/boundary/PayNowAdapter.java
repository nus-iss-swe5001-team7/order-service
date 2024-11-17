package com.nus.edu.se.payment.boundary;

public class PayNowAdapter implements PaymentGatewayInterface {
   private final PayNowPaymentInterface payNowPaymentInterface;

    public PayNowAdapter(PayNowPaymentInterface payNowPaymentInterface) {
        this.payNowPaymentInterface = payNowPaymentInterface;
    }

    @Override
    public void processPayment() {
        payNowPaymentInterface.checkPhoneNumForPayNow();
        System.out.println("Process: payNowPaymentInterface.checkPhoneNumForPayNow()");
    }
}
