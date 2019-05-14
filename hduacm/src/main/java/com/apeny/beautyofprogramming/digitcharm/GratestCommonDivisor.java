package com.apeny.beautyofprogramming.digitcharm;

/**
 * 最大公约数
 * Created by monis on 2019/5/4.
 */
public class GratestCommonDivisor {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        System.out.println("最大公约数toss(12, 8)=" + toss(12, 8));
        System.out.println("最大公约数toss(8, 12)=" + toss(8, 12));
        System.out.println("最大公约数subtract(8, 12)=" + subtract(8, 12));
        System.out.println("最大公约数subtract(7, 5)=" + subtract(7, 5));
    }

    /**
     * 辗转相除法
     *
     * @param x
     * @param y
     * @return
     */
    private static int toss(int x, int y) {
        if (x <= 0 || y < 0) {
            throw new IllegalArgumentException("参数不能小于0");
        }
        if (y == 0) {
            return x;
        }
        return toss(y, x % y);
    }

    /**
     * 减法求最大公约数
     * @param x
     * @param y
     * @return
     */
    private static int subtract(int x, int y) {
        if (x <= 0 || y < 0) {
            throw new IllegalArgumentException("参数不能小于0");
        }
        if (y == 0) {
            return x;
        }
        if (x > y) {
            return subtract(x - y, y);
        } else {
            return subtract(x, y - x);
        }
    }
}
