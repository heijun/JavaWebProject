package com.test2;

import com.test2.config.TestConfiguration2;
import com.test2.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
@Slf4j
public class Main {
    public static void main(String[] args) {
        log.info("项目正在启动...");

        ApplicationContext context2 = new AnnotationConfigApplicationContext(TestConfiguration2.class);
        TestService testService=context2.getBean(TestService.class);
        System.out.println(testService.getStudent());
        testService.test();
    }
}
