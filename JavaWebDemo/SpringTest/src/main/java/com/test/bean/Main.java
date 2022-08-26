package com.test.bean;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("test.xml");
        Student student = (Student) context.getBean("student");
        System.out.println(student);
        student.say();
        student.test("lbwnb");
        context.close();  //手动销毁容器
    }
}
