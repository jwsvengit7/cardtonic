package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.configurations.MailConfig;
import com.cardmonix.cardmonix.domain.constant.Email;
import com.cardmonix.cardmonix.domain.entity.account.Deposit;
import com.cardmonix.cardmonix.utils.EmailUtils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

@RequiredArgsConstructor
@Component
@Slf4j
public class PendingPublishListener implements ApplicationListener<PendingDeposit> {
    private final MailConfig mailConfig;
    private final TemplateEngine templateEngine;

    @Override
    public void onApplicationEvent(PendingDeposit event) {
        try {
            sendDepositMail(event.getDeposit());
        }catch (Exception e){
            log.info("{}",e.getMessage());
        }
    }

    private void sendDepositMail(Deposit deposit) throws MessagingException {
        MimeMessage mimeMessage = mailConfig.customJavaMailSender().createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
        messageHelper.setFrom("cadmonix@gmail.com");
        messageHelper.setSubject("Pending Deposit");
        messageHelper.setTo(deposit.getUser().getEmail());
        String mailContent = EmailUtils.sendHtmlEmailTemplate(templateEngine,deposit.getCoin(),deposit.getUser().getEmail(),deposit.getAmount().toString(),deposit.getAmountInCurrency().toString(), Email.DEPOSIT);
        messageHelper.setText(mailContent, true);
        log.info("{} Deposit"+deposit.getUser().getEmail());
        mailConfig.customJavaMailSender().send(mimeMessage);

    }
}
