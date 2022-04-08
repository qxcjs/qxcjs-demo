package com.qxcjs.demo.config;

import com.qxcjs.demo.service.ScheduledJobService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 项目启动后自运行
 */
@Log4j2
@Component
public class JobScheduleStartup implements CommandLineRunner {
    @Resource
    private ScheduledJobService quartzService;

    @Override
    public void run(String... args) throws Exception {
        log.info("任务调度开始==============任务调度开始");
//        quartzService.timingTask();
        log.info("任务调度结束==============任务调度结束");
    }
}
