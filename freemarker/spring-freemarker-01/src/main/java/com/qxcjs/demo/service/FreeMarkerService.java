package com.qxcjs.demo.service;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
@Log4j2
public class FreeMarkerService {
    @Resource
    private FreeMarkerConfigurer freemarkerConfigurer;

    public void sendMessageUsingFreemarkerTemplate(Map<String, Object> templateModel) throws IOException, TemplateException {
        Template freemarkerTemplate = freemarkerConfigurer.getConfiguration().getTemplate("email.ftlh");
        String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerTemplate, templateModel);
        log.info("htmlBody : {}", htmlBody);
    }
}
