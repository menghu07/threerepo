package com.apeny.thread.init;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/9/3.
 */
public class OrderThread {
    private static int a = 0;
    private static int b = 0;
    private static int x = 0;
    private static int y = 0;
    private static Set<String> set = new HashSet<>();
    public static void main(String[] args) {
        try {
            whileTrue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void whileTrue() throws InterruptedException {
        while (true) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });
            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            String value = x + "," + y;
            set.add(value);
            if (set.size() > 3) {
                System.out.println("set === " + set);
                return;
            }
            a = b = x = y = 0;
            TimeUnit.MICROSECONDS.sleep(10);
        }
    }


}
