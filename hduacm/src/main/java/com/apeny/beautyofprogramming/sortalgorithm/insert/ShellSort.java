package com.apeny.beautyofprogramming.sortalgorithm.insert;

import java.util.Arrays;
import java.util.Objects;

/**
 * 原地排序，不申请额外空间
 * 直接插入排序高级
 * 希尔排序(缩小增量排序)
 * 不稳定排序
 * 时间复杂度：O(n^1.3-2)
 * 空间复杂度：O(1)
 * Created by monis on 2019/5/2.
 */
public class ShellSort {
    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr3[] = {1, 3, 49, 4, 2, 32, 88, 99, 203, 24, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("shell sort nonrecursiveShell before sort: " + Arrays.toString(arr3)
                + "\nafter sort: " + Arrays.toString(nonrecursiveShell(arr3)));
    }

    /**
     * 非递归希尔排序
     * @param arr
     * @return
     */
    private static int[] nonrecursiveShell(int[] arr) {
        Objects.requireNonNull(arr);
        for (int d = arr.length >> 1; d >= 1; d >>= 1) {
            nonrecursiveDirect(arr, d);
        }
        return arr;
    }

    /**
     * 直接插入排序
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursiveDirect(int[] arr, int d) {
        Objects.requireNonNull(arr);
        int temp;
        for (int i = 0; i < d; i++) {
            for (int groupIndex = i + d; groupIndex < arr.length; groupIndex += d) {
                temp = arr[groupIndex];
                int j = groupIndex - d;
                for (; j >= 0; j -= d) {
                    if (arr[j] > temp) {
                        arr[j + d] = arr[j];
                    } else {
                        break;
                    }
                }
                arr[j + d] = temp;
            }
        }
        return arr;
    }
}
