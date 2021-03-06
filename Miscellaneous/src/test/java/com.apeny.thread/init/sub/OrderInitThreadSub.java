package com.apeny.thread.init.sub;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. 先通知，而没有线程在之前等待，后来等待线程不会直接获得通知，并直接往下执行
 * Created by monis on 2018/9/3.
 */
public class OrderInitThreadSub {
    private static long a = 0;

    private static long absoluteA = 0;

    public final static AtomicLong counter = new AtomicLong(0);

    public final static AtomicLong followCounter = new AtomicLong(0);

    public static void main(String[] args) {
        testPutAndGet();
    }

    private static void testPutAndGet() {
        PutThread putThread = new PutThread();
        GetThread getThread = new GetThread();
        GetThread getThreadTwo = new GetThread();
        GetThread getThreadThree = new GetThread();
        GetThread getThreadFour = new GetThread();
        GetThread getThreadFive = new GetThread();

        putThread.setNext(getThread);
        getThread.setNext(putThread);
        getThreadTwo.setNext(putThread);
        getThreadThree.setNext(putThread);
        getThreadFour.setNext(putThread);
        getThreadFive.setNext(putThread);
        getThread.start();
//        getThreadTwo.start();
//        getThreadThree.start();
//        getThreadFour.start();
//        getThreadFive.start();
        try {
            TimeUnit.SECONDS.sleep(1);
            putThread.start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main begin sleep......");
        try {
            TimeUnit.SECONDS.sleep(100000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setA(long a) {
        OrderInitThreadSub.a = a;
    }

    public static long getA() {
        return a;
    }

    public synchronized static long getAbsoluteA() {
        return absoluteA;
    }

    public synchronized static long setAbsoluteA(long absoluteA) {
        OrderInitThreadSub.absoluteA = absoluteA;
        return absoluteA;
    }
}

class PutThread extends Thread {

    private GetThread next;

    PutThread() {

    }

    public void setNext(GetThread next) {
        this.next = next;
    }

    @Override
    public void run() {
        while (true) {
            for (long i = 1; i < Long.MAX_VALUE; i++) {
                long a = OrderInitThreadSub.setAbsoluteA(OrderInitThreadSub.getAbsoluteA() + 100000);
                OrderInitThreadSub.setA(a);
                OrderInitThreadSub.counter.addAndGet(1);
                while (OrderInitThreadSub.followCounter.get() < OrderInitThreadSub.counter.get()) {

                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OrderInitThreadSub.counter.set(0);
        }
    }
}

class GetThread extends Thread {
    private PutThread next;

    public PutThread getNext() {
        return next;
    }

    public void setNext(PutThread next) {
        this.next = next;
    }

    @Override
    public void run() {
        while (true) {
            //put value
            for (long i = 1; i < Long.MAX_VALUE; i++) {
                while (i != OrderInitThreadSub.counter.get()) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                long putAbsoluteValue = OrderInitThreadSub.getAbsoluteA();
                long putValue = OrderInitThreadSub.getA();
                System.out.println("No: " + i + this + "old putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                if (putAbsoluteValue != putValue) {
                    System.out.println(this + "new putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                    System.exit(1);
                }
                OrderInitThreadSub.followCounter.addAndGet(1);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OrderInitThreadSub.followCounter.set(0);
        }
    }
}