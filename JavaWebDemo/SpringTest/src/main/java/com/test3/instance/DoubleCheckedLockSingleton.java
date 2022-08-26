package com.test3.instance;

public class DoubleCheckedLockSingleton implements Runnable{
    //单例对象声明
    private static DoubleCheckedLockSingleton doubleCheckedLockSingleton = null;
    //私有化构造器
    private DoubleCheckedLockSingleton(){
    }
    //获取单例对象，当调用该方法时才去判断是否存在对象，不存在实例化一个对象
    public static DoubleCheckedLockSingleton getSingletonInstance1(){
        if(doubleCheckedLockSingleton == null){
            synchronized(DoubleCheckedLockSingleton.class){ //将锁的位置换在实例化对象的地方
                if(doubleCheckedLockSingleton == null){ //因为有可能两个线程同时进入到上一层判断，因此需要在同步代码块中再添加一次校验
                    return new DoubleCheckedLockSingleton();
                }
            }
        }
        return doubleCheckedLockSingleton;
    }

    public static DoubleCheckedLockSingleton getSingletonInstance2(){

        if(doubleCheckedLockSingleton == null){
            return new DoubleCheckedLockSingleton();
        }
        return doubleCheckedLockSingleton;
    }


    static DoubleCheckedLockSingleton doubleCheckedLockSingleton1=new DoubleCheckedLockSingleton();

    public static void main(String[] args) {
        Thread t1 = new Thread(doubleCheckedLockSingleton1);
        Thread t2 = new Thread(doubleCheckedLockSingleton1);
        //伪代码
        long start=System.nanoTime();   //获取开始时间
        t1.start();
        t2.start();
        long end=System.nanoTime(); //获取结束时间
        System.out.println("程序运行时间： "+(end-start)+"ns");

    }

    @Override
    public void run() {
      /*  getSingletonInstance1();*/
       getSingletonInstance2();
    }
}
