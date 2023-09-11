package com.cardmonix.cardmonix.response;

import lombok.Data;

@Data
public class CoinResponse {
    private Long coin_id;
    private String image;
    private Float current_price;
    private Boolean activate;

    private String coinName;
    private String currency;
}
