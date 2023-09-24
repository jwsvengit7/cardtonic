package com.cardmonix.cardmonix.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PaystactAPIUtils {
    @Value("${application.security.paystack.key}")
    private String KEY;
    public  String getKEY(){
        return KEY;
    }

    public static final String PAYSTACK_SUBACCOUNT_URL = "https://api.paystack.co/subaccount";
    public static final   String PAYSTACK_RESOLVE_URL="https://api.paystack.co/bank/resolve";
    public static final String PERCENTAGE_CHARGE_BY_PAYSTACK ="2";
    public static final String PAYSTACK_BANK_API ="https://api.paystack.co/bank";



}
