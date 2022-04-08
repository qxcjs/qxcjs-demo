package com.qxcjs.demo.service.impl;

import com.qxcjs.demo.entity.QuartzTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Log4j2
@Component("testJob")
public class TestJobService {
    public void test() {
        log.info("-------------------任务执行开始-------------------");
        log.info(LocalDateTime.now().toString());
        log.info("-------------------任务执行结束-------------------");
    }

    public void test(QuartzTask quartzTask) {
        log.info("-------------------任务执行开始-------------------");
        log.info("quartz task : {}", quartzTask.toString());
        log.info("-------------------任务执行结束-------------------");
    }
}
