package com.qxcjs.demo.schedule;

import lombok.extern.log4j.Log4j2;
import org.quartz.*;

@Log4j2
public class GlobalScheduleJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobKey jobKey = jobDetail.getKey();
        Trigger trigger = context.getTrigger();
        TriggerKey triggerKey = trigger.getKey();
        log.info("jobName : {}, jobGroup : {}, triggerName : {}, triggerGroup : {}, trigger description : {}",
                jobKey.getName(),
                jobKey.getGroup(),
                triggerKey.getName(),
                triggerKey.getGroup(),
                trigger.getDescription()
        );
    }
}
