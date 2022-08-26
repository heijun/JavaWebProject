package proxy2;

import java.lang.reflect.Proxy;

public class Test6_Test4 {
    public static void main(String[] args) {
        proxy2.Shopper shopper = (proxy2.Shopper) Proxy.newProxyInstance(proxy2.Shopper.class.getClassLoader(),
                new Class[]{ Shopper.class },   //因为本身就是接口，所以直接用就行
                new ShopperProxy());

        shopper.sale("小强");
        System.out.println(shopper.getClass());
    }
}

