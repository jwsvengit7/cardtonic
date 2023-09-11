package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
public class RegisterPublishListener implements ApplicationListener<RegisterPublish> {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;


    @Override
    public void onApplicationEvent(RegisterPublish event) {
        String otp = event.getOtp();
        try {
             sendEmail(event.getUser(), otp);
            System.out.println(otp);
        }
        catch(Exception e){
            System.out.println(e.getMessage());

        }

    }

    private void sendEmail(User user,String otp) throws MessagingException, UnsupportedEncodingException {

        String subject = "SIGNUP";
        String companyName = "CADIOC INVESTMENT LTD.";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("info@swissfliptrade.com", companyName);
        messageHelper.setSubject(subject);
        messageHelper.setTo(user.getEmail());

        Context context = new Context();
        context.setVariable("user_name", user.getUser_name());
        context.setVariable("company_name", subject);
        context.setVariable("otp", otp);
        String mailContent = templateEngine.process("otp", context);
        messageHelper.setText(mailContent, true);
        // Send the email
        javaMailSender.send(mimeMessage);
        System.out.println("Email sent successfully to: " + user.getEmail());
    }

}
