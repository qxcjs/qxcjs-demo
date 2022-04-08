package com.qxcjs.demo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class QuartzJobDetail implements Serializable {
    private static final long serialVersionUID = 711828449584214328L;

    private String jobName;
    private String jobGroup;
    private String description;
    private String jobClass;
    private Map<String, Object> map;
}
