package com.qxcjs.demo.config;

import com.qxcjs.demo.annotation.Auth;
import com.qxcjs.demo.entity.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import java.util.*;

/**
 *
 */
@Component
@Log4j2
public class ApplicationStartup implements ApplicationRunner {
    @Autowired
    private RequestMappingInfoHandlerMapping requestMappingInfoHandlerMapping;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 扫描并获取所有需要权限处理的接口资源(该方法逻辑写在下面)
        List<Resource> list = getAuthResources();
        log.info("resources : {}", list.toString());
        /**
         * 1. 初始化权限到数据库中，只新增，不修改（修改只在web页面中操作）
         * 2. 将resource放到自定义的 SecurityMetadataSource 缓存里
         */
    }

    /**
     * 扫描并返回所有需要权限处理的接口资源
     */
    private List<Resource> getAuthResources() {
        // 接下来要添加到数据库的资源
        List<Resource> list = new LinkedList<>();
        // 拿到所有接口信息，并开始遍历
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingInfoHandlerMapping.getHandlerMethods();
        handlerMethods.forEach((info, handlerMethod) -> {
            // 拿到类(模块)上的权限注解
            Auth moduleAuth = handlerMethod.getBeanType().getAnnotation(Auth.class);
            // 拿到接口方法上的权限注解
            Auth methodAuth = handlerMethod.getMethod().getAnnotation(Auth.class);
            // 模块注解和方法注解缺一个都代表不进行权限处理
            if (moduleAuth == null || methodAuth == null) {
                return;
            }

            // 拿到该接口方法的请求方式(GET、POST等)
            Set<RequestMethod> methods = info.getMethodsCondition().getMethods();
            // 如果一个接口方法标记了多个请求方式，权限id是无法识别的，不进行处理
            if (methods.size() != 1) {
                return;
            }
            // 将请求方式和路径用`:`拼接起来，以区分接口。比如：GET:/user/{id}、POST:/user/{id}
            String path = methods.toArray()[0] + ":" + info.getPatternsCondition().getPatterns().toArray()[0];
            // 将权限名、资源路径、资源类型组装成资源对象，并添加集合中
            Resource resource = new Resource();
            resource.setType(1)
                    .setPath(path)
                    .setName(methodAuth.name())
                    .setId(moduleAuth.id() + methodAuth.id());
            list.add(resource);
        });
        return list;
    }
}
