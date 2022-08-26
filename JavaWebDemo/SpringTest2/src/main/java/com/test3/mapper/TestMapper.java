package com.test3.mapper;

import com.test3.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface TestMapper {

    @Select("select * from student where sid = 1")
    Student getStudent();
}