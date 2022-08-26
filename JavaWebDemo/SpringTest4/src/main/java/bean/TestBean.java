package bean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Component
public class TestBean {

    @PostConstruct
    void init(){
        System.out.println("我被初始化了！");
    }
}