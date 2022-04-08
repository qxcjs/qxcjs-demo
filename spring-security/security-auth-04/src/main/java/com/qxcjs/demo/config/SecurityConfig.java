package com.qxcjs.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("wt").password(passwordEncoder().encode("123456")).roles("admin").build());
        manager.createUser(User.withUsername("ellisli").password(passwordEncoder().encode("123456")).roles("user").build());
        return manager;
    }

    /**
     * configure 优先级高于 userDetailsService 中配置，会导致 userDetailsService 无效
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存用户，用来测试
        auth.inMemoryAuthentication()
                .withUser("liss")
                .password(passwordEncoder().encode("123456"))
                .roles("admin");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                /**
                 * 1. successForwardUrl 请求转发，转发的请求类型不会变, 比如登录时是POST, 则转发时还是POST, 转发后浏览器的地址不会变，登录成功后不会跳转到原来的地址
                 * springmvc不支持post请求直接返回页面, successForwardUrl是转发过来的，所以还是post请求，这里就报错了, 所以如果要是用successForwardUrl
                 * 1. controller中最后在重定向一下, return "redirect:/main.html";
                 * 2. controller请求方式修改为 @PostMapping
                 *
                 * 2. defaultSuccessUrl 302重定向, 重定向请求类型为GET, 登录成功后会跳转到原来的地址
                 *
                 */
                .formLogin()
//                .defaultSuccessUrl("/getHello", true)
                .successForwardUrl("/postHello")
                .permitAll();
    }
}
