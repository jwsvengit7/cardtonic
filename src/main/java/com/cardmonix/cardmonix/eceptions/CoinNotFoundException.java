package com.cardmonix.cardmonix.eceptions;

public class CoinNotFoundException  extends RuntimeException{
    public CoinNotFoundException(String message){
        super(message);
    }
}
