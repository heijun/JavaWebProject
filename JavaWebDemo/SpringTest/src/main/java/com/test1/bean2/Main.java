package com.test1.bean2;

import com.test1.config.MainConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //使用AnnotationConfigApplicationContext来实现注解配置
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfiguration.class); //这里需要告诉Spring哪个类作为配置类
        Card card = context.getBean(Card.class);  //容器用法和之前一样

        Student student=context.getBean(Student.class);
        student.test("lbwnb");
        System.out.println(student.toString());
        System.out.println(card);


    }
}
