package logfile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestMain6 {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileInputStream("test.properties"));
        properties.put("数值","123");
        System.out.println(properties);
        System.out.println(properties.getProperty("name"));



        System.getProperties().forEach((k,v)->System.out.println(k+":"+v));
    }
}
