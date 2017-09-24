package com.apeny.thread.init;

import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017年09月22日.
 */
public class OneThreadInit implements Runnable {

    @Override
    public void run() {
        NonsafeObject.init();
    }

    public static void main(String[] args) {
        OneThreadInit one = new OneThreadInit();
        OneThreadInit one1 = new OneThreadInit();
        TwoThreadGet two = new TwoThreadGet();
        TwoThreadGet three = new TwoThreadGet();
        TwoThreadGet four = new TwoThreadGet();
        TwoThreadGet five = new TwoThreadGet();
        new Thread(three).start();
        new Thread(two).start();
        new Thread(one).start();
        try {
            TimeUnit.SECONDS.sleep(2);
            new Thread(one1).start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(four).start();
        new Thread(five).start();
    }
}
