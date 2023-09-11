package com.cardmonix.cardmonix.request;

import lombok.Data;

@Data
public class SaveCoin {
    private String name;
    private String image;
    private float current_price;
}
