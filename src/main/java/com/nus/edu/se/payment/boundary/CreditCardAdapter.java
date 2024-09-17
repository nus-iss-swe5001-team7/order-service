package com.nus.edu.se.payment.boundary;

// * exposing the target interface while wrapping source object */
public class CreditCardAdapter implements PaymentGatewayInterface {

   private final CCPaymentInterface ccPaymentInterface;

    public CreditCardAdapter(CCPaymentInterface ccPaymentInterface) {
        this.ccPaymentInterface = ccPaymentInterface;
    }

    @Override
    public void processPayment() {
        ccPaymentInterface.checkCreditCardCredentials();
        System.out.println("Process: ccPaymentInterface.checkCreditCardCredentials()");
    }
}

// // * exposing the target interface while wrapping source object */
// public class CreditCardAdapter implements CCPaymentInterface {
//    private final CCPaymentInterface ccPaymentInterface;

//     public CreditCardAdapter(CCPaymentInterface ccPaymentInterface) {
//         this.ccPaymentInterface = ccPaymentInterface;
//     }

//     @Override
//     public void checkCreditCardCredentials() {
//         System.out.println("adapter_checkCreditCardCredentials");
//     }
// }
