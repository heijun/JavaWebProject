package com.example2;

import com.example2.entity.Student;
import com.example2.mapper.StudentMapper;
import com.example2.mapper.StudentsMapper;
import org.apache.ibatis.session.SqlSession;

public class WebTest2_Test2 {
    public static void main(String[] args) throws InterruptedException {
        try (SqlSession sqlSession = MybatisUtil.getSession(true)){
            StudentMapper testMapper = sqlSession.getMapper(StudentMapper.class);
            Student student1 = testMapper.getStudentBySid(1);
            Student student2 = testMapper.getStudentBySid(1);
            System.out.println(student1 == student2);
        }
    }
}
