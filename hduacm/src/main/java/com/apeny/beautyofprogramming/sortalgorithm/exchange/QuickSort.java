package com.apeny.beautyofprogramming.sortalgorithm.exchange;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 原地排序：希尔排序、冒泡排序、插入排序、选择排序、堆排序、快速排序
 * 递归调用需要申请额外空间
 * 快速排序
 * 不稳定排序
 * 时间复杂度：O(nlg(n))~O(n^2)
 * 空间复杂的：lg(n)递归调用的栈深度
 * Created by monis on 2019/5/2.
 */
public class QuickSort {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {1, 3, 49, 32, 99, 203, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr2)));
        System.out.println("sort two before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursiveTwo(arr2)));
        System.out.println("sort two2 before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursiveTwo2(arr2)));
        System.out.println("recursive sort two2 before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(recursiveTwo2(arr2, 0, arr2.length - 1)));
    }

    /**
     * 第一种非递归
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursive(int[] arr) {
        Objects.requireNonNull(arr);
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        Queue<Integer[]> toSortIndexes = new LinkedList<>();
        toSortIndexes.add(new Integer[]{0, arr.length - 1});
        while (!toSortIndexes.isEmpty()) {
            Integer[] fromAndTo = toSortIndexes.remove();
            int i = fromAndTo[0];
            int j = fromAndTo[1];
            boolean forward = false;
            while (i < j) {
                boolean exchanged = false;
                if (arr1[i] > arr1[j]) {
                    int temp = arr1[j];
                    arr1[j] = arr1[i];
                    arr1[i] = temp;
                    exchanged = true;
                }
                if (exchanged) {
                    if (forward) {
                        j--;
                        forward = false;
                    } else {
                        i++;
                        forward = true;
                    }
                } else {
                    if (forward) {
                        i++;
                    } else {
                        j--;
                    }
                }
            }
            int maxLeft = i - 1;
            int minRight = i + 1;
            if (maxLeft > fromAndTo[0]) {
                toSortIndexes.add(new Integer[]{fromAndTo[0], maxLeft});
            }
            if (minRight < fromAndTo[1]) {
                toSortIndexes.add(new Integer[]{minRight, fromAndTo[1]});
            }
        }
        return arr1;
    }

    /**
     * 第二种非递归
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursiveTwo(int[] arr) {
        Objects.requireNonNull(arr);
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        Queue<Integer[]> toSortIndexes = new LinkedList<>();
        toSortIndexes.add(new Integer[]{0, arr1.length - 1});
        while (!toSortIndexes.isEmpty()) {
            Integer[] fromAndTo = toSortIndexes.remove();
            int i = fromAndTo[0];
            int j = fromAndTo[1];
            while (i < j) {
                //backward 从右侧向左侧遍历
                while (i < j && arr1[i] <= arr1[j]) {
                    j--;
                }
                if (j > i) {
                    //交换当前元素和右侧元素
                    int temp = arr1[i];
                    arr1[i] = arr1[j];
                    arr1[j] = temp;
                    i++;
                }
                //forward 从左侧向右侧遍历
                while (i < j && arr1[i] <= arr1[j]) {
                    i++;
                }
                if (i < j) {
                    //交换当前元素和左侧元素
                    int temp = arr1[j];
                    arr1[j] = arr1[i];
                    arr1[i] = temp;
                    j--;
                }
            }
            int maxLeft = i - 1;
            int minRight = i + 1;
            if (maxLeft > fromAndTo[0]) {
                toSortIndexes.add(new Integer[]{fromAndTo[0], maxLeft});
            }
            if (minRight < fromAndTo[1]) {
                toSortIndexes.add(new Integer[]{minRight, fromAndTo[1]});
            }
        }
        return arr1;
    }

    /**
     * 第二种非递归(减少交换次数，用一个变量保存哨兵)
     *
     * @param arr
     * @return
     */
    private static int[] nonrecursiveTwo2(int[] arr) {
        Objects.requireNonNull(arr);
        int[] arr1 = Arrays.copyOf(arr, arr.length);
        Queue<Integer[]> toSortIndexes = new LinkedList<>();
        toSortIndexes.add(new Integer[]{0, arr1.length - 1});
        int sentinel;
        while (!toSortIndexes.isEmpty()) {
            Integer[] fromAndTo = toSortIndexes.remove();
            int i = fromAndTo[0];
            int j = fromAndTo[1];
            sentinel = arr1[i];
            while (i < j) {
                //backward 从右侧向左侧遍历
                while (i < j && sentinel <= arr1[j]) {
                    j--;
                }
                if (j > i) {
                    //右侧元素放在当前位置
                    arr1[i] = arr1[j];
                    i++;
                }
                //forward 从左侧向右侧遍历
                while (i < j && sentinel >= arr1[i]) {
                    i++;
                }
                if (i < j) {
                    //左侧元素放在当前位置
                    arr1[j] = arr1[i];
                    j--;
                }
            }
            arr1[i] = sentinel;
            int maxLeft = i - 1;
            int minRight = i + 1;
            if (maxLeft > fromAndTo[0]) {
                toSortIndexes.add(new Integer[]{fromAndTo[0], maxLeft});
            }
            if (minRight < fromAndTo[1]) {
                toSortIndexes.add(new Integer[]{minRight, fromAndTo[1]});
            }
        }
        return arr1;
    }

    /**
     * 递归(减少交换次数，用一个变量保存哨兵)
     *
     * @param arr
     * @return
     */
    private static int[] recursiveTwo2(int[] arr, int low, int high) {
        Objects.requireNonNull(arr);
        int i = low;
        int j = high;
        int sentinel;
        if (i < j && i >= 0 && j < arr.length) {
            sentinel = arr[i];
            while (i < j) {
                //backward 从右侧向左侧遍历
                while (i < j && sentinel <= arr[j]) {
                    j--;
                }
                if (j > i) {
                    //右侧元素放在当前位置
                    arr[i] = arr[j];
                    i++;
                }
                //forward 从左侧向右侧遍历
                while (i < j && sentinel >= arr[i]) {
                    i++;
                }
                if (i < j) {
                    //左侧元素放在当前位置
                    arr[j] = arr[i];
                    j--;
                }
            }
            arr[i] = sentinel;
            recursiveTwo2(arr, low, i - 1);
            recursiveTwo2(arr, i + 1, high);
        }
        return arr;
    }
}
