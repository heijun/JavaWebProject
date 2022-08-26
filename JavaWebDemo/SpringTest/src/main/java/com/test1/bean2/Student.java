package com.test1.bean2;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Data
public class Student {
    String name;
    int age;
    @Resource
    Card card;

    public Student(){
        System.out.println("我被构造了");

    }

    public void setName(String name) {
        this.name = name;
    }

    public int test(@NotNull String str){
        System.out.println("我被调用了:"+str);
        return str.length();
    }
}