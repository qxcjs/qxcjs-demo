package com.qxcjs.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxcjs.demo.entity.QuartzJobDetail;
import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.entity.QuartzTrigger;
import com.qxcjs.demo.service.ScheduledJobService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledJobServiceImplTest {

    @Resource
    private ScheduledJobService scheduledJobService;

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 一个 job 对应一个 trigger
     *
     * @throws Exception
     */
    @Test
    public void addJob() throws Exception {
        QuartzTask task = new QuartzTask();
        task.setJobName("job1");
        task.setJobGroup("group1");
        task.setBeanName("testJob");
        task.setMethodName("test");
        task.setCronExpression("0 55 8 * * ?");
        task.setInvokeParamClass("com.qxcjs.demo.entity.QuartzTask");
        String value = objectMapper.writeValueAsString(task);
        task.setInvokeParam(value);
        scheduledJobService.addJobAndTrigger(task);
        Thread.sleep(1000 * 20);
        task.setDescription("立即执行一次");
        task.setDelFlag(1);
        scheduledJobService.runTaskNow(task);
        Thread.sleep(1000 * 100000);
    }

    /**
     * 一个 job 对应多个 trigger
     */
    @Test
    public void addJob1() throws Exception {
        QuartzJobDetail jobDetail = new QuartzJobDetail();
        jobDetail.setJobName("job1");
        jobDetail.setJobGroup("group1");
        jobDetail.setJobClass("com.qxcjs.demo.schedule.GlobalScheduleJob");
        scheduledJobService.addJob(jobDetail);

        QuartzTrigger trigger1 = new QuartzTrigger();
        trigger1.setJobName("job1");
        trigger1.setJobGroup("group1");
        trigger1.setTriggerName("triggerName1");
        trigger1.setTriggerGroup("triggerGroup1");
        trigger1.setCronExpression("30 * * * * ?");
        trigger1.setDescription("trigger1");

        QuartzTrigger trigger2 = new QuartzTrigger();
        trigger2.setJobName("job1");
        trigger2.setJobGroup("group1");
        trigger2.setTriggerName("triggerName2");
        trigger2.setTriggerGroup("triggerGroup2");
        trigger2.setCronExpression("15 * * * * ?");
        trigger2.setDescription("trigger2");

        scheduledJobService.addTrigger(trigger1);
        scheduledJobService.addTrigger(trigger2);
        Thread.sleep(1000 * 10000);
    }

    @Test
    public void runTaskImmediately() throws Exception {
        QuartzTask task = new QuartzTask();
        task.setJobName("job1");
        task.setJobGroup("group1");
        task.setDescription("临时执行一次");
        scheduledJobService.runTaskNow(task);
        Thread.sleep(1000 * 10000);
    }
}