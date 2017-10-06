package com.apeny.jvmpractice.youngold;

/**
 * 创建的新对象不可能直接到Survivor空间，Eden空间不够，触发GC，再不够放入老年代
 * Created by ahu on 2017年10月06日.
 */
public class BigObject {
    public static void main(String[] args) {
//        bigObject1();
        allocateUseOrNonuseTLAB();
    }

    /**
     * bytes = new byte[100 * 1024 * 1024]
     * 1 大对象，直接晋升到老年代 到GC时 survivor空间不够 -Xms1500m -Xmn400m -XX:+PrintGCDetails -XX:+PrintHeapAtGC
     * 2 大对象初始化创建时就分配在老年代 -Xms1500m -Xmn100m -XX:+PrintGCDetails -XX:+PrintHeapAtGC
     *
     * 3 超过一定阈值的大对象PretenureSizeThreshold只对串行回收器有效 -Xms1500m -Xmn400m -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:PretenureSizeThreshold=90m -XX:+UseSerialGC
     * -Xms1500m -Xmn400m -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:PretenureSizeThreshold=90m -XX:+UseParNewGC
     * 在用-Xms1500m -Xmn400m -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:PretenureSizeThreshold=90m -XX:+UseParallelGC 或 -XX:+UseParallelOldGC时
     * 效果一样新生代用ParallelGC老年代用ParallelOldGC
     * byte[] bytes = new byte[5 * 1024 * 1024]
     * 4 -XX:+UseTLAB TLAB空间较小，1500m 不用TLAB时超过PretenureSizeThreshold的对象分配到老年代；
     * 500m堆空间时，不论是否UseTLAB都会分配到老年代；
     *
     */
    private static void bigObject1() {
        byte[] bytes = new byte[5 * 1024 * 1024];
//        while (true) {
//            byte[] bytes1 = new byte[1 * 1024 * 1024];
//        }
    }

    /**
     *
     */
    private static void allocateUseOrNonuseTLAB() {
        int i = 0;
        long begin = System.nanoTime();
        while (i < 999999) {
            byte[] bytes = new byte[10000];
            i++;
        }
        System.out.println("use time: "+ (System.nanoTime() - begin));
    }
}
