package com.qxcjs.demo.schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qxcjs.demo.commons.constant.ScheduledConstant;
import com.qxcjs.demo.entity.QuartzTask;
import com.qxcjs.demo.utils.SpringUtils;
import lombok.extern.log4j.Log4j2;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

@Log4j2
public class ScheduleJob implements Job {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("run job");
        //获取调度数据
        QuartzTask quartzTask = (QuartzTask) context.getJobDetail().getJobDataMap().get(ScheduledConstant.QUARTZ_TASK);

        /**
         * 重启时，Quartz会自动加载 qrtz_ 中任务，会导致获取不到任务参数信息
         */

        /**
         * 查询任务的参数
         */
//        ScheduledJobServiceImpl quartzService = SpringUtils.getBean(ScheduledJobServiceImpl.class);

        //获取对应的Bean
        Object object = SpringUtils.getBean(quartzTask.getBeanName());

        try {
            // 利用反射执行对应方法
            if (quartzTask.getInvokeParamClass() != null && quartzTask.getInvokeParam() != null) {
                Class<?> clazz = Class.forName(quartzTask.getInvokeParamClass());
                Method method = object.getClass().getMethod(quartzTask.getMethodName(), clazz);
                Object params = objectMapper.readValue(quartzTask.getInvokeParam(), clazz);
                method.invoke(object, params);
            } else if (quartzTask.getInvokeParam() != null && quartzTask.getInvokeParamClass() == null) {
                Method method = object.getClass().getMethod(quartzTask.getMethodName(), String.class);
                method.invoke(object, quartzTask.getInvokeParam());
            } else {
                Method method = object.getClass().getMethod(quartzTask.getMethodName());
                method.invoke(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
