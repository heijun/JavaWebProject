package Mapper;

import entity.Student;
import org.apache.ibatis.annotations.Select;

public interface TestMapper {
    @Select("select * from Student where sid = #{arg0} and sex=#{arg1}")
    Student getStudentBySidAndSex(int sid,String sex);
}
