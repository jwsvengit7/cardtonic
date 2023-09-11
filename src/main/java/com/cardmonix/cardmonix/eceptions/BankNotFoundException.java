package com.cardmonix.cardmonix.eceptions;

public class BankNotFoundException extends RuntimeException{
    public  BankNotFoundException(String message){
        super(message);
    }
}
