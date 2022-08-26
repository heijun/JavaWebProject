package com.test.aop;


import org.springframework.aop.MethodBeforeAdvice;


import java.lang.reflect.Method;

public class AopTest3 implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("通过Advice实现AOP");
        System.out.println("方法执行的对象为：" + target);
    }
}