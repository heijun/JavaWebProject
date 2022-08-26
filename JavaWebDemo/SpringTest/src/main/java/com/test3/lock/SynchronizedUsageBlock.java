package com.test3.lock;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedUsageBlock {
    public static void main(String[] args) throws InterruptedException {
        // 创建线程池同时执行任务
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        // 执行两次 synchronized(this)
        threadPool.execute(() -> {
            SynchronizedUsageBlock usage = new SynchronizedUsageBlock();
            usage.thisMethod();
        });
        threadPool.execute(() -> {
            SynchronizedUsageBlock usage2 = new SynchronizedUsageBlock();
            usage2.thisMethod();
        });

        // 执行两次 synchronized(xxx.class)
        threadPool.execute(() -> {
            SynchronizedUsageBlock usage3 = new SynchronizedUsageBlock();
            usage3.classMethod();
        });
        threadPool.execute(() -> {
            SynchronizedUsageBlock usage4 = new SynchronizedUsageBlock();
            usage4.classMethod();
        });
    }

    /**
     * synchronized(this) 加锁
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    public void thisMethod() {
        synchronized (this) {
            System.out.println("synchronized(this) 加锁：" + LocalDateTime.now());
            try {
                // 休眠 3s
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * synchronized(xxx.class) 加锁
     * 本方法的执行需要 3s（因为有 3s 的休眠时间）
     */
    public void classMethod() {
        synchronized (SynchronizedUsageBlock.class) {
            System.out.println("synchronized(xxx.class) 加锁：" + LocalDateTime.now());
            try {
                // 休眠 3s
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}