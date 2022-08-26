package com.example2.mapper;

import com.example2.entity.Student;
import com.example2.entity.Teachers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapperCopy {
    Teachers getTeacherByTid(int tid);
    @Select("select * from student where sid=#{sid} and name=#{name}")
    Student getStudent(@Param("sid") int sid,@Param("name") String name);

}