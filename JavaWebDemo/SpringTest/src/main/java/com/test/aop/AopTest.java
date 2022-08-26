package com.test.aop;

import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

public class AopTest {

    //执行之后的方法
    public void after(){
        System.out.println("我是执行之后");
    }

    //执行之前的方法
//执行之前的方法
    public void before(JoinPoint point){
        System.out.println("我是执行之前");
        System.out.println(point.getTarget());  //获取执行方法的对象
        System.out.println(Arrays.toString(point.getArgs()));  //获取传入方法的实参
    }
}