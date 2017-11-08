package com.apeny.hduacm;

/**
 * 找出只出现一次的数字，其他数字都出现3次
 * Created by apeny on 2017/10/26.
 */
public class OneNumber {
    public static void main(String[] args) {
        int[] nums = {3, 1, 3, 2, 3, 2, 2};
        singleNumber(nums, 7);
    }

    private static void singleNumber(int a[], int n) {
        int ones = 0;
        int twos = 0;
        int xthrees = 0;
        for (int i = 0; i < n; i++) {
            twos |= (ones & a[i]);
            ones ^= a[i];
            xthrees = ~(ones & twos);
            ones &= xthrees;
            twos &= xthrees;
        }
        System.out.println("one number " + ones);
    }
}
