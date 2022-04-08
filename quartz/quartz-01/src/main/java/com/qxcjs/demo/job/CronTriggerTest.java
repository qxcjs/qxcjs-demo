package com.qxcjs.demo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

@Slf4j
public class CronTriggerTest {
    public static void main(String[] args) {
        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();

            TriggerKey tKey = new TriggerKey("MyCronTrigger", "Group");
            JobKey jKey = new JobKey("MyCronJob", "Group");

            long now = System.currentTimeMillis();
            log.info(new StringBuilder().append("当前时间：").append(new Date(now)).toString());

            if (scheduler.checkExists(tKey)) {
                Trigger trigger = scheduler.getTrigger(tKey);
                scheduler.rescheduleJob(tKey, trigger);
            } else {
                JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity(jKey).build();

                CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(tKey)
                        .startNow()
                        .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                        .build();

                scheduler.scheduleJob(job, trigger);
            }
            Thread.sleep(60 * 1000);
            scheduler.shutdown(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
