package com.apeny.beautyofprogramming.sortalgorithm.exchange;

import java.util.Arrays;
import java.util.Objects;

/**
 * 冒泡排序
 * 稳定排序
 * 时间复杂度：O(n)~O(n^2)
 * 空间复杂度：O(1)
 * Created by monis on 2019/5/4.
 */
public class BubbleSort {
    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {1, 3, 49, 32, 99, 203, 93, 374, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("bubble sort nonrecursive before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr2)));
    }

    /**
     * 冒泡排序非递归
     * @param arr
     * @return
     */
    private static int[] nonrecursive(int[] arr) {
        Objects.requireNonNull(arr);
        boolean isExchanged;
        int temp;
        for (int i = arr.length - 1; i >= 1; i--) {
            isExchanged = false;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isExchanged = true;
                }
            }
            if (!isExchanged) {
                break;
            }
        }
        return arr;
    }
}
