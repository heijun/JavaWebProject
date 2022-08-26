package logfile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class TestMain5 {
    public static void main(String[] args) throws IOException {
        // 首先获取日志打印器
        Logger logger = Logger.getLogger(TestMain5.class.getName());
        // 调用info来输出一个普通的信息，直接填写字符串即可
        logger.info("我是普通的日志");




        Logger logger2 = Logger.getLogger(TestMain5.class.getName());
        //自定义过滤规则
        logger2.setFilter(record -> !record.getMessage().contains("普通"));
        logger2.log(Level.SEVERE, "严重的错误", new IOException("我就是错误"));
        logger2.log(Level.WARNING, "警告的内容");
        logger2.log(Level.INFO, "普通的信息");
        logger2.log(Level.CONFIG, "级别低于普通信息,无法打印");


        Logger logger3 = Logger.getLogger(TestMain5.class.getName());

        //修改日志级别
        logger3.setLevel(Level.CONFIG);
        //不使用父日志处理器
        logger3.setUseParentHandlers(false);
        //使用自定义控制台日志处理器
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(Level.CONFIG);
        logger3.addHandler(handler);
        handler.setFormatter(new XMLFormatter());

        //添加输出到本地文件
        FileHandler fileHandler = new FileHandler("test.log",true);
        fileHandler.setLevel(Level.WARNING);
        logger3.addHandler(fileHandler);

        fileHandler.setFormatter(new SimpleFormatter());

        logger3.log(Level.SEVERE, "严重的错误", new IOException("我就是错误"));
        logger3.log(Level.WARNING, "警告的内容");
        logger3.log(Level.INFO, "普通的信息");
        logger3.log(Level.CONFIG, "级别低于普通信息");

        System.out.println(logger3.getParent().getClass());



        logger3.setUseParentHandlers(false);

        //为了让颜色变回普通的颜色，通过代码块在初始化时将输出流设定为System.out
        ConsoleHandler handler2 = new ConsoleHandler(){{
            setOutputStream(System.out);
        }};
        //创建匿名内部类实现自定义的格式
        handler2.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                String time = format.format(new Date(record.getMillis()));  //格式化日志时间
                String level = record.getLevel().getName();  // 获取日志级别名称
                // String level = record.getLevel().getLocalizedName();   // 获取本地化名称（语言跟随系统）
                String thread = String.format("%10s", Thread.currentThread().getName());  //线程名称（做了格式化处理，留出10格空间）
                long threadID = record.getThreadID();   //线程ID
                String className = String.format("%-20s", record.getSourceClassName());  //发送日志的类名
                String msg = record.getMessage();   //日志消息

                //\033[33m作为颜色代码，30~37都有对应的颜色，38是没有颜色，IDEA能显示，但是某些地方可能不支持
                return "\033[38m" + time + "  \033[33m" + level + " \033[35m" + threadID
                        + "\033[38m --- [" + thread + "] \033[36m" + className + "\033[38m : " + msg + "\n";
            }
        });
        logger3.addHandler(handler2);

        logger3.info("我是测试消息1...");
        logger3.log(Level.INFO, "我是测试消息2...");
        logger3.log(Level.WARNING, "我是测试消息3...");



}
}
