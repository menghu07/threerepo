package com.apeny.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017年07月28日.
 */
public class NoJobWorker implements Callable<Boolean> {
    private int workId;

    public NoJobWorker(int workId) {
        this.workId = workId;
    }

    @Override
    public Boolean call() throws Exception {
        System.out.println("my workid in here." + workId + "; current thread-" + Thread.currentThread() + " time: " + System.nanoTime());
        TimeUnit.SECONDS.sleep(workId + 3);
        int i = 0;
        System.out.println("i am awaken " + Thread.currentThread() + ", time: " + System.nanoTime());
        if (i == 0) {
            throw new RuntimeException("i throw an exceptin: " + workId);
        }
        System.out.println("i not end" + Thread.currentThread() + ", time: " + System.nanoTime());
        return Boolean.TRUE;
    }
}
