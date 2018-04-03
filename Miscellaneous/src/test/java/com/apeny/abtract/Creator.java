package com.apeny.abtract;

/**
 * Created by apeny on 2018/4/1.
 */
public class Creator extends AbstractCreator {
    @Override
    Product createProduct() {
        Product product = new Product();
        System.out.println("sub class create Product" + product);
        return product;
    }
}
