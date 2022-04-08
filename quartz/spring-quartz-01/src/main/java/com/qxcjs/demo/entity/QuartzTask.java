package com.qxcjs.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class QuartzTask implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String jobName;
    private String jobGroup;
    private String beanName;
    private String methodName;
    private String invokeParam;
    private String invokeParamClass;
    private String cronExpression;
    private int status;
    private int delFlag;
    private String description;
}
