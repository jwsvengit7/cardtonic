package com.cardmonix.cardmonix.utils;

import com.cardmonix.cardmonix.domain.constant.Email;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


public class EmailUtils {

    public static String sendHtmlEmailTemplate(TemplateEngine templateEngine, String typesubject, String email, String value,String value2, Email notify){
        Context context = new Context();
        String file ="";
        if(notify.equals(Email.OTP)){
            file ="otp";
            context.setVariable("user_name", email);
            context.setVariable("company_name", typesubject);
            context.setVariable("otp", value);
        }else if(notify.equals(Email.DEPOSIT)){
            file ="deposit";
            context.setVariable("user_name", email);
            context.setVariable("coin", typesubject);
            context.setVariable("amount", value);
            context.setVariable("amountinusd", value2);

        }
        return templateEngine.process(file, context);
    }
}
