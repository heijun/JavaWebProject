package com.example2;

import com.example2.entity.Student;
import com.example2.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

public class WebTest2_Test3 {
    public static void main(String[] args) {
    Student student;
    try (SqlSession sqlSession = MybatisUtil.getSession(true)){
        StudentMapper testMapper = sqlSession.getMapper(StudentMapper.class);
        student = testMapper.getStudentBySid(1);
    }

    try (SqlSession sqlSession2 = MybatisUtil.getSession(true)){
        StudentMapper testMapper2 = sqlSession2.getMapper(StudentMapper.class);
        Student student2 = testMapper2.getStudentBySid(1);
        System.out.println(student2 == student);
    }
}
}
