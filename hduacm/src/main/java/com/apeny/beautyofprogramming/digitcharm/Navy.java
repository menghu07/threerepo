package com.apeny.beautyofprogramming.digitcharm;

/**
 * 找水军，发帖数量超过总数一半的ID
 * Created by monis on 2019/3/17.
 */
public class Navy {

    public static void main(String[] args) {
        int[] navies = {1, 20, 203, 0, 20, 2, 20, 20, 1, 2, 20, 2, 20, 20, 1, 4, 20, 9, 20, 20, 293, 20};
        System.out.println("navy number: " + findNavy(navies));
    }

    private static int findNavy(int[] posted) {
        if (posted == null || posted.length == 0) {
            throw new IllegalArgumentException("参数错误");
        }
        int times = 0;
        int candidate = posted[0];
        for (int i = 0; i < posted.length; i++) {
            if (times == 0) {
                candidate = posted[i];
                times = 1;
            } else {
                if (candidate == posted[i]) {
                    times++;
                } else {
                    times--;
                }
            }
        }
        return candidate;
    }
}
