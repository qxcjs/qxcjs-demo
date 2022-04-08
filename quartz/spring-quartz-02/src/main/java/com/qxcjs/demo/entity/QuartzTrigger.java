package com.qxcjs.demo.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class QuartzTrigger implements Serializable {
    private static final long serialVersionUID = 5516171679601064915L;

    private String jobName;
    private String jobGroup;
    private String triggerName;
    private String triggerGroup;
    private String cronExpression;
    private String description;
}
