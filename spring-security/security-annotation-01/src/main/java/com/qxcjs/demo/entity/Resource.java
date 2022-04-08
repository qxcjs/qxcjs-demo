package com.qxcjs.demo.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Resource {
    private long id;
    /**
     * 路径
     */
    private String path;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型。0为菜单，1为接口
     */
    private Integer type;
}
