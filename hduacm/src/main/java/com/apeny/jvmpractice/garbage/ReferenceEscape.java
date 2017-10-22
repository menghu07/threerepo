package com.apeny.jvmpractice.garbage;

import java.util.concurrent.TimeUnit;

/**
 * finalize()函数引用外泄
 * finalize()只会被调用一次
 * Created by ahu on 2017年10月04日.
 */
public class ReferenceEscape {
    private static ReferenceEscape objReference;

    public static void main(String[] args) {
        try {
            releaseObject();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 第一次没有被回收，第二次被回收了，
     * finalize只会执行一次！
     * @throws InterruptedException
     */
    public static void releaseObject() throws InterruptedException {
        objReference = new ReferenceEscape();
        System.out.println("hashCode:" + Integer.toHexString(System.identityHashCode(objReference)) + " " + objReference);
        objReference = null;
        System.gc();
        TimeUnit.NANOSECONDS.sleep(10);
        if (objReference == null) {
            System.out.println("objReference 是null");
        } else {
            System.out.println("objReference 可用");
        }
        System.out.println("第二次gc");
        objReference = null;
        System.gc();
        TimeUnit.NANOSECONDS.sleep(10);
        if (objReference == null) {
            System.out.println("second objReference 是null");
        } else {
            System.out.println("second objReference 可用");
        }
    }

    @Override
    public void finalize() {
        System.out.println("finalize is called ");
        objReference = this;
    }
}
