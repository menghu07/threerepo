package com.apeny.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017/7/28.
 * 这个测试的结论：
 * 1. 前台线程会会一直执行，直到结束，不管主线程是否结束；
 * 2. NoJobWorker抛出异常时和不抛出异常结果是一样的，都必须等5个线程结束时才会使service.isTerminated() == true
 */
public class ExecutorServiceMain {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            service.submit(new NoJobWorker(i));
        }
        service.shutdown();
        while (!service.isTerminated()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("main execute..." + Thread.currentThread() + "time: " + System.nanoTime());
        }
        System.out.println("main thread ended.......");
    }
}
