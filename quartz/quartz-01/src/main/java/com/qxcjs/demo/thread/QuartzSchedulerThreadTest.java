package com.qxcjs.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 主线程 QuartzSchedulerThread 初始化流程调试
 * 1. QuartzSchedulerResources 配置类
 */
@Slf4j
public class QuartzSchedulerThreadTest {
    public static void main(String[] args) {
        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            Thread.sleep(1000 * 600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
