/**
* anyRequest          |   匹配所有请求路径
* access              |   SpringEl表达式结果为true时可以访问
* anonymous           |   匿名可以访问
* denyAll             |   用户不能访问
* fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
* hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
* hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
* hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
* hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
* hasRole             |   如果有参数，参数表示角色，则其角色可以访问
* permitAll           |   用户可以任意访问
* rememberMe          |   允许通过remember-me登录的用户访问
* authenticated       |   用户登录后可访问
**/

- OAuth2
- Access Token
- JSON Web Token

## WebSecurityConfigurerAdapter 初始化

### SecurityContextHolderStrategy
ThreadLocalSecurityContextHolderStrategy 默认策略
GlobalSecurityContextHolderStrategy
InheritableThreadLocalSecurityContextHolderStrategy


## 授权流程

### RoleHierarchyImpl
角色继承关系

### GrantedAuthority

### AbstractRequestMatcherRegistry

### AbstractSecurityInterceptor
AccessDecisionManager 在过滤器 AbstractSecurityInterceptor 被调用

### SecurityExpressionOperations

### PermissionEvaluator

### SecurityMetadataSource
是一个接口，用来加载访问时所需要的具体权限，**这里只是获取权限的信息，不做权限比对**，还有一个接口 FilterInvocationSecurityMetadataSource 继承于它

### AccessDecisionManager
是一个接口，定义了在授权时如何决策的方法，即**会做权限的比对**，具体的实现类有3个：AffirmativeBased (一票通过) ，ConsensusBased (少数服从多数)，UnanimousBased (一票反对)。其中，一票通过是默认的决策。

### 授权的核心组件

AbstractAccessDecisionManager : 从这个抽象类可以看出，决策的依据是是选票（Voter）的List集合。

AccessDecisionVoter : 是一个接口，定义了vote方法，它的实现类也有很多，比如：
AuthenticatedVoter：比如某个用户对某个资源的访问是isAuthenticated()（即认证用户），该投票就通过
RoleVoter：比如某个用户对某个资源的访问是hasAnyRole("xxx")或hasRole("xxx")（即有该Role的用户）


## 认证方式
- 可以通过 form 表单来认证
- 可以通过 HttpBasic 来认证

## ACL 模型 和 RBAC 模型
ACL : Access Control List 访问控制列表，spring-security-acl
RBAC : Role-Based Access Controller 基于角色访问控制模型

## 忽略拦截
- 设置该地址匿名访问
- 直接过滤掉该地址，即该地址不走 Spring Security 过滤器链
```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/vercode");
    }
}
```

## 用户名密码获取方式
- 在内存中初始化
- 通过自定义接口调用

从 Spring5 开始，强制要求密码要加密，如果非不想加密，可以使用一个过期的 PasswordEncoder 的实例 NoOpPasswordEncoder，但是不建议这么做，毕竟不安全。Spring Security 中提供了 BCryptPasswordEncoder 密码编码工具，可以非常方便的实现密码的加密加盐，相同明文加密出来的结果总是不同，这样就不需要用户去额外保存盐的字段了，这一点比 Shiro 要方便很多。

## 自定义权限处理 hasPermission
https://segmentfault.com/a/1190000039421766
PermissionEvaluator

指定对应匹配规则的处理器
https://juejin.cn/post/6985514591983763463

## 必须配置类
web安全配置类 WebSecurityConfig
用户身份权限认证类 UserDetailService
资源权限认证器 AccessDecisionManager
请求过滤类 AbstractSecurityInterceptor
加载资源与权限的关系 FilterInvocationSecurityMetadataSource

## 可选处理类
权限不足处理类 AccessDeniedHandler
认证异常处理类 AuthenticationException
登录成功后处理类 AuthenticationSuccessHandler
登录失败后处理类 AuthenticationFailureHandler
退出系统处理类 LogoutHandler
成功退出系统处理类 LogoutSuccessHandler
登录验证（比如校验验证码） UsernamePasswordAuthenticationFilter
认证失败处理类 AuthenticationEntryPointFailureHandler

## 自定义用户信息缓存 UserCache
NullUserCache
SpringCacheBasedUserCache

## login
在 Spring Security 中，如果我们不做任何配置，默认的登录页面和登录接口的地址都是 /login，也就是说，默认会存在如下两个请求：
- GET http://localhost:8080/login
- POST http://localhost:8080/login
  如果是 GET 请求表示你想访问登录页面，如果是 POST 请求，表示你想提交登录数据。

```java
.and()
.formLogin()
.loginPage("/login.html")
.permitAll()
.and()
```
当我们配置了 loginPage 为 /login.html 之后，这个配置从字面上理解，就是设置登录页面的地址为 /login.html。
实际上它还有一个隐藏的操作，就是登录接口地址也设置成 /login.html 了。换句话说，新的登录页面和登录接口地址都是 /login.html，现在存在如下两个请求：
GET http://localhost:8080/login.html
POST http://localhost:8080/login.html
前面的 GET 请求用来获取登录页面，后面的 POST 请求用来提交登录数据。

登录页面和提交数据分开
```java
.and()
.formLogin()
.loginPage("/login.html")
.loginProcessingUrl("/doLogin")
.permitAll()
.and()
```

# rememberMe 原理
https://codeantenna.com/a/k7RLP8ZE02
http://www.semlinker.com/spring-security-remember-me/


# filter 顺序
https://www.cnblogs.com/snowater/p/8443100.html


