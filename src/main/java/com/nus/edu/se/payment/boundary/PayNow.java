package com.nus.edu.se.payment.boundary;

public class PayNow implements PayNowPaymentInterface {
    @Override
    public void checkPhoneNumForPayNow() {
        
        System.out.println("class_checkPhoneNumFor_PayNow");
    }

}
