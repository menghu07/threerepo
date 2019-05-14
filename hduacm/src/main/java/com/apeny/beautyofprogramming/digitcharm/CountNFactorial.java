package com.apeny.beautyofprogramming.digitcharm;

/**
 * N!末尾0个数
 * Created by monis on 2019/3/17.
 */
public class CountNFactorial {
    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        System.out.println("n = 125, num = " + countFive(125));
        System.out.println("n = 250, num = " + countFive(250));
        System.out.println("n = 500, num = " + countFive(500));
        System.out.println("fast one n = 125, num = " + countFiveFast(125));
        System.out.println("fast one n = 250, num = " + countFiveFast(250));
        System.out.println("fast one n = 500, num = " + countFiveFast(500));
        System.out.println("fast two n = 125, num = " + countFiveFastTwo(125));
        System.out.println("fast two n = 250, num = " + countFiveFastTwo(250));
        System.out.println("fast two n = 500, num = " + countFiveFastTwo(500));
        System.out.println("count 2 n = 3, num = " + countTwoFast(3));
        System.out.println("count 2 n = 10, num = " + countTwoFast(10));
    }

    /**
     * 统计5的个数
     * @param n
     * @return
     */
    private static int countFive(int n) {
        int num = 0;
        for (int i = 1; i <= n; i++) {
            int j = i;
            while (j % 5 == 0) {
                num++;
                j /= 5;
            }
        }
        return num;
    }

    /**
     * 5因子个数 = 5 + 5^2 + 5^3 + 5^4 + ...个数
     * @param n
     * @return
     */
    private static int countFiveFast(int n) {
        int num = 0;
        while (n > 0) {
            num += n / 5;
            n /= 5;
        }
        return num;
    }

    /**
     * 5因子个数 = 5 + 5^2 + 5^3 + 5^4 + ...个数
     * @param n
     * @return
     */
    private static int countFiveFastTwo(int n) {
        int num = 0;
        int init = 5;
        while (init <= n) {
            num += n / init;
            init *= 5;
        }
        return num;
    }

    /**
     * 计算最低位1的位置，等价于求质因数2的个数
     * @param n
     * @return
     */
    private static int countTwoFast(int n) {
        int num = 0;
        while (n > 0) {
            num += (n >>= 1);
        }
        return num + 1;
    }
}
