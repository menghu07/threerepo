package com.apeny.beautyofprogramming.digitcharm;

import java.util.Arrays;
import java.util.Objects;

/**
 * 寻找最大K个数
 * Created by monis on 2019/5/4.
 */
public class MaxK {

    public static void main(String[] args) {
        testOne();
    }

    private static void testOne() {
        int arr2[] = {1, 3, 49, 32, 99, 203, 93, 374, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        bubble(arr2, 5);
        System.out.println("maxk bubble sort before sort: " + Arrays.toString(arr2)
                + "\nafter sort: " + Arrays.toString(arr2));
        int arr3[] = {1, 3, 49, 32, 99, 203, 93, 374, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("maxk select sort before sort: " + Arrays.toString(arr3)
                + "\nafter sort: " + Arrays.toString(select(arr3, 5)));
        int arr4[] = {1, 3, 49, 32, 99, 203, 93, 374, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("maxk heap sort before sort: " + Arrays.toString(arr4)
                + "\nafter sort: " + Arrays.toString(heap(arr4, arr4.length)));
        int arr5[] = {1, 3, 49, 32, 99, 203, 93, 374, 4, 6, 2, 9, 10, 8, 11, 45, 22, 5, 35};
        System.out.println("maxk UseHeap sort before sort: " + Arrays.toString(arr5)
                + "\nafter sort: " + Arrays.toString(keepUseHeap(arr5, 8)));
    }

    /**
     * 使用冒泡排序
     * @param arr
     * @param k
     */
    private static int[] bubble(int[] arr, int k) {
        Objects.requireNonNull(arr);
        assert k > 0;
        assert arr.length >= k;
        boolean isExchanged;
        int temp;
        for (int i = arr.length - 1; i >= arr.length - k && i > 0; i--) {
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

    /**
     * 选择排序(选择最大元素放在数组靠前位置)
     * @param arr
     * @param k
     */
    private static int[] select(int[] arr, int k) {
        Objects.requireNonNull(arr);
        assert k > 0;
        assert arr.length >= k;
        //最大元素的下标
        int maxValueIndex;
        //将排序的下标
        int toSortIndex = 0;
        while (toSortIndex < k && toSortIndex < arr.length - 1) {
            maxValueIndex = toSortIndex;
            for (int i = toSortIndex + 1; i < arr.length; i++) {
                if (arr[i] > arr[maxValueIndex]) {
                    maxValueIndex = i;
                }
            }
            //交换最大元素和将排序下标对应的元素
            if (toSortIndex < maxValueIndex) {
                int temp = arr[toSortIndex];
                arr[toSortIndex] = arr[maxValueIndex];
                arr[maxValueIndex] = temp;
            }
            toSortIndex++;
        }
        return arr;
    }

    /**
     * 堆排序
     * @param arr
     * @param k
     * @return
     */
    private static int[] heap(int[] arr, int k) {
        Objects.requireNonNull(arr);
        assert k > 0;
        assert arr.length >= k;
        int heapSize = arr.length;
        //初始建堆
        createHeap(arr, heapSize);
        //交换堆根节点和堆最后一个节点
        swap(arr, 0, heapSize - 1);
        //堆大小减1
        heapSize--;
        while (heapSize > arr.length - k && heapSize > 1) {
            //重建堆
            recreateHeap(arr, 1, heapSize);
            //交换堆根节点和堆最后一个节点
            swap(arr, 0, heapSize - 1);
            //堆大小减1，重复以上步骤直到堆大小为1
            heapSize--;
        }
        return arr;
    }

    /**
     * 初始建堆
     *
     * @param arr
     * @param lastIndex
     */
    private static void createHeap(int[] arr, int lastIndex) {
        //从有子节点的最大节点开始遍历，往前n->1查询，创建堆
        int maxHasChildrenIndex = lastIndex >> 1;
        int maxIndex;
        for (int i = maxHasChildrenIndex; i >= 1; i--) {
            maxIndex = i << 1;
            if (maxIndex + 1 <= lastIndex && arr[maxIndex - 1] < arr[maxIndex]) {
                maxIndex++;
            }
            if (arr[maxIndex - 1] > arr[i - 1]) {
                swap(arr, i - 1, maxIndex - 1);
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
    private static void recreateHeap(int[] arr, int tempRoot, int lastIndex) {
        //从根节点开始重建堆，直到新根节点找到最终位置
        int maxIndex;
        for (int i = tempRoot; 2 * i <= lastIndex; ) {
            maxIndex = 2 * i;
            if (maxIndex + 1 <= lastIndex && arr[maxIndex - 1] < arr[maxIndex]) {
                maxIndex++;
            }
            if (arr[maxIndex - 1] > arr[i - 1]) {
                swap(arr, i - 1, maxIndex - 1);
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
    private static void swap(int[] arr, int x, int y) {
        int z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }

    /**
     * 堆排序
     * @param arr
     * @param k
     * @return
     */
    private static int[] keepUseHeap(int[] arr, int k) {
        Objects.requireNonNull(arr);
        assert k > 0;
        assert arr.length >= k;
        int[] result = new int[k];
        int ri = 0;
        //将arr中k个元素放入结果result中
        for (; ri < k; ri++) {
            result[ri] = arr[ri];
        }
        //堆排序result
        LittleRootHeap.createHeap(result, k);
        for (; ri < arr.length; ri++) {
            if (result[0] < arr[ri]) {
                result[0] = arr[ri];
                LittleRootHeap.recreateHeap(result, 1, k);
            }
        }
        return result;
    }
}

/**
 * 建立小根堆，下标从0开始
 */
class LittleRootHeap {

    /**
     * 建立小根堆
     *
     * @param arr
     * @param lastElement
     */
    static void createHeap(int[] arr, int lastElement) {
        //从有子节点的最大节点开始遍历，往前n->1查询，创建堆
        int maxHasChildrenElement = lastElement >> 1;
        int maxElement;
        for (int i = maxHasChildrenElement; i >= 1; i--) {
            maxElement = i << 1;
            if (maxElement + 1 <= lastElement && arr[maxElement - 1] > arr[maxElement]) {
                maxElement++;
            }
            if (arr[maxElement - 1] < arr[i - 1]) {
                swap(arr, i - 1, maxElement - 1);
                recreateHeap(arr, maxElement, lastElement);
            }
        }
    }

    /**
     * 重建指定节点为根的小根堆
     *
     * @param arr
     * @param tempRoot
     * @param lastElement
     */
    static void recreateHeap(int[] arr, int tempRoot, int lastElement) {
        //从根节点开始重建堆，直到新根节点找到最终位置
        int maxElement;
        for (int i = tempRoot; 2 * i <= lastElement; ) {
            maxElement = 2 * i;
            if (maxElement + 1 <= lastElement && arr[maxElement - 1] > arr[maxElement]) {
                maxElement++;
            }
            if (arr[maxElement - 1] < arr[i - 1]) {
                swap(arr, i - 1, maxElement - 1);
                i = maxElement;
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
    static void swap(int[] arr, int x, int y) {
        int z = arr[x];
        arr[x] = arr[y];
        arr[y] = z;
    }
}