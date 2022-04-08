package com.qxcjs.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

@Slf4j
public class SimpleTriggerTest {

    public static void main(String[] args) {
        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();

            TriggerKey tKey = new TriggerKey("MySimpleTrigger", "Group");
            JobKey jKey = new JobKey("MySimpleJob", "Group");

            long now = System.currentTimeMillis();
            log.info("当前时间：" + new Date(now));

            if (scheduler.checkExists(tKey)) {
                Trigger trigger = scheduler.getTrigger(tKey);
                scheduler.rescheduleJob(tKey, trigger);
            } else {
                JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity(jKey).build();

                SimpleTrigger trigger = TriggerBuilder.newTrigger().withIdentity(tKey)
                        .startAt(new Date(now + 5 * 1000))
                        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).withRepeatCount(10))
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