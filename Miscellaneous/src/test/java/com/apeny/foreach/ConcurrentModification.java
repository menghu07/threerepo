package com.apeny.foreach;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by apeny on 2018/1/28.
 */
public class ConcurrentModification {
    public static void main(String[] args) {
//        concurrentException();
        forEachList();
    }

    private static void concurrentException() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            System.out.println("number " + i);
            if (i == 1) {
                list.remove(i);
            }
        }
    }

    /**
     * for each 本质还是iterator
     */
    private static void forEachList() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        for (Integer i : list) {
            System.out.println("number " + i);
            if (i == 1) {
                list.remove(i);
            }
        }
    }
}
