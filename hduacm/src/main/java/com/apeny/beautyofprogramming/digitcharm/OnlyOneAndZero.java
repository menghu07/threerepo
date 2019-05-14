package com.apeny.beautyofprogramming.digitcharm;

/**
 * 给定整整数N，求最小的正整数M(M>1)，使得N*M的十进制表示里只含有1和0
 * Created by monis on 2019/5/11.
 */
public class OnlyOneAndZero {

    /**
     * 不存在倍数
     */
    private static final int NONE = 0;

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
//        System.out.println(" 7 M=" + findM(90));
        for (int i = 99; i < 100; i++) {
            findM(i);
        }
    }

    /**
     * 查询N的最小M倍数N*M(M>1)，使N*M十进制表示只有1和0
     *
     * @param n
     */
    private static long findM(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("参数错误");
        }
        if (n == 1) {
            return 10;
        }
        //x%n余数
        long[] moduli = new long[n];
        moduli[1] = 1;
        int noOperation = 0;
        for (long i = 10; i < Long.MAX_VALUE; i *= 10) {
            if (i <= n) {
                continue;
            }
            int j = (int) (i % n);
            boolean changed = false;
            if (moduli[j] == NONE) {
                moduli[j] = i;
                changed = true;
            }
            for (int k = 1; k < n; k++) {
                if (moduli[k] != NONE && moduli[k] < i && moduli[(k + j) % n] == NONE) {
                    moduli[(k + j) % n] = moduli[k] + i;
                    changed = true;
                }
            }
            if (changed) {
                noOperation = 0;
            } else {
                noOperation++;
            }
            if (noOperation == n || moduli[0] != NONE) {
                break;
            }
            if (i > Long.MAX_VALUE / 10) {
                break;
            }
        }
        if (moduli[0] != NONE) {
            System.out.println(n + " value = " + moduli[0]);
            return moduli[0] / n;
        }
        throw new IllegalArgumentException("illeagal value: " + n);
    }
}
