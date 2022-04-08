package com.qxcjs.demo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            /**
             * 用来加密输入的密码，可以自定义 RSA 加密
             * @param rawPassword 用户输入的密码
             */
            @Override
            public String encode(CharSequence rawPassword) {
                String password = rawPassword.toString();
                log.info("password encode : {}", password);
                return password;
            }

            /**
             * 用来比较密码是否匹配
             * @param rawPassword 用户输入的密码
             * @param encodedPassword 持久化的密码，一般从数据库中获取
             * @return
             */
            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                log.info("rawPassword : {} , encodedPassword : {}", rawPassword.toString(), encodedPassword);
                return false;
            }
        };
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存用户，用来测试
        auth.inMemoryAuthentication()
                .withUser("liss")
                .password(passwordEncoder().encode("123456"))
                .roles("admin");
    }

    /**
     * 角色继承
     */
    @Bean
    RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("ROLE_admin > ROLE_user");
        return hierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .rememberMe()
                .and()
                .csrf().disable();
    }
}
