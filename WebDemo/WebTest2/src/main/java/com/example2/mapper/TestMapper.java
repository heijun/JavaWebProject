package com.example2.mapper;

import com.example2.entity.Student;
import com.example2.entity.Teachers;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestMapper {
    @Results({
            @Result(id = true, column = "tid", property = "tid"),
            @Result(column = "name", property = "name"),
            @Result(column = "tid", property = "studentList", many =
            @Many(select = "getStudentByTid")
            )
    })

    @Select("select * from teacher where tid = #{tid}")
    Teachers getTeacherBySid(int tid);

    @Select("select * from student inner join teach on student.sid = teach.sid where tid = #{tid}")
    List<Student> getStudentByTid(int tid);
}
