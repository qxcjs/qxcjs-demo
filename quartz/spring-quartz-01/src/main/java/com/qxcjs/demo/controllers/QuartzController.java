package com.qxcjs.demo.controllers;

import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.service.ScheduledJobService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/quartz")
public class QuartzController {
    @Resource
    private ScheduledJobService quartzService;

    @PostMapping("addJob")
    public Object add(@RequestBody QuartzTask quartzTask) {
        quartzService.addJobAndTrigger(quartzTask);
        return "success";
    }

    @PostMapping("/startJob")
    public Object start() {
        return null;
    }

    @PostMapping("/pauseJob")
    public Object pause() {
        return null;
    }

    @PostMapping("/deleteJob")
    public Object delete() {
        return null;
    }

    @PostMapping("/startAllJob")
    public Object startAllJob() {
        return null;
    }

    @PostMapping("/pauseAllJob")
    public Object pauseAllJob() {
        return null;
    }
}
