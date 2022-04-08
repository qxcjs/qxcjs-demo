package com.qxcjs.demo.service;

import com.qxcjs.demo.commons.constant.ScheduledJobOperateEnum;
import com.qxcjs.demo.entity.QuartzJobDetail;
import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.entity.QuartzTrigger;
import org.quartz.SchedulerException;

import java.util.List;
import java.util.Set;

/**
 * Quartz job 增删改查
 */
public interface ScheduledJobService {
    /**
     * 新增定时任务 新增 JobDetail 并绑定 Trigger
     */
    void addJobAndTrigger(QuartzTask task);

    /**
     * 新增 JobDetail
     */
    void addJob(QuartzJobDetail task);

    /**
     * 修改定时任务，更新任务 JobDetail 中 JobDataMap 数据
     */
    void modifyJob(QuartzTask task);

    /**
     * 删除定时任务
     */
    void deleteJob(QuartzTask task);

    /**
     * 停止定时任务
     */
    void pauseJob(QuartzTask task);

    /**
     * 恢复定时任务
     */
    void resumeJob(QuartzTask task);

    /**
     * 操作定时任务
     */
    void operateJob(ScheduledJobOperateEnum jobOperateEnum, QuartzTask task) throws SchedulerException;

    /**
     * 新增 Trigger ， 通过 jobName 和 jobGroup 绑定到对应的 JobDetail
     */
    void addTrigger(QuartzTrigger task);

    /**
     * 新增 Trigger ， 通过 jobName 和 jobGroup 绑定到对应的 JobDetail
     */
    void addTriggers(Set<QuartzTrigger> tasks);

    /**
     * 更新任务的 Trigger 修改cron表达式等
     */
    void modifyTrigger(QuartzTask task);

    /**
     * 立即执行一次任务
     */
    void runTaskNow(QuartzTask task);

    /**
     * 启动所有任务
     */
    void startAllJob() throws SchedulerException;

    /**
     * 关闭所有任务
     */
    void shutdownAllJob() throws SchedulerException;

    /**
     * 删除所有任务
     */
    void deleteAllJob() throws SchedulerException;

    /**
     * 暂停所有任务
     */
    void pauseAllJob() throws SchedulerException;

    /**
     * 计划中的任务，指那些已经添加到quartz调度器的任务
     */
    List<QuartzTask> planScheduleJobs();

    /**
     * 运行中的任务，指那些已经添加到quartz调度器的任务
     */
    List<QuartzTask> runningScheduleJobs();
}
