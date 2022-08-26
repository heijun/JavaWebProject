package logfile;

import lombok.extern.java.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Log(topic = "打工是不可能打工的")
public class TestMain7 {
    public static void main(String[] args) throws IOException {
        System.out.println("自动生成的Logger名称："+log.getName());
        log.info("我是日志信息");

        //获取日志管理器
        LogManager manager = LogManager.getLogManager();
        //读取我们自己的配置文件
        manager.readConfiguration(new FileInputStream("test.properties"));
        //再获取日志打印器

        log.log(Level.CONFIG, "我是一条日志信息");   //通过自定义配置文件，我们发现默认级别不再是INFO了
    }
}