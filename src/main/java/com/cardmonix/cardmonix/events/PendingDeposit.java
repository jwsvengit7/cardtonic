package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.domain.entity.account.Deposit;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class PendingDeposit extends ApplicationEvent {
    private Deposit deposit;
    private String message;

    public PendingDeposit(Deposit deposit, String message){
        super(deposit);
        this.deposit=deposit;
        this.message=message;
    }
}
