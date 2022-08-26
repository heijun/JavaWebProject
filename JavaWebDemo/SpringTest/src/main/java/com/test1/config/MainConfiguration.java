package com.test1.config;

import org.springframework.context.annotation.*;

@ComponentScans(
        {      @ComponentScan("com.test1.bean2"),
                @ComponentScan("com.test.aop")}
)
@EnableAspectJAutoProxy
@Configuration
public class MainConfiguration {
    //没有配置任何Bean

/*    @Bean
    @Scope("prototype")
    public Card card(){
        return new Card();
    }

    @Bean
    @Scope("prototype")
    public  Student student() {
        Student student=new Student();
        student.setName("小明");
        return  student;
    }*/
}