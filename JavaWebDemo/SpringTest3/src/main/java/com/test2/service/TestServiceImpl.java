package com.test2.service;

import com.test2.entity.Student;
import com.test2.mapper.TestMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
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

            test2();

        testMapper.insertStudent();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void test2() {
        testMapper.insertStudent();
        throw new RuntimeException("我是测试异常！");  //发生异常时，会回滚另一个事务吗？

    }
}
