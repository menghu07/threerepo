package com.apeny.foreach;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apeny on 2018/1/13.
 */
public class foreach {
    private static int count = 2;

    public static void main(String[] args) {
        forEach();
    }

    /**
     * forEach 只会执行一次getList()
     */
    private static void forEach() {
        for (Integer i : getList()) {
            System.out.println("for each element: " + i);
        }

        //这个for会总执行size方法
        for (int i = 0; i < getList().size(); i++) {
            System.out.println("numm: " + getList().get(i));
        }
    }

    private static List<Integer> getList() {
        List<Integer> list = new ArrayList<>();
        int num = count++;
        System.out.println("list size: " + num);
        for (int i = 0; i < num; i++) {
            list.add(i);
        }
        return list;
    }
}
