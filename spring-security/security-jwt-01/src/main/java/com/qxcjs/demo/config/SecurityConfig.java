package com.qxcjs.demo.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.qxcjs.demo.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.DefaultLoginPageConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import java.util.Properties;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtFilter jwtFilter;
    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;

    public SecurityConfig() {
        /**
         * 禁用默认的filter
         */
        super(true);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(
                User.withUsername("admin")
                        .password(passwordEncoder().encode("123456"))
                        .roles("admin").build()
        );
        manager.createUser(
                User.withUsername("user")
                        .password(passwordEncoder().encode("123456"))
                        .roles("user").build()
        );
        return manager;
    }

    /**
     * configure 优先级高于 userDetailsService 中配置，会导致 userDetailsService 无效
     */
/*
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 内存用户，用来测试
        auth.inMemoryAuthentication()
                .withUser("liss")
                .password(passwordEncoder().encode("123456"))
                .roles("admin");
    }
*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf和frameOptions，如果不关闭会影响前端请求接口（这里不展开细讲了，感兴趣的自行搜索，不难）
        http.csrf().disable();
        http.headers().frameOptions().disable();
        // 开启跨域以便前端调用接口
        http.cors();
        // 禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
//                .antMatchers("/").permitAll() // 默认首页允许所有用户都可以访问，加了后对 localhost:port 会直接提示 404
                .antMatchers("/api/hello").permitAll()
                .antMatchers("/api/anonymous/**").anonymous()
                .antMatchers("/api/admin/**").hasRole("admin")
                .antMatchers("/api/user/**").hasRole("user")
                .anyRequest().authenticated();
        http.exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
                .accessDeniedHandler(customAccessDeniedHandler);
        http.anonymous();
        http.apply(new DefaultLoginPageConfigurer<>()); // 需要放到  formLogin 之前
        http.formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/doLogin")
                .failureHandler(loginFailureHandler)
//                .failureUrl("/login")
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .rememberMe();
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    Producer verifyCode() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "150");
        properties.setProperty("kaptcha.image.height", "50");
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }

}