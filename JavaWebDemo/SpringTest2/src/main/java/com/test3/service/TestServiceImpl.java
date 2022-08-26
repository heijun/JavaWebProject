package com.test3.service;

import com.test3.entity.Student;
import com.test3.mapper.TestMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TestServiceImpl implements TestService {
    @Resource
    TestMapper testMapper;
    /*SqlSessionTemplate template;*/


    @Override
    public Student getStudent() {
     /*   TestMapper testMapper=template.getMapper(TestMapper.class);*/
        return testMapper.getStudent();
    }
}
