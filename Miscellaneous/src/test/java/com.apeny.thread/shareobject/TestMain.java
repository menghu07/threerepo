package com.apeny.thread.shareobject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017/12/14.
 */
public class TestMain {
    private static boolean ready;
    private static int number;
    private int i;
    private TestInner inner;
    private volatile Map<String, TestMain> map = new HashMap<>();

    TestMain() {

    }

    TestMain(int i) {
        this.i = i;
    }

    TestMain(int i, TestInner inner) {
        this.i = i;
        this.inner = inner;
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

    private static class ReadMapThread extends Thread {
        private final TestMain testMain;

        ReadMapThread(TestMain testMain) {
            this.testMain = testMain;
        }
        public void run() {
            while (!ready) {
                System.out.println("读线程>>>>>>" + ready);
                testMain.readMap();
                //放弃cpu
                Thread.yield();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("number: " + number + ", ready" + ready);
            }
        }
    }

    private static class TestInner {
        private int x;
        TestInner(int x) {
            this.x = x;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TestInner{");
            sb.append("x=").append(x);
            sb.append('}');
            sb.append(super.toString());
            return sb.toString();
        }
    }

    public static void main(String[] args) {
//        testOne();
        testTwo();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestMain{");
        sb.append("i=").append(i);
        sb.append(",testInner=").append(inner);
        sb.append('}');
        sb.append(super.toString());
        return sb.toString();
    }

    /**
     * 当ready 设置为true时，read线程结束
     */
    private static void testOne() {
        new ReaderThread().start();
        System.out.println("main thread.");
        number = 23;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ready = true;
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void testTwo() {
        TestMain testMain = new TestMain();
        testMain.readMap();
        new ReadMapThread(testMain).start();
        testMain.writeMap();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ready = true;
    }

    private void writeMap() {
//        Map<String, TestMain> map = new HashMap<>();
        map.put("1", new TestMain(1, new TestInner(293)));
        System.out.println("write map");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put("2", new TestMain(2, new TestInner(9384)));
        TestMain testMain = map.get("1");
        testMain.inner.x = 392;
    }

    private void readMap() {
        Map<String, TestMain> localMap = map;
        for (Map.Entry<String, TestMain> e : localMap.entrySet()) {
            System.out.println(Thread.currentThread()+ ", key: " + e.getKey() + "; value: " + e.getValue());
        }
    }
}
