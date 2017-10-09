package com.apeny.jvmpractice.garbage;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by ahu on 2017年10月04日.
 */
public class SoftRefQueue {
    private static class User {
        private int id;
        private String name;

        User() {
        }

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        softQueue();
    }

    private static void softQueue() throws InterruptedException {
        ReferenceQueue<User> queue = new ReferenceQueue<>();
        User user = new User(192, "yyyy");
        SoftReference<User> reference = new SoftReference<User>(user, queue);
        user = null;
        List<SoftReference<User>> list = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            list.add(new SoftReference<User>(new User(new Random().nextInt(), "333"), queue));
//            TimeUnit.NANOSECONDS.sleep(1);
            System.out.println("ffff");
        }
        System.gc();
        new CheckThread(queue).start();
        TimeUnit.NANOSECONDS.sleep(10);
        byte[] bytes = new byte[10 * 1024 * 1024];
        System.gc();
        TimeUnit.NANOSECONDS.sleep(10);
        System.out.println("main end...........");
    }

    static class CheckThread extends Thread {

        private ReferenceQueue<User> queue;

        CheckThread(ReferenceQueue<User> queue) {
            this.queue = queue;
        }

        public void run() {
            while (true) {
                if (queue != null) {
                    try {
                        SoftReference<User> reference = (SoftReference<User>) queue.remove();
                        if (reference != null) {
                            System.out.println("################## reference: " + reference);
                            System.exit(1);
                            System.out.println("99999999999999999");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
