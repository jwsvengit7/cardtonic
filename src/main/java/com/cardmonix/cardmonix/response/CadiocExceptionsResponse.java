package com.cardmonix.cardmonix.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CadiocExceptionsResponse {
    private String message;
    private String time;
    private Integer statusCode;


}
