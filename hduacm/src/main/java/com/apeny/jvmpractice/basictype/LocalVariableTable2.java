package com.apeny.jvmpractice.basictype;

/**
 * 局部变量表
 * Created by ahu on 2017年10月02日.
 */
public class LocalVariableTable2 {

    public static void main(String[] args) {
        LocalVariableTable2 table2 = new LocalVariableTable2();
//        table2.localVariableGC1();
//        table2.localVariableGC2();
//        table2.localVariableGC3();
//        table2.localVariableGC4();
        table2.localVariableGC5();
    }

    /**
     * 不设置为null
     * 没有回收
     */
    public void localVariableGC1() {
        byte[] a = new byte[6 * 1024 * 1024];
        String yx = "yux汉字";
        System.gc();
    }

    /**
     * 设置为null
     * GC回收
     */
    public void localVariableGC2() {
        byte[] a = new byte[6 * 1024 * 1024];
        a = null;
        System.gc();
    }

    /**
     * 局部变量不不覆盖
     * 没有回收
     */
    public void localVariableGC3() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }
        System.gc();
    }

    /**
     * 覆盖
     * 回收了
     */
    public void localVariableGC4() {
        {
            byte[] a = new byte[6 * 1024 * 1024];
        }
        int c = 10;
        System.gc();
    }

    /**
     * 调用GC1()
     * Full GC回收了
     */
    public void localVariableGC5() {
        localVariableGC1();
        System.gc();
    }
}
