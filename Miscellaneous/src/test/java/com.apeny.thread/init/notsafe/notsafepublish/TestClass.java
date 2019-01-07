package com.apeny.thread.init.notsafe.notsafepublish;

import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/9/10.
 */
public class TestClass {
    public static void main(String[] args) {
        int  i = 0;
        while (i < 1) {
            i++;
            InitClass initClass = new InitClass();
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    initClass.initialize();
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    initClass.testAssert();
                }
            });
            t1.start();
            try {
                TimeUnit.MICROSECONDS.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            t2.start();
        }

    }
}
