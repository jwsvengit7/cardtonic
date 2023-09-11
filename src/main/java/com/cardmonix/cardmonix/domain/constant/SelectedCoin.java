package com.cardmonix.cardmonix.domain.constant;



public enum SelectedCoin {
    BITCOIN("BITCOIN (BTC)"),
    ETHEREUM("ETHEREUM (ETH)"),
    TETHER("TETHER (USDT)"),
    RIPPLE("RIPPLE (XRP)"),
    BINANCE("BINANCE (BNB)"),
    TRON("TRON (TRX)");

    private final String type;
    SelectedCoin(String type) {
            this.type = type;
        }
        public static SelectedCoin toType(String type) {
            for (SelectedCoin category : SelectedCoin.values()) {
                if (category.type.equalsIgnoreCase(type)) {
                    return category;
                }
            }
            throw new IllegalArgumentException("You must select a payment type: ");
        }









}
