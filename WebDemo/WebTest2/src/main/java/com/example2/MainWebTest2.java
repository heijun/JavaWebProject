package com.example2;

import com.example2.entity.Book;
import com.example2.entity.Student;
import com.example2.mapper.BookMapper;
import com.example2.mapper.StudentMapper;
import com.example2.mapper.StudentsMapper;
import org.apache.ibatis.session.SqlSession;
import java.io.FileNotFoundException;
import java.util.List;


public class MainWebTest2 {
    public static void main(String[] args) throws FileNotFoundException {

/*        try (SqlSession sqlSession1 = MybatisUtil.getSession(true)){
            StudentMapper testMapper = sqlSession1.getMapper(StudentMapper.class);

            System.out.println(testMapper.getStudent(7, "小明"));

        }*/

        try (
                SqlSession sqlSession1 = MybatisUtil.getSession(true)) {
            StudentMapper studentMapper=sqlSession1.getMapper(StudentMapper.class);
            List<Student> students=studentMapper.selectStudent();
            students.forEach(System.out::println);

        }

        try (
                SqlSession sqlSession4 = MybatisUtil.getSession(true)) {
            StudentMapper studentMapper=sqlSession4.getMapper(StudentMapper.class);
            System.out.println(studentMapper.addStudent(new Student().setName("小李").setSex("男").setGrade(2018)));


        }

    }
}
