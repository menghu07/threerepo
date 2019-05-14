package com.apeny.beautyofprogramming.digitcharm;

/**
 * 统计1的个数
 * Created by monis on 2019/3/17.
 */
public class CountOneDigs {
    public static void main(String[] args) {
        int xhi = Integer.MAX_VALUE;
        System.out.println(" xhi = " + (xhi >>> -1));
        System.out.println(onePass(-121123130));
        testOne();
    }

    private static void testOne() {
        int num1 = 8979;
        System.out.println("num = " + num1 + ", count = " + countOneUseOnePass(num1));
    }

    /**
     * 计算给定的整数中1的个数
     * @param n
     * @return
     */
    private static int onePass(int n) {
        int num = 0;
        while (n != 0) {
            if (n % 10 == 1 || n % 10 == -1) {
                num++;
            }
            n /= 10;
        }
        return num;
    }

    /**
     * 计算1的个数，用OnePass
     * @param n1
     * @return
     */
    private static int countOneUseOnePass(int n1) {
        int totalNum = 0;
        for (int i = 1; i <= n1; i++) {
            totalNum += onePass(i);
        }
        return totalNum;
    }
}