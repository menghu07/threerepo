package com.apeny.beautyofprogramming.sortalgorithm.select;

import java.util.Arrays;
import java.util.Objects;

/**
 * 原地排序，不申请额外空间
 * 选择排序高级版
 * 堆排序
 * 不稳定排序
 * 时间复杂度：O(nlg(n)) 重建堆时间比较长
 * 空间复杂度：O(1)
 * Created by monis on 2019/5/3.
 */
public class HeapSort {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {0, 1, 3, 49, 32, 88, 99, 203, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("heap sort nonrecursive before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr2)));
        int arr3[] = {0, 1, 3, 49, 4, 2, 32, 88, 99, 7, 203, 4, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("heap sort nonrecursive before sort: " + Arrays.toString(arr3)
                + "\nafter sort: " + Arrays.toString(nonrecursive(arr3)));
        int arr4[] = {0, 1, 3, 49, 4, 2, 32, 88, 99, 7, 203, 4, 892, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("heap sort recursive before sort: " + Arrays.toString(arr4)
                + "\nafter sort: " + Arrays.toString(recursive(arr4, arr4.length - 1)));
    }

    /**
     * 非递归堆排序，arr[0]用来做交换
     *
     * @param arr
     * @return
     */
    public static int[] nonrecursive(int[] arr) {
        Objects.requireNonNull(arr);
        assert arr.length > 1;
        int heapSize = arr.length - 1;
        //初始建堆
        createHeap(arr, heapSize);
        //交换堆根节点和堆最后一个节点
        swap(arr, 1, heapSize);
        //堆大小减1
        heapSize--;
        while (heapSize > 1) {
            //重建堆
            recreateHeap(arr, 1, heapSize);
            //交换堆根节点和堆最后一个节点
            swap(arr, 1, heapSize);
            //堆大小减1，重复以上步骤直到堆大小为1
            heapSize--;
        }
        return arr;
    }

    /**
     * 递归堆排序，arr[0]用来做交换(尾递归)
     *
     * @param arr
     * @return
     */
    private static int[] recursive(int[] arr, int heapSize) {
        Objects.requireNonNull(arr);
        assert arr.length > 1;
        if (heapSize == arr.length - 1) {
            //初始建堆
            createHeap(arr, heapSize);
            //交换堆根节点和堆最后一个节点
            swap(arr, 1, heapSize);
            return recursive(arr, heapSize - 1);
        } else if (heapSize > 1) {
            recreateHeap(arr, 1, heapSize);
            //交换堆根节点和堆最后一个节点
            swap(arr, 1, heapSize);
            return recursive(arr, heapSize - 1);
        } else {
            return arr;
        }
    }

    /**
     * 初始建堆
     *
     * @param arr
     * @param lastIndex
     */
    public static void createHeap(int[] arr, int lastIndex) {
        //从有子节点的最大节点开始遍历，往前n->1查询，创建堆
        int maxHasChildrenIndex = lastIndex >> 1;
        int maxIndex;
        for (int i = maxHasChildrenIndex; i >= 1; i--) {
            maxIndex = i << 1;
            if (maxIndex + 1 <= lastIndex && arr[maxIndex] < arr[maxIndex + 1]) {
                maxIndex++;
            }
            if (arr[maxIndex] > arr[i]) {
                swap(arr, i, maxIndex);
                recreateHeap(arr, maxIndex, lastIndex);
            }
        }
    }

    /**
     * 重建指定节点为根的堆
     *
     * @param arr
     * @param tempRoot
     * @param lastIndex
     */
    public static void recreateHeap(int[] arr, int tempRoot, int lastIndex) {
        //从根节点开始重建堆，直到新根节点找到最终位置
        int maxIndex;
        for (int i = tempRoot; 2 * i <= lastIndex; ) {
            maxIndex = 2 * i;
            if (maxIndex + 1 <= lastIndex && arr[maxIndex] < arr[maxIndex + 1]) {
                maxIndex++;
            }
            if (arr[maxIndex] > arr[i]) {
                swap(arr, i, maxIndex);
                i = maxIndex;
            } else {
                return;
            }
        }
    }

    /**
     * 交换两个元素
     *
     * @param arr
     * @param x
     * @param y
     */
    public static void swap(int[] arr, int x, int y) {
        int z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }
}
