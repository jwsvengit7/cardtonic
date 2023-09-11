package com.cardmonix.cardmonix.request;

import com.cardmonix.cardmonix.domain.constant.GiftCards;
import lombok.Data;

@Data
public class BuyGiftcardRequest {
    private String cartId;
    private Double amount;
    private String type;
    private GiftCards giftcard;
}
