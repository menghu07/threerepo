package com.apeny.fullgcobjects;

import java.util.ArrayList;

/**
 * Created by apeny on 2018/5/19.
 */
public class BigList {
    public static void main(String[] args) {

        //1197571635
        ArrayList<Byte> list = new ArrayList<>(1197571635);
//        List<Byte> list = new LinkedList<>();
        System.out.println("new byte()===" + (new Byte((byte)1) == new Byte((byte)1)));
        long i = 1;
        Byte tb = new Byte((byte)1);
        int max = 1197571635;
//        max = Integer.MAX_VALUE - 3;
        try {
            for (; i <= max; i++) {
                list.add(new Byte((byte)1));
            }
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println("catch iii " + i);
        } finally {
            System.out.println("finally: " + i);
        }
        System.exit(1);
        System.out.println("last value: " + i);
    }
}
