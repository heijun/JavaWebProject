package proxy;

import java.lang.reflect.Proxy;

public class Test6_Test2 {
    public static void main(String[] args) {
        Shopper impl = new ShopperImpl();
        Shopper shopper = (Shopper) Proxy.newProxyInstance(impl.getClass().getClassLoader(),
                impl.getClass().getInterfaces(), new ShopperProxyCopy(impl));
        shopper.saleWatermelon("小强");
        System.out.println(shopper.getClass());

    }
}