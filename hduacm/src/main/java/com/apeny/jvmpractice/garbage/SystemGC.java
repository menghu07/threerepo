package com.apeny.jvmpractice.garbage;

import java.util.concurrent.TimeUnit;

/**
 * System.gc()默认触发Full GC
 * Created by ahu on 2017年10月05日.
 */
public class SystemGC {
    public static void main(String[] args) {
        gc();
    }

    private static void gc() {
        byte[] bytes = new byte[1024 * 1024];
        System.gc();
//        System.gc();
//        byte[] bytes1 = new byte[1024 * 1024 * 1024];
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}