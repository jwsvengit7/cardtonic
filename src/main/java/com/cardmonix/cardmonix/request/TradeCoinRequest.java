package com.cardmonix.cardmonix.request;

import lombok.Data;

@Data
public class TradeCoinRequest {
    private String message;
    private Double amount;
    private Double amountInusd;
    private String coin;

    private String transid;

}
