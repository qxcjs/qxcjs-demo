package com.qxcjs.demo.cron;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

@Slf4j
public class MisfireInstructionDoNothing {
    public static void main(String[] args) {
        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();

            TriggerKey tKey = new TriggerKey("MyTrigger", "Group");
            JobKey jKey = new JobKey("MyJob", "Group");

            long now = System.currentTimeMillis();
            log.info("当前时间：" + new Date(now));

            if (scheduler.checkExists(tKey)) {
                Trigger trigger = scheduler.getTrigger(tKey);
                scheduler.rescheduleJob(tKey, trigger);
            } else {
                JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity(jKey).build();

                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(tKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/30 40-45 * * * ?").withMisfireHandlingInstructionDoNothing())
                        .startNow()
                        .build();

                scheduler.scheduleJob(job, trigger);
            }
            scheduler.start();
            Thread.sleep(2 * 60 * 1000);
            scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
