package com.test3.lock;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedUsage {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池同时执行任务
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 执行两次静态方法
        threadPool.execute(() -> {
            staticMethod();
        });
        threadPool.execute(() -> {
            staticMethod();
        });

        // 执行两次普通方法
        threadPool.execute(() -> {
            SynchronizedUsage usage = new SynchronizedUsage();
            usage.method();
        });
        threadPool.execute(() -> {
            SynchronizedUsage usage2 = new SynchronizedUsage();
            usage2.method();
        });
    }

    /**
     * synchronized 修饰普通方法
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    public synchronized void method() {
        System.out.println("普通方法执行时间：" + LocalDateTime.now());
        try {
            // 休眠 3s
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * synchronized 修饰静态方法
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    public static synchronized void staticMethod() {
        System.out.println("静态方法执行时间：" + LocalDateTime.now());
        try {
            // 休眠 3s
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}