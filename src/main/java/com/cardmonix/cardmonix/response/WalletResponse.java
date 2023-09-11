package com.cardmonix.cardmonix.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WalletResponse {
    private Long walletId;
    private Double wallet_amount;
    private Float walletInUsd;
    private String coin;
}
