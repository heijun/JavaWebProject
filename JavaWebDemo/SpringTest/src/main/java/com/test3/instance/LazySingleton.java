package com.test3.instance;

public class LazySingleton{
    //单例对象声明
    private static LazySingleton lazySingleton = null;
    //私有化构造器
    private LazySingleton(){
    }
    //获取单例对象，当调用该方法时才去判断是否存在对象，不存在实例化一个对象
    public static synchronized LazySingleton getSingletonInstance(){ //需要加锁才能保证线程安全
        if(lazySingleton == null){
            return new LazySingleton();
        }
        return lazySingleton;
    }
}

