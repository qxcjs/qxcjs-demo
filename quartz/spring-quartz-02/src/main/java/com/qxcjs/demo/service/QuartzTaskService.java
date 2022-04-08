package com.qxcjs.demo.service;

import com.qxcjs.demo.entity.QuartzTask;

import java.util.List;

public interface QuartzTaskService {
    List<QuartzTask> queryAllQuartzTask();

    boolean addQuartzTask(QuartzTask task);
}
