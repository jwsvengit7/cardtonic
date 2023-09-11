package com.cardmonix.cardmonix.utils;

public class CoinsUtils {
    private static String api ="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd";

    public static String coinGeckoApi(){
        return api;

    }
}
