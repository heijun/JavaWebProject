package com.test2.service;

import com.test2.entity.Student;
import com.test2.mapper.TestMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class TestServiceImpl implements  TestService{
    @Resource
    TestMapper testMapper;

    @Override
    public Student getStudent() {
        return testMapper.getStudent();
    }
    @Transactional
    @Override
    public void test() {
        testMapper.insertStudent();
        if(true) throw new RuntimeException("我是测试异常！");
        testMapper.insertStudent();
    }
}
