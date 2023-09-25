package com.cardmonix.cardmonix.utils;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
@Data
public class UserUtils {


    public static SecurityContext getContext(){
        return SecurityContextHolder.getContext();
    }

    public static String getAccessTokenEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
