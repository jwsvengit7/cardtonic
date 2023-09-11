package com.cardmonix.cardmonix.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class PaystactAPIUtils {
    @Value("${application.security.paystack.key}")
    private static String KEY;
    private HttpHeaders headers;
    PaystactAPIUtils(HttpHeaders headers){
        this.headers=headers;
    }
    public static String secreteKey(){
        return KEY;
    }

    public static void apiDetails(HttpHeaders headers) {
        headers.setBearerAuth(PaystactAPIUtils.secreteKey());
        headers.set("Cache-Control", "no-cache");
        headers.setContentType(MediaType.APPLICATION_JSON);

    }
}
