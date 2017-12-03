package com.apeny;

import java.lang.reflect.Field;

/**
 * Created by apeny on 2017/12/3.
 */
public class ExchageInteger {
    public static void main(String[] args) {
        exchange();
    }

    private static void exchange() {
        Integer a = 1;
        Integer b = 2;
        System.out.println("before exchange a = " + a + ", b = " + b);
        exchange(a, b);
        System.out.println("after exchange a = " + a + ", b = " + b);
    }

    private static void exchange(Integer a, Integer b) {
        try {
            Field field = Integer.class.getDeclaredField("value");
            field.setAccessible(true);
            int temp = a.intValue();
            try {
                field.set(a, b);
                //自动装箱会把1装箱成new Integer(2)
                field.set(b, new Integer(temp));
                System.out.println("in exchange after exchange: a = " + a + ", b = " + b);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
