package com.test2.mapper;

import com.test2.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;



@Mapper
public interface TestMapper {

    @Select("select * from student where sid = 1")
    Student getStudent();

    @Insert("insert into student(name, sex) values('测试', '男')")
    void insertStudent();
}