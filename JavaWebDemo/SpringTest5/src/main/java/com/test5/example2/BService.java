package com.test5.example2;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class BService {
    // @Autowired
    public AService aService;

    public void setaService(AService aService) {
        this.aService = aService;
    }
}