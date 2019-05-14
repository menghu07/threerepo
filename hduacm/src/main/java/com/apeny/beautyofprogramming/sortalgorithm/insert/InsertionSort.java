package com.apeny.beautyofprogramming.sortalgorithm.insert;

import java.util.Arrays;
import java.util.Objects;

/**
 * 原地排序，不申请额外空间
 * 插入排序(基于位置的排序)
 * 稳定排序
 * 时间复杂度：O(n)~O(n^2)
 * 空间复杂度：O(1)
 * Created by monis on 2019/5/2.
 */
public class InsertionSort {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {1, 3, 49, 32, 88, 99, 203, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("direct insert sort nonrecursiveDirect before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursiveDirect(arr2)));
        int arr3[] = {1, 3, 49, 4, 2, 32, 88, 99, 203, 4, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("binary insert sort nonrecursiveBinary before sort: " + Arrays.toString(arr3)
                + "\nafter sort: " + Arrays.toString(nonrecursiveBinary(arr3)));
    }

    /**
     * 直接插入排序
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursiveDirect(int[] arr) {
        Objects.requireNonNull(arr);
        int temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            int j = i - 1;
            for (; j >= 0; j--) {
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
        }
        return arr;
    }

    /**
     * 二分插入排序/折半插入排序
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursiveBinary(int[] arr) {
        Objects.requireNonNull(arr);
        int temp;
        for (int i = 1; i < arr.length; i++) {
            temp = arr[i];
            int begin = 0;
            int end = i - 1;
            for (int j = (begin + end) >> 1; begin < end; j = (begin + end) >> 1) {
                if (arr[j] > temp) {
                    end = j - 1;
                } else {
                    begin = j + 1;
                }
            }
            if (arr[begin] <= temp) {
                begin++;
            }
            System.arraycopy(arr, begin, arr, begin + 1, i - begin);
            arr[begin] = temp;
        }
        return arr;
    }
}
