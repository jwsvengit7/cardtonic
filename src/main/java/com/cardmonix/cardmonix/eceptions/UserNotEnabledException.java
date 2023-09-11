package com.cardmonix.cardmonix.eceptions;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException(String message){
        super(message);
    }
}
