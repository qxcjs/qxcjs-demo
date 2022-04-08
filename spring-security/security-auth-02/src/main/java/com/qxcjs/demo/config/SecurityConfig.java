package com.qxcjs.demo.config;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 默认用户名为 user, 密码会通过日志打印出来
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityConfig() {
        /**
         * 禁用默认的filter
         */
        super(true);
    }
}
