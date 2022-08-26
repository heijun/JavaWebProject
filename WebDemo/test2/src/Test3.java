import java.lang.reflect.Constructor;
import java.sql.*;

public class Test3 {

    public static void main(String[] args) throws ClassNotFoundException {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookmanager","root","123456");
             Statement statement = connection.createStatement()){

            //3. 执行SQL语句，并得到结果集
            ResultSet set = statement.executeQuery("select * from book");
            //4. 查看结果
            while (set.next()){
                Book book = convert(set, Book.class);
                if(book != null) book.print();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
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
}
