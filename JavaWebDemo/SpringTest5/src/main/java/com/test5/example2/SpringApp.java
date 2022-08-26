package com.test5.example2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringApp {
    public static void main(String[] args) {
//        1. ioc容器在创建的时候所有的单例对象是不是会被创建

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
//        // 对例子情况 当你在调用的时候才获取
        AService aSerivce = applicationContext.getBean("AService", AService.class);
        BService bSerivce = applicationContext.getBean("BService", BService.class);
        aSerivce.setbService(bSerivce);
        bSerivce.setaService(aSerivce);
        // 循环引用异常 找不到对象
        /**
         * 思考问题？ 如果我们的项目对象必须要是多例？ 而且必须要循环引用  明确的指定引用那个对象
         */
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (int i = 0; i < beanDefinitionNames.length; i++) {
            System.out.println(beanDefinitionNames[i]);
        }
    }
}