package com.nus.edu.se.payment.boundary;

class   PayNow implements PayNowPaymentInterface {
    @Override
    public void checkPhoneNumForPayNow() {
        
        System.out.println("class_checkPhoneNumFor_PayNow");
    }

}
