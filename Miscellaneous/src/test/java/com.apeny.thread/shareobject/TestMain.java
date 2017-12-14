package com.apeny.thread.shareobject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apeny on 2017/12/14.
 */
public class TestMain {
    private static boolean ready;
    private static int number;
    private int i;
    private volatile Map<String, TestMain> map = new HashMap<>();

    public TestMain() {

    }

    public TestMain(int i) {
        this.i = i;
    }

    private static class ReaderThread extends Thread {
        public void run() {
//            System.out.println("first read number: " + ready);
            while (!ready) {
                System.out.println("读线程>>>>>>" + ready);
                //放弃cpu
                Thread.yield();
                System.out.println("number: " + number + ", ready" + ready);
            }
            System.out.println("second number: " + ready);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        System.out.println("main thread.");
        number = 23;
        ready = true;
    }

    private void writeMap() {
        Map<String, TestMain> map = new HashMap<>();
        map.put("1", new TestMain(1));
        System.out.println("write map");
        map.put("2", new TestMain(2));
        this.map = map;
    }

    private void readMap() {
        Map<String, TestMain> localMap = map;
        for (Map.Entry<String, TestMain> e : localMap.entrySet()) {
            System.out.println("key: " + e.getKey() + "; value: " + e.getValue());
        }
    }
}
