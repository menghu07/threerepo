package com.apeny.thread.mchar;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by ahu on 2017年07月30日.
 */
public class CharMain {
    public static void main(String[] args) {
        String java = new StringBuilder("ja").append("va").toString();
        String java2 = new StringBuilder("j").append("ava").toString();
        System.out.println(java.intern() == java2.intern());
        char c = '值';
        System.out.println("sm c: 中文" + c);

        sun.misc.Unsafe f = null;
        Calendar cc = Calendar.getInstance();
        cc.set(1960, 0, 1);
        System.out.println(new Date());
        System.out.println("matches?" + "www9203WWWW".matches("\\w+"));
    }
}
