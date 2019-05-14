package com.apeny.beautyofprogramming.digitcharm;

/**
 * 二进制中1的个数
 * Created by monis on 2019/3/17.
 */
public class CountDigs {
    public static void main(String[] args) {
        int xhi = Integer.MAX_VALUE;
        System.out.println(" xhi = " + (xhi >>> -1));
        testDiv2();
    }

    private static void testDiv2() {
        div2(-1);
        div2(-293);
        div2(192);
        rotate1(-1);
        rotate1(-293);
        rotate1(192);
        rotate2(-1);
        rotate2(-293);
        rotate2(192);
        sub1(-1);
        sub1(-293);
        sub1(192);
    }

    /**
     * 错误的写法对于负数
     *
     * @param x
     */
    private static void div2(int x) {
        int temp = x;
        int num = 0;
        while (x != 0) {
            if (x % 2 != 0) {
                num++;
            }
            x /= 2;
        }
        System.out.println("x = " + temp + "binary bits = " + +Integer.bitCount(temp) + ", count = " + num);
    }

    /**
     * 这种写法正确
     *
     * @param x
     */
    private static void rotate1(int x) {
        int temp = x;
        int num = 0;
        while (x != 0) {
            if (x % 2 != 0) {
                num++;
            }
            //逻辑右移补0
            x >>>= 1;
        }
        System.out.println("rotate x = " + temp + "binary bits = " + +Integer.bitCount(temp) + ", count = " + num);
    }

    /**
     * 这种写法正确
     *
     * @param x
     */
    private static void rotate2(int x) {
        int temp = x;
        int num = 0;
        while (x != 0) {
            if ((x & 0x1) == 1) {
                num++;
            }
            //逻辑右移补0
            x >>>= 1;
        }
        System.out.println("rotate2 x = " + temp + "binary bits = " + +Integer.bitCount(temp) + ", count = " + num);
    }

    /**
     * 这种写法正确
     *
     * @param x
     */
    private static void sub1(int x) {
        int temp = x;
        int num = 0;
        while (x != 0) {
            num++;
            //消除最后一位1
            x &= (x -1);
        }
        System.out.println("sub x = " + temp + "binary bits = " + +Integer.bitCount(temp) + ", count = " + num);
    }
}
