package com.qxcjs.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.mapper.QuartzTaskMapper;
import com.qxcjs.demo.service.QuartzTaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Log4j2
@Service
public class QuartzTaskServiceImpl implements QuartzTaskService {
    @Resource
    private QuartzTaskMapper quartzTaskMapper;

    @Override
    public List<QuartzTask> queryAllQuartzTask() {
        LambdaQueryWrapper<QuartzTask> queryWrapper = new QueryWrapper<QuartzTask>().lambda()
                .ge(QuartzTask::getDelFlag, 0);
        List<QuartzTask> tasks = quartzTaskMapper.selectList(queryWrapper);
        return tasks;
    }

    @Override
    public boolean addQuartzTask(QuartzTask task) {
        int insert = quartzTaskMapper.insert(task);
        if (insert != 1) {
            return false;
        }
        return true;
    }
}
