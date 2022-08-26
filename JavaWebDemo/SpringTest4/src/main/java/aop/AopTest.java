package aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class AopTest {

    @Before("execution(* entity.Student.test(..))")
    public void before(JoinPoint point){
        System.out.println("参数列表："+ Arrays.toString(point.getArgs()));
        System.out.println("我是之前执行的内容！");
    }

    @AfterReturning(value = "execution(*  entity.Student.test(..))", returning = "returnVal")
    public void after(Object returnVal){
        System.out.println("方法已返回，结果为："+returnVal);
    }


}