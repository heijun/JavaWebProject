package com.test3.instance;

import java.io.IOException;

public class SpringTest3 {
    public static void main(String[] args) throws IOException {
    /*    LazySingleton lazySingleton=new LazySingleton();*/
        LazySingleton lazySingleton=LazySingleton.getSingletonInstance();
        System.out.println(lazySingleton);
        HungrySingleton hungrySingleton=HungrySingleton.getInstance();
        System.out.println(hungrySingleton);



    }
}
