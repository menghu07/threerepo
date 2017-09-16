package com.apeny.httprpc.domain;

import java.io.Serializable;

/**
 * Created by ahu on 2017年09月16日.
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 2L;

    private int id;
    private String name;
    private String addr;

    public Person() {}

    public Person(int id, String name, String addr) {
        this.id = id;
        this.name = name;
        this.addr = addr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "id=" + id + ", name=" + name + ", addr=" + addr;
    }
}
