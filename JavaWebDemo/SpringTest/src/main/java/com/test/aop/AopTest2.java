package com.test.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
@Aspect
public class AopTest2 {
    @PostConstruct
    public void init(){
        System.out.println("我是初始化方法！1");
    }

    @Before("execution(* com.test1.bean2.Student.test(..))")
    public void before(){
        System.out.println("我是之前执行的内容！");
    }

    @AfterReturning(value = "execution(* com.test1.bean2.Student.test(..))", returning = "returnVal")
    public void after(Object returnVal){
        System.out.println("方法已返回，结果为："+returnVal);
    }

    @Around("execution(* com.test1.bean2.Student.test(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("方法执行之前！");
        Object val = point.proceed();
        System.out.println("方法执行之后！");
        return val;
    }
}
