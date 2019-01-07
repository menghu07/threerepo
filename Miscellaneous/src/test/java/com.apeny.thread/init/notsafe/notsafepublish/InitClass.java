package com.apeny.thread.init.notsafe.notsafepublish;

/**
 * Created by monis on 2018/9/9.
 */
public class InitClass {
    public Holder holder;

    public void initialize() {
        holder = new Holder(42, this);
    }

    public void testAssert() {
        while (true) {
            try {
                holder.assertSanity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
