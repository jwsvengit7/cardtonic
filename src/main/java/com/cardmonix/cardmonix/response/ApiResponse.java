package com.cardmonix.cardmonix.response;

import lombok.Data;

import java.util.Date;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private Date dateTime;

    public ApiResponse(T data){
        this.message="message response";
        this.data=data;
        this.dateTime = new Date();
    }

}
