package mapper;

import entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;



public interface StudentMapper {
    @Select("select * from student where sid = 1")
    Student getStudent();

    @Insert("insert into student(name, sex,grade) values('测试', '男','2019')")
    void insertStudent();
}

