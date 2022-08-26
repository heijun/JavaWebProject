package test;

import config.MainConfiguration;
import entity.Student;
/*import mapper.StudentMapper;*/
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.TestService;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ApplicationContext context= new AnnotationConfigApplicationContext(MainConfiguration.class);
/*        Student student = (Student) context.getBean("student");
        System.out.println(student);
        student.say();
        student.test("牛逼");*/
   /*     StudentMapper mapper = context.getBean(StudentMapper.class);
        mapper.getStudent().say();*/

        System.out.println(context.getBean("lbwnb"));

/*        TestService service = context.getBean(TestService.class);
        service.test2();*/



    }
}