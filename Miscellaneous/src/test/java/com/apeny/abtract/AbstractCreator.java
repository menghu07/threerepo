package com.apeny.abtract;

/**
 * Created by apeny on 2018/4/1.
 */
public abstract class AbstractCreator {
    AbstractCreator() {
        System.out.println("create product abstract");
        createProduct();
    }

    abstract Product createProduct();
}
