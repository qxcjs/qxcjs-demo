package com.qxcjs.demo.service;

import com.qxcjs.demo.entity.User;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FreeMarkerServiceTest {

    @Resource
    private FreeMarkerService freeMarkerService;

    @Test
    public void sendMessageUsingFreemarkerTemplate() throws IOException, TemplateException {
        User user = new User();
        user.setName("liss");

        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        freeMarkerService.sendMessageUsingFreemarkerTemplate(map);
    }
}