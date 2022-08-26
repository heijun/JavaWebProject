package service;

import mapper.StudentMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
public class TestServiceImpl implements TestService{

    @Resource
    StudentMapper mapper;

    @Transactional
    public void test() {
        test2();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void test2() {
        mapper.insertStudent();
        if(true) throw new RuntimeException("我是测试异常！");
    }
}
