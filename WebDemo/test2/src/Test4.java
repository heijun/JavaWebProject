import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class Test4 {
        public static void main(String[] args) throws ClassNotFoundException {
/*            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
                 PreparedStatement statement = connection.prepareStatement("select * from book where title= ? and `desc`=?;");
                 Scanner scanner = new Scanner(System.in)){
                statement.setString(1, scanner.nextLine());
                statement.setString(2, scanner.nextLine());

                System.out.println(statement);    //打印查看一下最终执行的
                ResultSet res = statement.executeQuery();
                while (res.next()){
                    String title=res.getString(2);
                    String desc=res.getString(3);
                    System.out.println("书名为:"+title+",描述为:"+desc);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }*/

            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
                 Statement statement = connection.createStatement();
                 Scanner scanner = new Scanner(System.in)){
                ResultSet res = statement.executeQuery("select * from book where title='"+scanner.nextLine()+"'and `desc`='"+scanner.nextLine()+"';");
                while (res.next()){
                    String title=res.getString(2);
                    String desc=res.getString(3);
                    System.out.println("书名为:"+title+",描述为:"+desc);
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }


}
