package config;

import entity.Card;
import entity.Student;
/*import mapper.StudentMapper;*/
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import post.TestBeanDefinitionRegistrar;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@MapperScan("mapper")
@ComponentScan({"aop","mapper","service","post"})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@Configuration
@Import({TestConfiguration.class, TestBeanDefinitionRegistrar.class})
public class MainConfiguration {
    public MainConfiguration() throws Exception {
    }

    private static <T> T convert(ResultSet set, Class<T> clazz){
        try {
            Constructor<T> constructor = clazz.getConstructor(clazz.getConstructors()[0].getParameterTypes());   //默认获取第一个构造方法
            Class<?>[] param = constructor.getParameterTypes();  //获取参数列表
            Object[] object = new Object[param.length];  //存放参数
            for (int i = 0; i < param.length; i++) {   //是从1开始的
                object[i] = set.getObject(i+1);
                if(object[i].getClass() != param[i])
                    throw new SQLException("错误的类型转换："+object[i].getClass()+" -> "+param[i]);
            }
            return constructor.newInstance(object);
        } catch (ReflectiveOperationException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //没有配置任何Bean
    @Resource
    Connection connection;

    @Bean
    @Scope("prototype")
    public Card card(){
        return new Card();
    }

    @Bean
    public Student student(@Autowired Card card) {

        Student student = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("select * from book.student where sid = 1");
            while (res.next()) {
                student = convert(res, Student.class);
                student.setCard(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;

    }

    @Bean
    public TransactionManager transactionManager(@Autowired DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

/*    @Autowired
    @Bean
    public Student student( Card card,StudentMapper mapper) throws Exception {

      Student student= mapper.getStudent();
      student.setCard(card);
      return  student;
    }*/
}