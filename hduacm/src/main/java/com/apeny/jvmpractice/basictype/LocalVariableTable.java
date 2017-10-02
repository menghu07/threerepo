package com.apeny.jvmpractice.basictype;

/**
 * Created by ahu on 2017年09月25日.
 */
public class LocalVariableTable {

    private static int count = 0;

    public static void main(String[] args) {
        try {
//            recursion1(1, 2, 3);
            recursion2();
        } catch (Throwable ex) {
            System.out.println("count depth: " + count);
            ex.printStackTrace();
        }
    }

    private static void recursion1(long a, long b, long c) {
        byte mm = 12;
        long e = 1,
                f = 2,
                g = 4,
                h = 19,
                i = 99,
                j = 19;

        long k = 12,
                l = 23,
                m = 29,
                n = 192;
        count++;
        recursion1(a, b, c);
    }

    private static void recursion2() {
        count++;
        recursion2();
    }
}