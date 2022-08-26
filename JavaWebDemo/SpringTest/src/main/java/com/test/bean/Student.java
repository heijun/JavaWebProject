package com.test.bean;

import java.util.Map;

//注意，这里还用不到值注入，只需要包含成员属性即可，不用Getter/Setter。
public class Student {
    String name;
    int age;
    Card card;
    Map<String, Double> map;

    //分别在test方法执行前后切入
    public int test(String str) {
        System.out.println("我是一个测试方法："+str);
        return str.length();
    }

    public void setMap(Map<String, Double> map) {
        this.map = map;
    }

    private void init(){
        System.out.println("我是初始化方法！");
    }

    public Student(String name, int age){
        System.out.println("我被构造了");
        this.name = name;
        this.age = age;
    }


    private void destroy(){
        System.out.println("我是销毁方法！");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void say(){
        System.out.println("我是："+name+",今年"+age+"岁了！"+"我的学生证："+card+",我的成绩："+ map);
    }

    public void setCard(Card card) {
        this.card=card;
    }
}


