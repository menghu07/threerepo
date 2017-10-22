package com.apeny.jvmpractice.bytecode;

/**
 * Created by apeny on 2017/10/22.
 */
public class TwoObject {
    TwoObject(int i) {
        this.i = i;
    }

    private TwoObject(int i, int j) {
        this.i = i;
    }

    private long i;
    long jlong;

    public static void main(String[] args) {
        TwoObject twoObject = new TwoObject(23);
        TwoObject ijTwoObject = new TwoObject(23, 35);
        Object object = new Object();
    }

    public int fun1(double dbl) throws Exception {
        System.out.print("you");
        return 1;
    }
}


class OutClass {
    private long longValue;
}