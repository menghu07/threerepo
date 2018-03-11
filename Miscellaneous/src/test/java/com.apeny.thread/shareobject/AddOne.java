package com.apeny.thread.shareobject;

/**
 * Created by apeny on 2018/3/11.
 */
public class AddOne {
    private static int i = 0;

    public static void main(String[] args) {
        startTwoThread();
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

    private static class AddOneThread extends Thread {
        private String name;

        AddOneThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int times = 100;
            for (int j = 0; j < times; j++) {
                System.out.println("before add "+ Thread.currentThread() + ", i = " + i++);
                System.out.println("after add " + Thread.currentThread() + ", i = " + i);
            }
        }
    }

}
