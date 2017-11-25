package com.apeny.thread.init;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 结论：一个线程抛出内存溢出不会影响另另一个线程，并且它占用的内存会释放
 * Created by apeny on 2017/11/25.
 */
public class OutOfMemoryThread {
    public static void main(String[] args) {
        Thread thread = new Thread(new OutOfMemThread());
        thread.start();
        try {
            int i = 0;
            if (i != 0) {
                throw new OutOfMemoryError("main throw error");
            }
            TimeUnit.SECONDS.sleep(10);
            List<Long> longList = new ArrayList<>();
            for (; i < 1000_000_000; i++) {
                longList.add(1L);
                System.out.println("main i " + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end...");
    }

    /**
     * 抛出错误
     */
    private static class OutOfMemThread implements Runnable {

        @Override
        public void run() {
            int i = 0;
            List<Long> longList = new ArrayList<>();
            for (; i < 1000000000; i++) {
                longList.add(1L);
                System.out.println("sub i " + i);
            }
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("subthread end...");
            if (i == 0) {
                throw new OutOfMemoryError("抛出错误");
            }
        }
    }
}
