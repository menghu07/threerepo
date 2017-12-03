package com.apeny.proxy;

/**
 * Created by apeny on 2017/12/3.
 */
public class Math implements IMath {

    @Override
    public int add(int i, int j) {
        System.out.println("i + j = " + (i + j));
        return i + j;
    }

    class $yy {

    }
}
