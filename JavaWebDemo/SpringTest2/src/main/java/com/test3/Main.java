package com.test3;

import com.test3.config.TestConfiguration;
import com.test3.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("项目正在启动...");
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfiguration.class);
        TestService testService=context.getBean(TestService.class);
        System.out.println(testService.getStudent());
    }
}