JwtAuthenticationFilter extends OncePerRequestFilter
LoginFilter extends UsernamePasswordAuthenticationFilter

## spring security 登录案例
1. 通过填写用户名和密码登录。
   1.1 验证成功后, 服务端生成 JWT 认证 token, 并返回给客户端。
   1.2 验证失败后返回错误信息。
   1.3 客户端在每次请求中携带 JWT 来访问权限内的接口。
2. 每次请求验证 token 有效性和权限，在无有效 token 时抛出 401 未授权错误。
3. 当发现请求带着的 token 有效期快到了的时候，返回特定状态码，重新请求一个新 token。

https://segmentfault.com/a/1190000039297589


## mybatis 拦截器
https://mybatis.org/mybatis-3/configuration.html
https://juejin.cn/post/6945614751334400014#heading-8
https://www.cnblogs.com/flysand/p/9274997.html

## jwt 与 token + redis 方案优缺点对比
https://www.zhihu.com/question/274566992


## 前端密码加密 后端密码加密
1. 后端生成公钥和私钥，将公钥给前端
2. 前端通过公钥将密码加密后发给后端
3. 后端通过私钥将加密后的密码解密
4. 比对账号密码，判断是否成功登录


## RememberMeServices
一些默认实现
TokenBasedRememberMeServices
PersistentTokenBasedRememberMeServices

## Authentication
AbstractAuthenticationToken
AnonymousAuthenticationToken
UsernamePasswordAuthenticationToken
RememberMeAuthenticationToken


## AuthenticationManager
ProviderManager

## AuthenticationProvider
DaoAuthenticationProvider
AnonymousAuthenticationProvider
RememberMeAuthenticationProvider

## 默认filter作用及顺序
FilterComparator

### 认证流程
AbstractAuthenticationProcessingFilter
SecurityContextHolderAwareRequestFilter
UsernamePasswordAuthenticationFilter
RememberMeAuthenticationFilter

org.springframework.security.web.authentication.rememberme.CookieTheftException: Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack.
Token 认证不通过时会认证 RememberMeAuthenticationFilter，此时前面的请求 RememberMe 认证通过会更新 RememberMe Cookie，而后面的请求因为还是旧的 RememberMe Cookie 导致请求失败。

### 授权流程

### 登录成功后会先校验权限，再校验token


### 退出登录
logout 退出登录不会走 JwtAuthenticationFilter ，也就不会将body中的 token 放到 header 中

# 各个组件使用

http.authorizeRequests()
1. `http.authorizeRequests()` 配置并非总在第一行出现，如果只有一个过滤器链，他总是在第一行出现，表示该过滤器链的拦截规则是 `/**`
2. 请求只有先被过滤器链拦截下来，接下来才会进入到不同的 Security Filters 中进行处理
3. `http.antMatchers("/admin/**").hasRole("admin").antMatchers("/user/**").hasRole("user")` 有先后顺序
4. `http.anyRequest().authenticated()` 在最后配置，表示所有请求都要认证，不一定需要权限，权限是根据 `antMatchers("/admin/**").hasRole("admin")` 这些去判断的

## 自定义 UsernamePasswordAuthenticationFilter
当我们代替了 UsernamePasswordAuthenticationFilter 之后，原本在 SecurityConfig#configure 方法中关于 form 表单的配置就会失效，那些失效的属性，都可以在配置 LoginFilter 实例的时候配置。

1. [自定义UsernamePasswordAuthenticationFilter](http://www.javaboy.org/2020/0331/spring-security-json.html)

## 集群模式session共享
1. [集群化部署，Spring Security 要如何处理 session 共享？](http://www.javaboy.org/2020/0518/springsecurity-spring-session.html)

## 验证码应该在哪一步校验

## 单机模式验证码存储，集群模式验证码存储

## 多种登录方式，账号密码登录，邮箱验证码登录，手机号验证码登录


## SecurityConfigurer 和 SecurityBuilder 关系

SecurityConfigurer :
SecurityConfigurerAdapter :
SecurityBuilder :

## AbstractAuthenticationFilterConfigurer

formLogin() 普通表单登录
oauth2Login() 基于 OAuth2.0 认证/授权协议
openidLogin() 基于 OpenID 身份认证规范
以上三种方式统统是 AbstractAuthenticationFilterConfigurer 实现的

## SecurityBuilder
用来创建 SpringSecurity 相关对象。

## SecurityConfigurer
用来配置 SecurityBuilder。 所有 SecurityConfigurer 首先调用它们的 init(SecurityBuilder) 方法。 在调用了所有 init(SecurityBuilder) 方法之后，将调用每个 configure(SecurityBuilder) 方法。

比如 AuthenticationManagerBuilder 对应 InitializeAuthenticationProviderManagerConfigurer

## @EnableWebSecurity
@EnableWebSecurity : 初始化 Spring Security 默认配置
WebSecurityConfigurerAdapter : 重写方法以覆盖 Spring Security 的默认配置

@EnableWebSecurity 初始化细节可以查看以下几个 Configuration
- WebSecurityConfiguration
- AuthenticationConfiguration
- ObjectPostProcessorConfiguration



org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter@225c531
org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter@2197ec60
org.springframework.security.web.access.intercept.FilterSecurityInterceptor@5b0c572d


BasicAuthenticationFilter 默认登录方式
1. GET请求
2. 密码会放在 Authorization 中以username:password方式拼接，并使用Base64编码