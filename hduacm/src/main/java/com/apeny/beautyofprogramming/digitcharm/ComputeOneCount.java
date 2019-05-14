package com.apeny.beautyofprogramming.digitcharm;

/**
 * 计算1,2,3, ..., N中1的总数
 * Created by monis on 2019/5/1.
 */
public class ComputeOneCount {
    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        computeOneCount(12);
        computeOneCount(20);
        computeOneCount(99);
    }

    private static void computeOneCount(int n) {
        int factor = 1;
        int before;
        int after;
        int current;
        int total = 0;
        while (n / factor != 0) {
            before = n / (factor * 10);
            after = n % factor;
            current = n / factor % 10;
            if (current == 0) {
                total += before * factor;
            } else if (current == 1) {
                total += before * factor + (after + 1);
            } else {
                total += (before + 1) * factor;
            }
            factor *= 10;
        }
        System.out.println("n: " + n + ", total 1 nums: " + total);
    }
}
