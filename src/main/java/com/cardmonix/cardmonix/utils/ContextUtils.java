package com.cardmonix.cardmonix.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContextUtils {

    public static String appUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
    public static String frontAppUrl(HttpServletRequest request){
        return "http://"+request.getServerName()+":5173"+request.getContextPath();
    }


}
