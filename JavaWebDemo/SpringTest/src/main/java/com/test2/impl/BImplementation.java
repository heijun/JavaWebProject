package com.test2.impl;

import com.test2.InterfaceB;

public class BImplementation implements InterfaceB {
    private BImplementation(){}
    private static BImplementation bImplementation=null;
    public static synchronized BImplementation getSingletonInstance(){ //需要加锁才能保证线程安全
        if(bImplementation == null){
            return new BImplementation();
        }
        return bImplementation;
    }
    @Override
    public void doIt() {
        System.out.println("哈哈");

    }
}
