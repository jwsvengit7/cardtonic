package com.cardmonix.cardmonix.events;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class RegisterPublish extends ApplicationEvent {
    private String otp;
    private User user;
    public RegisterPublish(User user,String otp){
        super(user);
        this.user=user;
        this.otp=otp;
    }

}
