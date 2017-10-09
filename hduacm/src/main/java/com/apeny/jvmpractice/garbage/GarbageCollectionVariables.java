package com.apeny.jvmpractice.garbage;

import static com.apeny.jvmpractice.methodregion.CglibBeanClass.createBeanNotWeak;

/**
 * Created by ahu on 2017年10月03日.
 */
public class GarbageCollectionVariables {
    public static void main(String[] args) {
//        allocate();

        //不会抛出OOM，可以被回收
        for (int i = 0; i < 100_000_1000; i++) {
            createBeanNotWeak();
        }

//        for (int i = 0; i < 10; i++) {
//            allocateLongArr();
//        }
    }

    /**
     * 内存空间的释放需要标记的！！！，要不然释放不了，除非超出作用域
     */
    private static void allocate() {
        long[] longArr = new long[1024 * 1024 * 90];
        longArr = null;
        long[] longArr2 = new long[1024 * 1024 * 90];
        System.gc();
        System.gc();
    }

    /**
     * 可以被回收，循环调用时
     *
     * @return
     */
    private static long[] allocateLongArr() {
        long[] longArr = new long[1024 * 1024 * 90];
        return longArr;
    }
}
