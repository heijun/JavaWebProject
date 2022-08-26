package junit;

import org.junit.Assert;
import org.junit.Test;

public class TestMain3 {
    @Test
    public void method() {
        int[] arr = {0, 4, 5, 2, 6, 9, 3, 1, 7, 8};

        //错误的冒泡排序
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    // arr[j+1] = tmp;
                }
            }
        }

        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }
}
