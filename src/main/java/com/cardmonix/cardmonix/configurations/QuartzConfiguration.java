package com.cardmonix.cardmonix.configurations;

import com.cardmonix.cardmonix.airdropPrice.BirthdayNotification;
import lombok.SneakyThrows;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {
    @SneakyThrows
    @Bean
    public Scheduler scheduler()  {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    @Bean
    public JobDetail birthdayNotify() {
        return JobBuilder.newJob(BirthdayNotification.class)
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger birthday() {
        return TriggerBuilder.newTrigger()
                .forJob(birthdayNotify())
                .withIdentity("Birthday")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 59 3 * * ?")) // 3:15 AM every day
                .build();
    }


}