package com.test2;

import com.test2.config.TestConfiguration2;
import com.test2.service.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration2.class)
public class TestMain {

    @Autowired
    TestService service;

    @Test
    public void test(){
        service.test2();
    }
}