package com.apeny.beautyofprogramming.sortalgorithm.select;

import java.util.Arrays;
import java.util.Objects;

/**
 * 原地排序，不申请额外空间
 * 选择排序(先排序前面元素位置)
 * 不稳定排序
 * 时间复杂度：O(n^2)
 * 空间复杂度：O(1)
 * Created by monis on 2019/5/2.
 */
public class SelectSort {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {1, 3, 49, 32, 99, 203, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("quick sort nonrecursive before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr2)));
    }

    /**
     * 非递归选择排序
     * @param arr
     * @return
     */
    private static int[] nonrecursive(int[] arr) {
        Objects.requireNonNull(arr);
        //最小元素的下标
        int minValueIndex;
        //将排序的下标
        int toSortIndex = 0;
        while (toSortIndex < arr.length - 1) {
            minValueIndex = toSortIndex;
            for (int i = toSortIndex + 1; i < arr.length; i++) {
                if (arr[i] < arr[minValueIndex]) {
                    minValueIndex = i;
                }
            }
            //交换最小元素和将排序下标对应的元素
            if (toSortIndex < minValueIndex) {
                int temp = arr[toSortIndex];
                arr[toSortIndex] = arr[minValueIndex];
                arr[minValueIndex] = temp;
            }
            toSortIndex++;
        }
        return arr;
    }
}
