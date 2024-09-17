package com.nus.edu.se.payment.boundary;


public class PayLahAdapter implements PaymentGatewayInterface {

   private final PayLahPaymentInterface payLahPaymentInterface;

    public PayLahAdapter(PayLahPaymentInterface payLahPaymentInterface) {
        this.payLahPaymentInterface = payLahPaymentInterface;
    }

    @Override
    public void processPayment() {
        payLahPaymentInterface.checkPhoneNumForPayLah();
        System.out.println("Process: payLahPaymentInterface.checkPhoneNumForPayLah()");
    }
}

// class PayLahAdapter implements PayLahPaymentInterface {
//    private final PayLahPaymentInterface payLahPaymentInterface;

//     public PayLahAdapter(PayLahPaymentInterface payLahPaymentInterface) {
//         this.payLahPaymentInterface = payLahPaymentInterface;
//     }

//     @Override
//     public void checkPhoneNumForPayLah() {
//         System.out.println("adapter_checkPhoneNumFor_PayLah");
//     }
// }

