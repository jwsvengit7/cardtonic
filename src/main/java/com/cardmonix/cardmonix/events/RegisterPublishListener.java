package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.configurations.MailConfig;
import com.cardmonix.cardmonix.domain.constant.Email;
import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Component
@Slf4j
public class RegisterPublishListener implements ApplicationListener<RegisterPublish> {
    private final MailConfig mailConfig;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String hostName;
    @Value("${app.name}")
    private String APP_NAME;

    @Override
    public void onApplicationEvent(RegisterPublish event) {

        String otp = event.getOtp();
        try {
             sendEmail(event.getUser(), otp);
            log.info(otp);
        }
        catch(Exception e){
            log.info(e.getMessage());
        }
    }

    private void sendEmail(User user,String otp) throws MessagingException, UnsupportedEncodingException {
        String subject = "SIGNUP";
        String companyName = APP_NAME;
        MimeMessage mimeMessage = mailConfig.customJavaMailSender().createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("cadmonix@gmail.com", companyName);
        messageHelper.setSubject(subject);
        messageHelper.setTo(user.getEmail());
        String mailContent = EmailUtils.sendHtmlEmailTemplate(templateEngine,subject,user.getEmail(),otp,APP_NAME, Email.OTP);
        messageHelper.setText(mailContent, true);
        mailConfig.customJavaMailSender().send(mimeMessage);
        log.info("Email sent successfully to: " + user.getEmail());
    }

}
