package com.apeny.thread.init.subref;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 1. 先通知，而没有线程在之前等待，后来等待线程不会直接获得通知，并直接往下执行
 * Created by monis on 2018/9/3.
 */
public class OrderInitThreadSubRef {
    private static ConcurrentVO a = new ConcurrentVO();

    private static ConcurrentVO absoluteA = a;

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

    public static ConcurrentVO getA() {
        return a;
    }

    public synchronized static void setA(ConcurrentVO a) {
        OrderInitThreadSubRef.a = a;
    }

    public synchronized static ConcurrentVO getAbsoluteA() {
        return OrderInitThreadSubRef.absoluteA;
    }

    public synchronized static ConcurrentVO setAbsoluteA(ConcurrentVO absoluteA) {
        OrderInitThreadSubRef.absoluteA = absoluteA;
        return absoluteA;
    }

    public static AtomicLong getCounter() {
        return counter;
    }

    public static AtomicLong getFollowCounter() {
        return followCounter;
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
                ConcurrentVO putAbsoluteValue = OrderInitThreadSubRef.getAbsoluteA();
                ConcurrentVO putValue = OrderInitThreadSubRef.getA();
                System.out.println("No: " + i + this + "put thread old putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                if (putAbsoluteValue != putValue || putAbsoluteValue.getI() != putValue.getI() || putValue.getI() != putValue.getI()) {
                    System.out.println(this + "put thread new putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                    System.exit(1);
                }
                if (i % 10 == 0) {
                    for (int j = 0; j < 1000; j++) {
                        ConcurrentVO a = OrderInitThreadSubRef.setAbsoluteA(new ConcurrentVO(new Random().nextInt(1000000)));
                        a.setI(a.getI() + 10000);
                        OrderInitThreadSubRef.setA(a);
                    }
                } else {
                    OrderInitThreadSubRef.getA().setI(OrderInitThreadSubRef.getA().getI() * 23);
                }
                OrderInitThreadSubRef.counter.addAndGet(1);
                while (OrderInitThreadSubRef.followCounter.get() < OrderInitThreadSubRef.counter.get()) {

                }
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OrderInitThreadSubRef.counter.set(0);
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
                while (i != OrderInitThreadSubRef.counter.get()) {
                    try {
                        TimeUnit.NANOSECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ConcurrentVO putAbsoluteValue = OrderInitThreadSubRef.getAbsoluteA();
                ConcurrentVO putValue = OrderInitThreadSubRef.getA();
                System.out.println("No: " + i + this + "old putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                if (putAbsoluteValue != putValue || putAbsoluteValue.getI() != putValue.getI() || putValue.getI() != putValue.getI()) {
                    System.out.println(this + "new putValue = " + putValue + ", absoluteValue = " + putAbsoluteValue);
                    System.exit(1);
                }
                if (i % 3 == 0) {
                    for (int j = 0; j < 10; j++) {
                        ConcurrentVO a = OrderInitThreadSubRef.setAbsoluteA(new ConcurrentVO(new Random().nextInt(1000000)));
                        a.setI(a.getI() + 10000);
                        OrderInitThreadSubRef.setA(a);
                    }
                } else {
                    OrderInitThreadSubRef.getA().setI(OrderInitThreadSubRef.getA().getI() * 19);
                }
                OrderInitThreadSubRef.followCounter.addAndGet(1);
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            OrderInitThreadSubRef.followCounter.set(0);
        }
    }
}