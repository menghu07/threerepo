package com.apeny.finalize;

import java.util.concurrent.TimeUnit;

/**
 * 线程实例的finalize方法在垃圾回收时，只要线程没有执行结束，就不会被回收，即不会调用finalize方法
 * Created by apeny on 2017年09月18日.
 */
public class FinalizeThread {
    public static void main(String[] args) {
        FinalizeNormalClass nums = new FinalizeNormalClass();
        FinalizeThreadClass threadClass = new FinalizeThreadClass();
        threadClass.setDaemon(false);
        threadClass.start();
        /*try {
            nums = null;
            threadClass = null;
            System.gc();
            TimeUnit.SECONDS.sleep(1);
            System.gc();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        while (true) {
            nums = null;
            threadClass = null;
            System.gc();
        }
    }
}

class FinalizeNormalClass {

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize.... " + this);
    }
}

class FinalizeThreadClass extends Thread {

    @Override
    public void run() {
        int i = 0;
        while (i++ < 10) {
            System.out.println("finalizeThreadClass");
            try {
                TimeUnit.SECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize thread.... " + this);
    }
}
