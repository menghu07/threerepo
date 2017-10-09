package com.apeny.jvmpractice.garbage;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 不太好测试SoftReference被回收
 * Created by ahu on 2017年10月04日.
 */
public class SoftReferenceClass {
    private int id;
    private String userName;

    public SoftReferenceClass() {
    }

    public SoftReferenceClass(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public static void main(String[] args) throws InterruptedException {
        releaseSoft();
    }

    /**
     * 只有软引用时，在资源紧张时，软引用引用的对象可以被释放
     */
    private static void releaseSoft() throws InterruptedException {
        SoftReferenceClass softReference = new SoftReferenceClass(100, "yyyyy");
        SoftReferenceClass softReference2 = new SoftReferenceClass(100, "yyyyy");
        java.lang.ref.SoftReference<SoftReferenceClass> softReferenceSoftReference = new java.lang.ref.SoftReference<SoftReferenceClass>(softReference);
        java.lang.ref.SoftReference<SoftReferenceClass> softReferenceSoftReference2 = new java.lang.ref.SoftReference<SoftReferenceClass>(softReference2);
        java.lang.ref.SoftReference<SoftReferenceClass> softReferenceSoftReference3 = new java.lang.ref.SoftReference<SoftReferenceClass>(new SoftReferenceClass(12, "eeee"));
        java.lang.ref.SoftReference<SoftReferenceClass> softReferenceSoftReference4 = new java.lang.ref.SoftReference<SoftReferenceClass>(new SoftReferenceClass(123, "ffff"));
        softReference2 = null;
        softReference = null;
        System.gc();
        List<SoftReference<SoftReferenceClass>> list = new ArrayList<>();
        for (int i = 0; i < 300_000; i++) {
            list.add(new SoftReference<SoftReferenceClass>(new SoftReferenceClass(123, "fff")));
            for (SoftReference<SoftReferenceClass> e : list) {
                if (e.get() == null) {
                    System.out.println("gc soft reference effective: " + e.get());
                    throw new RuntimeException("oom");
                }
            }
        }
        System.out.println("第一次GC之后 softReference: " + softReferenceSoftReference.get() + " " + softReferenceSoftReference2.get());
        for (SoftReference<SoftReferenceClass> e : list) {
            if (e.get() == null) {
                System.out.println("gc soft reference effective: ############################" + e.get());
            }
        }
        byte[] bytes = new byte[2560 * 1024];
        System.gc();
        TimeUnit.NANOSECONDS.sleep(10);
        for (SoftReference<SoftReferenceClass> e : list) {
            if (e.get() == null) {
                System.out.println("gc soft reference effective: ############################" + e.get());
            }
        }
        System.out.println("第二次隐式GC之后：" + softReferenceSoftReference.get() + " " + softReferenceSoftReference2.get());
        System.out.println("第二次隐式GC之后：" + softReferenceSoftReference3.get() + " " + softReferenceSoftReference4.get());
    }
}
