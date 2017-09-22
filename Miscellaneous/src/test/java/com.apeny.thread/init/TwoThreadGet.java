package com.apeny.thread.init;

import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017年09月22日.
 */
public class TwoThreadGet implements Runnable {
    private int i;

    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread() + "get twovalues: " + NonsafeObject.twoValues);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
