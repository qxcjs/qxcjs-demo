package com.qxcjs.demo.service.impl;

import com.qxcjs.demo.commons.constant.ScheduledConstant;
import com.qxcjs.demo.commons.constant.ScheduledJobOperateEnum;
import com.qxcjs.demo.entity.QuartzJobDetail;
import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.entity.QuartzTrigger;
import com.qxcjs.demo.mapper.QuartzTaskMapper;
import com.qxcjs.demo.schedule.ScheduleJob;
import com.qxcjs.demo.service.QuartzTaskService;
import com.qxcjs.demo.service.ScheduledJobService;
import lombok.extern.log4j.Log4j2;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class ScheduledJobServiceImpl implements ScheduledJobService {
    /**
     * 调度器
     */
    @Resource
    private Scheduler scheduler;

    @Resource
    private QuartzTaskMapper quartzTaskMapper;

    @Resource
    private QuartzTaskService quartzTaskService;

    @Override
    public void addJobAndTrigger(QuartzTask task) {
        try {
            JobKey jobKey = new JobKey(task.getJobName(), task.getJobGroup());
            //创建任务
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class)
                    .withIdentity(jobKey)
                    .build();

            //传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put(ScheduledConstant.QUARTZ_TASK, task);

            TriggerKey triggerKey = new TriggerKey(task.getJobName(), task.getJobGroup());
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
                    .startNow()
                    .build();

            //调度作业
            scheduler.scheduleJob(jobDetail, trigger);

            quartzTaskService.addQuartzTask(task);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addJob(QuartzJobDetail task) {
        try {
            JobKey jobKey = new JobKey(task.getJobName(), task.getJobGroup());
            // 创建任务
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(task.getJobClass()))
                    // durable, 指明任务就算没有绑定 Trigger 仍保留在 Quartz 的 JobStore 中
                    .storeDurably(true)
                    .withIdentity(jobKey)
                    .build();

            // 传入调度的数据，在QuartzFactory中需要使用
            jobDetail.getJobDataMap().put(ScheduledConstant.QUARTZ_TASK, task);

            scheduler.addJob(jobDetail, true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void modifyJob(QuartzTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            /**
             * 使用 jobDetail 中的属性重新创建了一个 JobDetail 对象，创建时可以修改里面的属性
             */
            JobDetail newJobDetail = jobDetail.getJobBuilder()
                    .storeDurably(true)
                    .build();
            newJobDetail.getJobDataMap().put("a", "b");

            // true 表示覆盖已有的job
            scheduler.addJob(newJobDetail, true);
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    @Override
    public void deleteJob(QuartzTask task) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            boolean b = scheduler.deleteJob(new JobKey(task.getJobName(), task.getJobGroup()));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pauseJob(QuartzTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.pauseJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void resumeJob(QuartzTask task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            if (scheduler.checkExists(jobKey)) {
                scheduler.resumeJob(jobKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void operateJob(ScheduledJobOperateEnum jobOperateEnum, QuartzTask task) throws SchedulerException {
        JobKey jobKey = new JobKey(task.getJobName(), task.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            //抛异常
        }
        switch (jobOperateEnum) {
            case RESUME:
                scheduler.resumeJob(jobKey);
                break;
            case PAUSE:
                scheduler.pauseJob(jobKey);
                break;
            case DELETE:
                scheduler.deleteJob(jobKey);
                break;
        }
    }

    @Override
    public void addTrigger(QuartzTrigger task) {
        try {
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            TriggerKey triggerKey = new TriggerKey(task.getTriggerName(), task.getTriggerGroup());
            //创建触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(jobKey)
                    .withIdentity(triggerKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
                    .withDescription(task.getDescription())
                    .startNow()
                    .build();

            // 调度作业
            scheduler.scheduleJob(trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addTriggers(Set<QuartzTrigger> tasks) {
        tasks.forEach(task -> addTrigger(task));
    }

    @Override
    public void modifyTrigger(QuartzTask task) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(task.getJobName(), task.getJobGroup());
            // 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error(e);
        }
    }

    @Override
    public void runTaskNow(QuartzTask task) {
        try {
            log.info("立即执行一次任务 start");
            JobKey jobKey = JobKey.jobKey(task.getJobName(), task.getJobGroup());
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put(ScheduledConstant.QUARTZ_TASK, task);
            scheduler.triggerJob(jobKey, jobDataMap);
            log.info("立即执行一次任务 end");
        } catch (SchedulerException e) {
            log.error("立即执行任务错误", e);
        }
    }

    @Override
    public void startAllJob() throws SchedulerException {
        if (!scheduler.isStarted()) {
            scheduler.start();
        }
    }

    @Override
    public void shutdownAllJob() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    @Override
    public void deleteAllJob() throws SchedulerException {
        scheduler.clear();
    }

    @Override
    public void pauseAllJob() throws SchedulerException {
        scheduler.standby();
    }

    @Override
    public List<QuartzTask> planScheduleJobs() {
        List<QuartzTask> tasks = new ArrayList<>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    QuartzTask job = new QuartzTask();
                    job.setJobName(jobKey.getName());
                    job.setJobGroup(jobKey.getGroup());
                    job.setDescription("触发器:" + trigger.getKey());
//                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
//                    job.setJobStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setCronExpression(cronExpression);
                    }
                    tasks.add(job);
                }
            }
        } catch (SchedulerException e) {
            log.error(e);
        }
        return tasks;
    }

    @Override
    public List<QuartzTask> runningScheduleJobs() {
        List<QuartzTask> tasks = new ArrayList<>();
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext executingJob : executingJobs) {
                QuartzTask job = new QuartzTask();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setJobName(jobKey.getName());
                job.setJobGroup(jobKey.getGroup());
                job.setDescription("触发器:" + trigger.getKey());
                //            Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                //            job.setJobStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setCronExpression(cronExpression);
                }
                tasks.add(job);
            }
        } catch (SchedulerException e) {
            log.error(e);
        }
        return tasks;
    }
}
