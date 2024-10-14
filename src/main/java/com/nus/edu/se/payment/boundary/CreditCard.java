package com.nus.edu.se.payment.boundary;

public class CreditCard implements CCPaymentInterface {
	
    @Override
    public void checkCreditCardCredentials() {
        
        System.out.println("class_checkCreditCardCredentials");
    }


}
