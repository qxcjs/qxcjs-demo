参考 : [最新版 Spring Security5.x 教程合集](http://www.javaboy.org/springsecurity/)

# Spring Security

- security-auth-01 : 最小配置的 Spring Security Demo
  > 默认用户 user, 密码会在log中打印出来
  > 默认登录页 : GET /login
  > 默认登录请求 : POST /login
  > 默认登出请求 : GET /logout
  > 注意日志中默认的Filter链
    1. org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
    2. org.springframework.security.web.context.SecurityContextPersistenceFilter
    3. org.springframework.security.web.header.HeaderWriterFilter org.springframework.security.web.csrf.CsrfFilter
    4. org.springframework.security.web.authentication.logout.LogoutFilter
    5. org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
    6. org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
    7. org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
    8. org.springframework.security.web.authentication.www.BasicAuthenticationFilter
    9. org.springframework.security.web.savedrequest.RequestCacheAwareFilter
    10. org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
    11. org.springframework.security.web.authentication.AnonymousAuthenticationFilter
    12. org.springframework.security.web.session.SessionManagementFilter
    13. org.springframework.security.web.access.ExceptionTranslationFilter
    14. org.springframework.security.web.access.intercept.FilterSecurityInterceptor

- security-auth-02 : 禁用默认的SecurityFilter链
  > 注意日志中默认的Filter链，只剩下以下三个Filter
  > UsernamePasswordAuthenticationFilter
  > BasicAuthenticationFilter
  > FilterSecurityInterceptor

- security-auth-03 : 禁用默认的SecurityFilter链并自定义添加过滤器

- security-auth-04 : 多种方式自定义内存用户，自定义登陆页面接口，登录请求接口，登录失败页面接口
  > loginPage
  > loginProcessingUrl
  > failureUrl
  > defaultSuccessUrl 和 successForwardUrl 区别

- security-auth-05 :
  > 自定义密码加密和简单权限验证，参考 [Spring Security 中的授权操作原来这么简单](http://www.javaboy.org/2020/0408/spring-security-authorization.html)
  > permitAll 与 anonymous 区别

- security-auth-06 : 自定义Json参数登录方式
  > 自定义 UsernamePasswordAuthenticationFilter 后, defaultSuccessUrl 和 successForwardUrl 失效?

- security-session-01 : 单机版验证码(http session)
  > 项目打包后使用不同的端口启动
  > java -jar demo05-1.0-SNAPSHOT.jar --server.port=8080
  > java -jar demo05-1.0-SNAPSHOT.jar --server.port=8081

- security-session-02 : 集群版验证码(http session, redis)

- security-annotation-01 : 读取controller上的自定义权限注解

- security-authority-01 : Spring Security 自带权限注解
  > hasRole 和 hasAuthority 区别
  > @PreAuthorize : 方法执行前进行权限检查
  > @PostAuthorize : 方法执行后进行权限检查

- security-jwt-01  : 自定义登录流程，完整的Filter流程及异常处理demo

# Quartz

# 模板引擎

- jsp：是一种动态网页开发技术。它使用JSP标签在HTML网页中插入Java代码。
- Thymeleaf : 主要渲染xml，HTML，HTML5而且与springboot整合。
- Velocity：不仅可以用于界面展示（HTML.xml等）还可以生成输入java代码，SQL语句等文本格式。
- FreeMarker：功能与Velocity差不多，但是语法更加强大，使用方便。

## Velocity
