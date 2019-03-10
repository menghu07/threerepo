package com.apeny.beautyofprogramming.gameentertainment;

/**
 * 饮料供货算法，使用动态规划
 * 有N种饮料，要求总容量V=∑(Vi * Bi)，满意度∑(Hi*Bi)最大
 * 数组表示(Si, Vi, Ci, Hi, Bi)，Si标识饮料名称、Vi单个饮料容量（2的m次幂，如2，4，8，16等）、
 * Ci饮料Si最大个数，Hi满意度值、Bi实际产品个数
 * Created by monis on 2019/2/17.
 */
public class SupplyDrink {

    //最小值
    private static final int POSITIVE_INF = -1;

    //初始值
    private static final int INITIAL = -999;

    //总容量
    private static final int V = 100;

    //饮料种数
    private static final int N = 3;

    private static final int[][] optimization = new int[V + 1][N + 1];

    static {
        for (int i = 0; i <= V; i++) {
            for (int j = 0; j <= N; j++) {
                optimization[i][j] = INITIAL;
            }
        }
    }

    private final Optimization NULL = new Optimization(null, POSITIVE_INF, POSITIVE_INF, null);

    //饮料名称
    private String[] siArray = {"Milk", "Coffee", "Coca-Cola"};

    //单个饮料容量
//    private int[] viArray = {4, 2, 8};
    //单个饮料容量2
    private int[] viArray = {8, 4, 2};

    //满意度
//    private int[] happiness = {2, 5, 3};

    //满意度2
    private int[] happiness = {3, 2, 5};

    //每个饮料最大个数
    private int[] ciArray = {V / viArray[0], V / viArray[1], V / viArray[2]};

    public static void main(String[] args) {
        maxOutputSupply();
    }

    private static void maxOutputSupply() {
//        new SupplyDrink().maxHappiness();
        System.out.println("maxHappiness Value: " + new SupplyDrink().maxHappiness(V, 0));
//        new SupplyDrink().maxHappinessNodes();
    }

    /**
     * 最大满意度
     *
     * @return
     */
    private int maxHappiness() {
        for (int i = 1; i <= V; i++) {
            optimization[i][N] = POSITIVE_INF;
        }
        for (int drink = N - 1; drink >= 0; drink--) {
            //饮料drink到N-1种饮料的最优化结果
            for (int volume = 0; volume <= V; volume++) {
                //总容量是volume时最优化结果
                optimization[volume][drink] = POSITIVE_INF;
                for (int count = 0; count <= ciArray[drink]; count++) {
                    if (volume >= count * viArray[drink]) {
                        int otherOptimization = optimization[volume - count * viArray[drink]][drink + 1];
                        if (otherOptimization != POSITIVE_INF) {
                            int currentHappiness = count * happiness[drink] + otherOptimization;
                            if (currentHappiness > optimization[volume][drink]) {
                                optimization[volume][drink] = currentHappiness;
                            }
                        }
                    }
                }
            }
        }
        //输出数组结果
        for (int i = 0; i <= V; i++) {
            System.out.print(leftPadding(i) + "[");
            for (int o : optimization[i]) {
                System.out.print(leftPadding(o));
            }
            System.out.println("]");
        }
        return optimization[V][0];
    }

    /**
     * 最大满意度备忘录算法
     *
     * @return
     */
    private int maxHappiness(int volume, int drink) {
        if (drink == N) {
            if (volume == 0) {
                return 0;
            } else {
                return POSITIVE_INF;
            }
        }
        if (volume < 0 || volume > V || drink < 0 || drink > N) {
            throw new IllegalArgumentException("参数错误");
        }
        //drink 0 -> N-1
        if (volume == 0) {
            return 0;
        } else if (optimization[volume][drink] != INITIAL) {
            return optimization[volume][drink];
        }
        optimization[volume][drink] = POSITIVE_INF;
        for (int count = 0; count <= ciArray[drink]; count++) {
            if (volume < count * viArray[drink]) {
                break;
            }
            int otherOptimization = maxHappiness(volume - count * viArray[drink], drink + 1);
            if (otherOptimization != POSITIVE_INF) {
                int currentHappiness = count * happiness[drink] + otherOptimization;
                if (currentHappiness > optimization[volume][drink]) {
                    optimization[volume][drink] = currentHappiness;
                }
            }
        }
        return optimization[volume][drink];
    }

    private String leftPadding(int i) {
        String istr = String.valueOf(i);
        while (istr.length() < 4) {
            istr = " " + istr;
        }
        return istr;
    }


    /**
     * 最大满意度
     *
     * @return
     */
    private int maxHappinessNodes() {
        Optimization[][] optimization = new Optimization[V + 1][N + 1];
        optimization[0][N] = new Optimization(null, 0, 0, NULL);
        for (int i = 1; i <= V; i++) {
            optimization[i][N] = NULL;
        }
        for (int drink = N - 1; drink >= 0; drink--) {
            //饮料drink到N-1种饮料的最优化结果
            for (int volume = 0; volume <= V; volume++) {
                //总容量是volume时最优化结果
                optimization[volume][drink] = NULL;
                for (int count = 0; count <= ciArray[drink]; count++) {
                    if (volume >= count * viArray[drink]) {
                        Optimization otherOptimization = optimization[volume - count * viArray[drink]][drink + 1];
                        if (otherOptimization != NULL) {
                            int currentHappiness = count * happiness[drink] + otherOptimization.value;
                            if (currentHappiness > optimization[volume][drink].value) {
                                optimization[volume][drink] = new Optimization(siArray[drink], count, currentHappiness, otherOptimization);
                            }
                        }
                    }
                }
            }
        }
        //输出数组结果
        for (int i = 0; i <= V; i++) {
            System.out.print(leftPadding(i) + "[");
            for (Optimization o : optimization[i]) {
                System.out.print(leftPadding(o.value));
            }
            System.out.println("]");
        }
        //输出最优化结果
        Optimization maxOptimization = optimization[V][0];
        while (maxOptimization != NULL) {
            System.out.println("name: " + maxOptimization.drink + ", count: " + maxOptimization.count
                    + ", value: " + maxOptimization.value);
            maxOptimization = maxOptimization.next;
        }
        return optimization[V][0].value;
    }

    /**
     * 最优化算法节点
     */
    private class Optimization {
        private final String drink;

        private final int count;

        private final int value;

        private final Optimization next;

        Optimization(String drink, int count, int value, Optimization next) {
            this.drink = drink;
            this.count = count;
            this.value = value;
            this.next = next;
        }
    }
}
