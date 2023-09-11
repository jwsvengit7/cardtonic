package com.cardmonix.cardmonix.airdropPrice;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class MonthlyPayment implements Job {
    @Override

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Time is here");

    }

}