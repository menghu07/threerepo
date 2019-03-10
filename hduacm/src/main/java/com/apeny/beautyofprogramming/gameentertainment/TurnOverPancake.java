package com.apeny.beautyofprogramming.gameentertainment;

import java.util.Arrays;

/**
 * Created by monis on 2019/2/13.
 */
public class TurnOverPancake {

    //烙饼信息数组
    private int[] mCakeArray;

    //最多交换次数
    private int mnMaxSwap;

    //翻转烙饼的总个数
    private int swapCount;

    //当前翻转烙饼步骤数组
    private int[] mReverseCakeArraySwap;

    //最终排序结果
    private int[] targetCakeArray;

    private TurnOverPancake(int[] pCakeArray) {
        assert (pCakeArray != null);
        mCakeArray = new int[pCakeArray.length];
        targetCakeArray = new int[pCakeArray.length];
        System.arraycopy(pCakeArray, 0, mCakeArray, 0, pCakeArray.length);
        System.arraycopy(pCakeArray, 0, targetCakeArray, 0, pCakeArray.length);
        Arrays.sort(targetCakeArray);
        init();
    }

    public static void main(String[] args) {
        sampleOne();
    }

    /**
     * 样例1
     */
    private static void sampleOne() {
        TurnOverPancake turnOverPancake = new TurnOverPancake(new int[]{6, 3, 2, 5, 1, 4, 9, 8, 7});
        turnOverPancake.traverse(0, 0);
    }

    private void init() {
        mnMaxSwap = upperBound(mCakeArray.length);
        mReverseCakeArraySwap = new int[mnMaxSwap];
    }

    /**
     * 依次翻转数组
     *
     * @param step
     * @param beforeIndex 前一次翻转是索引值,从0开始
     */
    private void traverse(int step, int beforeIndex) {
        //递归调用终止条件
        //1. step + lowerBound > mnMaxSwap
        //2. 已经排好序
        if (step + lowerBound() > mnMaxSwap) {
            return;
        }
        if (isSorted()) {
            if (step < mnMaxSwap) {
                mnMaxSwap = step;
            }
            print();
            return;
        }
        for (int i = 1; i < mCakeArray.length; i++) {
            //不让上一次翻转索引和此次翻转索引相同，因为这属于没有意义的两次翻转
            if (i != beforeIndex) {
                //翻转0到i
                reverseCake(i);
                swapCount += i;
                mReverseCakeArraySwap[step] = i;
                traverse(step + 1, i);
                //恢复上面翻转的0到i
                reverseCake(i);
                swapCount -= i;
            }
        }
    }

    private boolean isSorted() {
        for (int i = 0; i < mCakeArray.length - 1; i++) {
            int before = mCakeArray[i];
            int after = mCakeArray[i + 1];
            if (before > after) {
                return false;
            }
        }
        return true;
    }

    /**
     * 最大翻转次数
     *
     * @param mnCakeCount
     * @return
     */
    private int upperBound(int mnCakeCount) {
        return Math.max(2 * mnCakeCount - 3, 0);
    }

    /**
     * 最小翻转次数
     *
     * @return
     */
    private int lowerBound() {
        int lowerBound = 0;
        boolean descend = false;
        for (int i = 1; i < mCakeArray.length - 1; i++) {

            int before = mCakeArray[i - 1];
            int middle = mCakeArray[i];
            int after = mCakeArray[i + 1];
            if ((before < middle && middle > after) || (before > middle && middle < after)) {
                lowerBound++;
            }
            if (before > middle) {
                descend = true;
            }
        }
        if (lowerBound == 0 && descend) {
            lowerBound = 1;
        }
        return lowerBound;
    }

    /**
     * 翻转索引在0到i的元素
     * @param i
     */
    private void reverseCake(int i) {
        int j = 0;
        int z = i;
        while (j < z) {
            int temp = mCakeArray[j];
            mCakeArray[j] = mCakeArray[z];
            mCakeArray[z] = temp;
            j++;
            z--;
        }
    }

    /**
     * 打印翻转步骤
     */
    private void print() {
        System.out.print("交换次数" + mnMaxSwap + ", 总翻转个数: " + swapCount + ": [");
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mnMaxSwap; i++) {
            builder.append("," + mReverseCakeArraySwap[i]);
        }
        System.out.println(builder.substring(1) + "]");
    }
}