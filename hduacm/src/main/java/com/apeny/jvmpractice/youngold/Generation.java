package com.apeny.jvmpractice.youngold;

/**
 * Created by ahu on 2017年10月05日.
 */
public class Generation {
    public static void main(String[] args) {
        eden();
    }

    /**
     * 都分配在Eden空间
     */
    private static void eden() {
        byte[] bytes = new byte[10 * 1024 * 1024];
    }
}
