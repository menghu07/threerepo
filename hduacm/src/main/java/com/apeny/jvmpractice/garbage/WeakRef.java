package com.apeny.jvmpractice.garbage;

import java.lang.ref.WeakReference;

/**
 * 如果一个对象只被弱引用引用，就会被垃圾回收，但垃圾回收线程什么时候执行，还不知道
 * Created by ahu on 2017年10月04日.
 */
public class WeakRef {
    private static class User {
        private int id;
        private String name;
        User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static void main(String[] args) {
        collectWeakRef();
    }

    private static void collectWeakRef() {
        User user = new User(123, "fff");
        WeakReference<User> reference = new WeakReference<User>(user);
        System.out.println("before gc reference object: " + reference.get());
        user = null;
        System.gc();
        System.out.println("after gc reference object: " + reference.get());
    }
}
