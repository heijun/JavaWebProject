package junit;

import org.junit.Assert;
import org.junit.Test;

public class TestMain2 {
    @Test
    public void method(){
        System.out.println("我是测试案例！");
        Assert.assertEquals(1, 2);    //参数1是期盼值，参数2是实际测试结果值
    }
}