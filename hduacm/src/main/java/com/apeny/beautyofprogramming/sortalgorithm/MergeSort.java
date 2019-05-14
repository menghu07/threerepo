package com.apeny.beautyofprogramming.sortalgorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 归并排序
 * 稳定排序
 * 时间复杂度：O(nlg(n)
 * 空间复杂度：O(n)
 * Created by monis on 2019/5/3.
 */
public class MergeSort {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr4[] = {0, 1, 3, 49, 4, 2, 32, 88, 99, 7, 203, 4, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("merge sort recursive before sort: " + Arrays.toString(arr4));
        recursive(arr4, 0, arr4.length - 1);
        System.out.println("\nafter sort: " + Arrays.toString(arr4));
        int arr5[] = {0, 1, 3, 49, 4, 2, 32, 81, 99, 73, 203, 4, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("merge sort recursive no return before sort: " + Arrays.toString(arr5)
                + "\nafter sort: " + Arrays.toString(recursiveNoReturn(arr5, 0, arr5.length - 1)));
        int arr6[] = {0, 1, 3, 49, 4, 2, 322, 81, 99, 73, 203, 4, 182, 2, 9, 10, 8, 11, 45, 22, 534, 35};
        System.out.println("merge sort nonrecursive no return before sort: " + Arrays.toString(arr6)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr6)));
    }

    /**
     * 归并算法非递归实现
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursive(int[] arr) {
        Objects.requireNonNull(arr);
        //按步长1、2、4、8、...合并
        for (int step = 1; step < arr.length; step *= 2) {
            for (int i = 0; i + step < arr.length; i += 2 * step) {
                int end = i + 2 * step - 1;
                merge(arr, i, i + step, end < arr.length ? end : arr.length - 1);
            }
        }
        return arr;
    }

    /**
     * 归并排序递归算法merge有返还值
     *
     * @param arr
     * @param x1
     * @param x2
     * @return
     */
    private static int[] recursive(int[] arr, int x1, int x2) {
        Objects.requireNonNull(arr);
        if (x1 < x2) {
            int mid = (x1 + x2) / 2;
            int[] splitedOne = recursive(arr, x1, mid);
            int[] splitedTwo = recursive(arr, mid + 1, x2);
            int[] result = merge(splitedOne, splitedTwo);
            System.arraycopy(result, 0, arr, x1, x2 - x1 + 1);
            return result;
        } else {
            return new int[]{arr[x1]};
        }
    }

    /**
     * 归并排序递归算法merge有返还值
     *
     * @param arr
     * @param x1
     * @param x2
     * @return
     */
    private static int[] recursiveNoReturn(int[] arr, int x1, int x2) {
        Objects.requireNonNull(arr);
        if (x1 < x2) {
            int mid = (x1 + x2) / 2;
            recursiveNoReturn(arr, x1, mid);
            recursiveNoReturn(arr, mid + 1, x2);
            merge(arr, x1, mid + 1, x2);
        }
        return arr;
    }

    /**
     * 合并两个有序数组
     *
     * @param arr1
     * @param arr2
     * @return
     */
    private static int[] merge(int[] arr1, int[] arr2) {
        Objects.requireNonNull(arr1);
        Objects.requireNonNull(arr2);
        int[] result = new int[arr1.length + arr2.length];
        int index = 0;
        int i = 0;
        int j = 0;
        for (; i < arr1.length && j < arr2.length; ) {
            if (arr1[i] > arr2[j]) {
                result[index++] = arr2[j++];
            } else {
                result[index++] = arr1[i++];
            }
        }
        while (i < arr1.length) {
            result[index++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[index++] = arr2[j++];
        }
        return result;
    }

    /**
     * 无返回值merge
     *
     * @param arr
     * @param start1
     * @param start2
     * @param end2
     */
    private static void merge(int[] arr, int start1, int start2, int end2) {
        Objects.requireNonNull(arr);
        assert start1 >= 0 && start1 < start2
                && start2 <= end2 && end2 < arr.length;
        int[] result = new int[end2 - start1 + 1];
        int index = 0;
        int i = start1;
        int j = start2;
        for (; i < start2 && j <= end2; ) {
            if (arr[i] > arr[j]) {
                result[index++] = arr[j++];
            } else {
                result[index++] = arr[i++];
            }
        }
        while (i < start2) {
            result[index++] = arr[i++];
        }
        while (j <= end2) {
            result[index++] = arr[j++];
        }
        System.arraycopy(result, 0, arr, start1, end2 - start1 + 1);
    }
}
