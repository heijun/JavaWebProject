package com.test2;


public class ClassA {
    private InterfaceB clzB;
    private InterfaceB clzB2;
    private InterfaceB clzB3;
    public void doSomething() {
/*        Object obj = Class.forName(BImplementation).newInstance();
        clzB = (InterfaceB)obj;*/
        clzB=BeanFactory.getBean(InterfaceB.class);
        clzB.doIt();

        try {
            clzB2=(InterfaceB) Class.forName("com.test2.impl.BImplementation").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clzB2.doIt();

        clzB3=BeanFactory.getBean(InterfaceB.class);

        System.out.println(clzB3);
        System.out.println(clzB2);
        System.out.println(clzB);
    }

}
