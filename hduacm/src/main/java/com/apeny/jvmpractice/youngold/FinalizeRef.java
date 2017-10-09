package com.apeny.jvmpractice.youngold;

import java.util.concurrent.TimeUnit;

/**
 * 糟糕的finalize实现，阻止垃圾回收
 * 子类会有俩个next
 * 只有System.gc() 并行回收器Full GC -XX:-ScavengeBeforeFullGC才有效
 * Created by ahu on 2017年10月06日.
 */
public class FinalizeRef extends SuperFinalizeRef {
    private long[] longArr = new long[1024 * 1024];
    private int next = 1092;

    public static void main(String[] args) {
        OneThread one = new OneThread();
        System.gc();
        one.setDaemon(false);
        one.start();
        finalizeRef();
    }

    private static class OneThread extends Thread {
        public void run() {
            try {
                System.out.println("one thread is sleeping.");
                while (true) {
                    byte[] bytes = new byte[1024];
                }
//                TimeUnit.SECONDS.sleep(3600);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void finalizeRef() {
        while (true) {
            new FinalizeRef();
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void finalize() {
//        System.out.println("finalize is called");
        try {
            TimeUnit.SECONDS.sleep(3600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SuperFinalizeRef {
    private int next = 10;
}