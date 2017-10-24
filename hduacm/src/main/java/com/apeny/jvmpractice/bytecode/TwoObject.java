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

    private int i;
    long jlong;

    public static void main(String[] args) throws Exception {
        TwoObject twoObject = new TwoObject(23);
        TwoObject ijTwoObject = new TwoObject(23, 35);
        ijTwoObject.fun1(10, Float.NaN);
        Object object = new Object();
    }

    public int fun1(double dbl, float fi) throws Exception {
        System.out.print("you");
        short f1 = 9;
        byte f2 = 10;
        System.out.println(f1 > f2);
        if (f1 < f2) {
            int f4 = 102;
            f4 = 20;
        } else {
            f2 = 127;
        }

        float f3 = 20;
        float f4 = Float.NaN;
        //做反方向判断fcmpg
        System.out.println(f3 != f4);
        int[] arr = new int[100];
        return 1;
    }

    public void setI(int i) throws IllegalStateException {
        try {
            this.i = i;
            while (i < 10) {
                i++;
                int local = 2;
                local = local + 2;
                //goto指令会往回跳转goto 5
            }
            int i4 = 4;
        } catch (IllegalStateException e) {
            System.out.println(e.toString());
        }
    }
}


class OutClass {
    private long longValue;
}