package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.domain.entity.account.Deposit;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PendingPublishListener implements ApplicationListener<PendingDeposit> {
    private final JavaMailSender javaMailSender;
    @Override
    public void onApplicationEvent(PendingDeposit event) {
        sendDepositMail(event.getDeposit());
    }
    @SneakyThrows
    private void sendDepositMail(Deposit deposit) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

        messageHelper.setFrom("info@swissfliptrade.com");
        messageHelper.setSubject("Pending Deposit");
        messageHelper.setTo(deposit.getUser().getEmail());
        String message ="<div>" +
                "<h1>Pending Deposit in " +deposit.getCoin()+"</h1>"+
                "<h1>" +"</h1>"+
                "<p>Amount: $" +deposit.getAmount()+"</p>"+
                "<p>AmountIn: " +deposit.getAmountInCurrency()+"</p>"+
                "<p> Deposit will be appeared confirm once it been verified by Cardiac " +"</p>"+
                "</div>";
        messageHelper.setText(message,true);
        System.out.println("Deposit"+deposit.getUser().getEmail());
        javaMailSender.send(mimeMessage);

    }
}
