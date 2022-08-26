import java.sql.*;

public class Test2 {
    public static void main(String[] args) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
             Statement statement = connection.createStatement()){

            //3. 执行SQL语句，并得到结果集
            ResultSet set = statement.executeQuery("select * from book");
            //4. 查看结果
            while (set.next()){
                Book book = new Book(set.getInt(1), set.getString(2), set.getString(3),set.getBigDecimal(4));
                book.print();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
