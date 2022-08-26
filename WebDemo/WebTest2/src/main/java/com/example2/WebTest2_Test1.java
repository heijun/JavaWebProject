package com.example2;

import com.example2.entity.Book;
import com.example2.entity.Student;
import com.example2.entity.Students;
import com.example2.entity.Teachers;
import com.example2.mapper.*;
import org.apache.ibatis.session.SqlSession;

import java.io.FileNotFoundException;
import java.util.List;

public class WebTest2_Test1 {

    public static void main(String[] args) throws FileNotFoundException {

        try (
                SqlSession sqlSession2 = MybatisUtil.getSession(true)) {
                BookMapper bookMapper=sqlSession2.getMapper(BookMapper.class);
                List<Book> books=bookMapper.selectBook();
                books.forEach(System.out::println);

        }

        try (
                SqlSession sqlSession3 = MybatisUtil.getSession(true)) {
            StudentsMapper studentsMapper=sqlSession3.getMapper(StudentsMapper.class);
            List<Students> students=studentsMapper.selectStudent(1);
            students.forEach(System.out::println);

        }
        try (
                SqlSession sqlSession4 = MybatisUtil.getSession(true)) {
            StudentMapperCopy studentsMapper=sqlSession4.getMapper(StudentMapperCopy.class);
            System.out.println(studentsMapper.getTeacherByTid(1));


        }
        try (
                SqlSession sqlSession5 = MybatisUtil.getSession(true)) {
            TestMapper studentsMapper=sqlSession5.getMapper(TestMapper.class);
            System.out.println(studentsMapper.getTeacherBySid(1));


        }


    }
}
