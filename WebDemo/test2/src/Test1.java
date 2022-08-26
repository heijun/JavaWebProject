import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test1 {
    public static void main(String[] args) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
             Statement statement = connection.createStatement()){

            statement.addBatch("insert into book values (5, 'java并发编程','不错的书',14.5)");
            statement.addBatch("insert into book values (6, 'java虚拟机技术','不错的讲这方面的书',18.5)");   //添加每一条批处理语句
            statement.executeBatch();   //一起执行

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
