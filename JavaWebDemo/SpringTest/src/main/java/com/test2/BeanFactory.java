package com.test2;

import com.test2.impl.BImplementation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BeanFactory {

    private static Properties prop = new Properties();

    //将加载写到静态代码块中,只用加载一次
    static{
        try {
            //1、加载配置文件
            prop.load(new FileInputStream("src/beanFactory.properties"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用泛型实现通用
     * @param c
     * @return
     */
    public static <T> T getBean(Class<T> c) {
        try {
            //2、取出用哪个实现类,c.getSimpleName()会返回类名(不含包名)
            String className = prop.getProperty(c.getSimpleName());
            //3、根据完整类名创建实例并返回
            @SuppressWarnings("unchecked")
            Class<T> clazz = (Class<T>) Class.forName(className);
                return clazz.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
