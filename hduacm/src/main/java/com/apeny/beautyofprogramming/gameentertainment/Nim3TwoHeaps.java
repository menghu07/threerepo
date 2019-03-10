package com.apeny.beautyofprogramming.gameentertainment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 两堆石头，每次只能拿两堆中相同数量的石头，或只拿其中一个堆中石头，最后把两个堆中石头拿完整赢
 * Created by monis on 2019/2/23.
 */
public class Nim3TwoHeaps {
    public static void main(String[] args) {
        testWins();
    }

    private static void testWins() {
        System.out.println("nums: 1 2 win? " + toupperMathod(1, 2));
        System.out.println("nums: 1 5 win? " + toupperMathod(1, 5));
        System.out.println("nums: 2 3 win? " + toupperMathod(2, 3));
        System.out.println("nums: 4 6 win? " + toupperMathod(4, 6));
        System.out.println("nums: 3 5 win? " + toupperMathod(3, 5));
        System.out.println("nums: 5 3 win? " + toupperMathod(5, 3));
        System.out.println("nums: 6 9 win? " + toupperMathod(6, 9));
        System.out.println("nums: 7 4 win? " + toupperMathod(7, 4));
        System.out.println("nums: 6 10 win? " + toupperMathod(6, 10));
        System.out.println("nums: 6 11 win? " + toupperMathod(6, 11));
    }

    private static boolean toupperMathod(int num1, int num2) {
        if (num1 < 0 || num2 < 0) {
            throw new IllegalArgumentException("参数错误");
        }
        if (num1 == 0 && num2 == 0) {
            //i lose
            return false;
        }
        if (num1 == num2) {
            // i win
            return true;
        }
        if (num1 > num2) {
            int t = num2;
            num2 = num1;
            num1 = t;
        }
        if (num1 == 1 && num2 == 2) {
            return false;
        }
        List<Integer[]> notSafes = new ArrayList<>();
        notSafes.add(new Integer[]{1, 2});
        //找到第一个比num1大的an
        int an = 1;
        int level = 2;
        while (an < num1) {
            an = firstNotExisted(notSafes);
            notSafes.add(new Integer[]{an, an + level++});
        }
        return !(an == num1 && num2 == an + level - 1);
    }

    private static int firstNotExisted(List<Integer[]> notSafes) {
        Set<Integer> sortedNums = new TreeSet<>();
        notSafes.forEach(x -> {
            sortedNums.add(x[0]);
            sortedNums.add(x[1]);
        });
        int first = -1;
        for (int x : sortedNums) {
            if (first != -1) {
                if (x - first != 1) {
                    return first + 1;
                } else {
                    first = x;
                }
            } else {
                first = x;
            }
        }
        return first + 1;
    }
}
