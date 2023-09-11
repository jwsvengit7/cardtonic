package com.cardmonix.cardmonix.eceptions;

public class InsufficientFundException extends RuntimeException{
    public InsufficientFundException(String message){
        super(message);
    }
}
