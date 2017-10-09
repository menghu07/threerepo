package com.apeny.jvmpractice.youngold;

/**
 * Created by ahu on 2017年10月05日.
 */
public class ToOldGeneration {
    public static void main(String[] args) {
        oldGeneration();
    }

    private static void oldGeneration() {
        byte[] bytes = new byte[5 * 1024 * 1024];
        while (true) {
            byte[] bytes1 = new byte[1024 * 1024];
            bytes1 = null;
        }
    }
}
