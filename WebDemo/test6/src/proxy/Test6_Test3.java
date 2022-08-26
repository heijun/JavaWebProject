package proxy;

import java.lang.reflect.Proxy;

public class Test6_Test3 {
    public static void main(String[] args) {
        Shopper shopper = (Shopper) Proxy.newProxyInstance(Shopper.class.getClassLoader(),
                new Class[]{ Shopper.class },   //因为本身就是接口，所以直接用就行
                new ShopperProxyCopy2());
        shopper.saleWatermelon("小强");
        System.out.println(shopper.getClass());
    }
}
