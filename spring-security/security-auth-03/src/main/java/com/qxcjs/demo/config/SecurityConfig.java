package com.qxcjs.demo.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;

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

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                /**
                 * 如果没有 AnonymousAuthenticationFilter，当访问一个需要认证才能访问的接口时，会直接在 AbstractSecurityInterceptor#credentialsNotFound 抛 AuthenticationException
                 * 这样在 ExceptionTranslationFilter 就不会再判断 AccessDeniedException
                 */
//                .and().anonymous()
                .and().apply(new DefaultLoginPageConfigurer<>())
                .and().exceptionHandling()
                /**
                 * new DefaultLoginPageConfigurer<>() 需要放到  formLogin 之前
                 */
                .and().formLogin()
        ;
    }
}
