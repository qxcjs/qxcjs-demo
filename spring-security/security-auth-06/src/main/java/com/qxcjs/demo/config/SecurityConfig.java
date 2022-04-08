package com.qxcjs.demo.config;

import com.qxcjs.demo.security.LoginFilter;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        return loginFilter;
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
                .antMatchers("/api/admin/**").hasRole("admin")
                .antMatchers("/api/user/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/api/index", true)
                .failureForwardUrl("/api/hello")
                .permitAll()
                .and()
                .rememberMe()
                .and()
                .csrf().disable()
                .addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }
}
