package com.apeny.thread.init;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 1. 先通知，而没有线程在之前等待，后来等待线程不会直接获得通知，并直接往下执行
 * Created by monis on 2018/9/3.
 */
public class OrderInitThread {
    private static int a = 0;

    private static int absoluteA = 0;

    public static final Object mutex = new Object();

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

    public static void setA(int a) {
        OrderInitThread.a = a;
    }

    public static int getA() {
        return a;
    }

    public synchronized static int getAbsoluteA() {
        return absoluteA;
    }

    public synchronized static int setAbsoluteA(int absoluteA) {
        OrderInitThread.absoluteA = absoluteA;
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
            //put value
            int a = OrderInitThread.setAbsoluteA(new Random().nextInt(10000000));
            OrderInitThread.setA(a);
            System.out.println ("put thread>>>" + "put value " + a);
            synchronized (OrderInitThread.mutex) {
                OrderInitThread.mutex.notify();
            }
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
            synchronized (OrderInitThread.mutex) {
                try {
//                    System.out.println("get thread has wait");
                    OrderInitThread.mutex.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            //get value
            int putAbsoluteValue = OrderInitThread.getAbsoluteA();
            int putValue = OrderInitThread.getA();
            System.out.println(this + "old putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
            if (putAbsoluteValue != putValue) {
                System.out.println(this + "new putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                System.exit(1);
            }
        }
    }
}