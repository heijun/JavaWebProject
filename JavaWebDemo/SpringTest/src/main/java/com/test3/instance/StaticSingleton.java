package com.test3.instance;

public class StaticSingleton {
    //静态内部类
    private static class StaticSingletonHolder {
        private static final StaticSingleton INSTANCE = new StaticSingleton();
    }
    //私有化构造器
    private StaticSingleton (){
    }
    //通过内部类来获取单例对象
    public static final StaticSingleton getInstance() {
        return StaticSingletonHolder.INSTANCE;
    }


}
