package com.qxcjs.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 */
@Slf4j
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            log.info("[" + context.getJobDetail().getKey() + "]My Job!!!");
            Thread.sleep(6000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
