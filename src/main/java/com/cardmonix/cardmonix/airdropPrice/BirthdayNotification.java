package com.cardmonix.cardmonix.airdropPrice;

import com.cardmonix.cardmonix.domain.entity.userModel.User;
import com.cardmonix.cardmonix.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;
import java.util.List;

public class BirthdayNotification implements Job {

    private UserService userService;
    BirthdayNotification(UserService userService){
        this.userService=userService;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date newDate = new Date();
        int year = newDate.getYear();
        int month = newDate.getMonth();
        int day = newDate.getDay();
        String dates =year+"-"+month+"-"+day;
        List<User> usersWithBirthdayToday =userService.userBirthday(dates);
        if(usersWithBirthdayToday.isEmpty()){
            System.out.println("www");
        }else{
            System.out.println("rrr");
        }

        for (User user : usersWithBirthdayToday) {
            // Implement your notification logic here
            System.out.println("Sending birthday notification to user: " + user.getUsername());
        }
    }
}
