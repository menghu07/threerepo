package com.apeny.jvmpractice.methodregion;

import net.sf.cglib.util.ParallelSorter;

import java.util.Arrays;

/**
 * 排完一个就恢复原来的顺序！！！
 * Created by ahu on 2017年10月02日.
 */
public class CglibParallelSorter {
    public static void main(String[] args) {
        Object[] ar = new Object[8];
        int[] arr = {1, 23, 34, 3, 2, 22, 31, 35};
        long[] longArr = {23L, 35L, 31L, 93L, 38L, 8L, 0L, -1L};
        Object[] multiArrs = {ar, arr, longArr};
        ParallelSorter sorter = ParallelSorter.create(multiArrs);
        sorter.quickSort(1);
        System.out.println(Arrays.toString((int[]) multiArrs[1]));
        sorter.quickSort(2);
        System.out.println(Arrays.toString((int[]) multiArrs[1]) + "\n" + Arrays.toString((long[]) multiArrs[2]));
    }
}
