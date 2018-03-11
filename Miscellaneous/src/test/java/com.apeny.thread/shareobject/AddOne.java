package com.apeny.thread.shareobject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/3/11.
 */
public class AddOne {
    private static int i = 0;

    public static void main(String[] args) {
//        startTwoThread();
        startThreadPool();
    }

    private static void startTwoThread() {
        AddOneThread one = new AddOneThread("thread one");
        AddOneThread two = new AddOneThread("thread two");
        try {
            one.start();
            two.start();
            one.join();
            two.join();
            System.out.println("last value i=" + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 核心线程默认不会结束（它会阻塞在获取任务方法queue.take()）,run方法不会执行完
     */
    private static void startThreadPool() {
        AddOneThread one = new AddOneThread("thread one");
        AddOneThread two = new AddOneThread("thread two");
        ThreadPoolExecutor service = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
        service.execute(one);
        service.execute(two);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("last value i=" + i);
    }

    private static class AddOneThread extends Thread {
        private String name;

        AddOneThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int times = 5;
            for (int j = 0; j < times; j++) {
                System.out.println("before add " + Thread.currentThread() + ", i = " + i++);
                System.out.println("after add " + Thread.currentThread() + ", i = " + i);
            }
        }
    }

}
