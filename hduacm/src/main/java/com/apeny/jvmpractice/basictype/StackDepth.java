package com.apeny.jvmpractice.basictype;

/**
 * Created by ahu on 2017年09月24日.
 */
public class StackDepth {
    private static int count = 0;

    public static void main(String[] args) {
        try {
            recursion();
        } catch (Throwable ex) {
            System.out.println("stack depth value:" + count);
            ex.printStackTrace();
        }
    }

    private static void recursion() {
        count++;
        recursion();
    }
}
