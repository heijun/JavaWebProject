package com.test5.example2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class AService {
    // @Autowired
    public BService bService;
    // 为什么Aservice在创建的时候 为什么Bservice比ASERVICE 先创建
    public AService() {
        System.out.println("AService被Java的反射技术创建");
    }
    public void setbService(BService bService) {
        this.bService = bService;
    }

}