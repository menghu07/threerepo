package com.apeny.thread.init;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2017年09月22日.
 */
public class NonsafeObject {

    public static TwoValues twoValues;

    public static void init() {
        if (twoValues == null) {
            twoValues = new TwoValues(new Random(17).nextInt(), new Random().nextInt());
        }
    }

    public static void getTwoValues() {
        if (twoValues != null) {
            System.out.println("currentThread" + Thread.currentThread() + "; " + twoValues);
        }
    }
}

class TwoValues {
    private int i;
    private int j;
    private Object object;

    public TwoValues(int i, int j) {
        System.out.println(Thread.currentThread() + ", " + this);
        this.i = i;
        object = new TwoThreadGet();
        try {
            System.out.println(Thread.currentThread() + ", " + this);
            TimeUnit.SECONDS.sleep(5);
            ((TwoThreadGet) object).setI(1089);
            TimeUnit.MINUTES.sleep(1);
            System.out.println(Thread.currentThread() + ", " + this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return "TwoValues{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }
}
