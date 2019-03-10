package com.apeny.beautyofprogramming.gameentertainment;

import java.util.ArrayList;
import java.util.List;

/**
 * 24点，游戏规则为4张扑克每张扑克是1到13点，使用+ - * / ( )使表达式结果为24
 * Created by monis on 2019/2/24.
 */
public class TwentyFourPoints {

    private static final int N = 4;

    private static final Double PT24 = 24D;

    private static final int[] NUMS = {11, 8, 3, 5};

//    private static final int[] NUMS = {6, 6, 6, 6};

    private static final List[] EXISTED_VALUES = new ArrayList[1 << N];

    public static void main(String[] args) {
        print24();
    }

    private static void print24() {
        int full = (1 << N) - 1;
        for (int i = 0; i < N; i++) {
            List<Double> value = new ArrayList<>();
            value.add((double) NUMS[i]);
            EXISTED_VALUES[1 << i] = value;
        }
        for (int i = 1; i <= full; i++) {
            EXISTED_VALUES[i] = twentyfourPoints(i);
        }
        if (EXISTED_VALUES[full].contains(PT24)) {
            System.out.println("可以生成24点");
        }
    }

    private static List<Double> twentyfourPoints(int set) {
        if (EXISTED_VALUES[set] != null) {
            return EXISTED_VALUES[set];
        }
        EXISTED_VALUES[set] = new ArrayList();
        for (int i = 1; i < set; i++) {
            //i的二进制数是set真子集
            if ((i & set) == i) {
                List<Double> forked = fork(twentyfourPoints(i), twentyfourPoints(set - i));
                forked.forEach(x -> {
                    if (!EXISTED_VALUES[set].contains(x)) {
                        EXISTED_VALUES[set].add(x);
                    }
                });
            }
        }
        return EXISTED_VALUES[set];
    }

    /**
     * 两个集合关于+ - * /的笛卡尔积
     *
     * @param collectionA
     * @param collectionB
     * @return
     */
    private static List<Double> fork(List<Double> collectionA, List<Double> collectionB) {
        List<Double> result = new ArrayList<>();
        collectionA.forEach(x -> {
            collectionB.forEach(y -> {
                //做6种运算 a + b、 a - b、 b - a、 a * b、 a / b、 b / a
                Double plus = x + y;
                if (!result.contains(plus)) {
                    result.add(plus);
                }
                Double minus = x - y;
                if (!result.contains(minus)) {
                    result.add(minus);
                }
                minus = y - x;
                if (!result.contains(minus)) {
                    result.add(minus);
                }
                Double multiply = x * y;
                if (!result.contains(multiply)) {
                    result.add(multiply);
                }
                Double divide;
                if (y != 0) {
                    divide = x / y;
                    if (!result.contains(divide)) {
                        result.add(divide);
                    }
                }
                if (x != 0) {
                    divide = y / x;
                    if (!result.contains(divide)) {
                        result.add(divide);
                    }
                }
            });
        });
        return result;
    }
}
