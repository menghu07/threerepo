package com.apeny.thread.init.notsafe.notsafepublish;

/**
 * Created by monis on 2018/9/9.
 */
public class Holder {
    private int i;
    public Holder(int i, InitClass initClass) {
        System.out.println("hold " + initClass.holder);
        initClass.testAssert();
        this.i = i;
    }

    public boolean assertSanity() {
        if (i != i) {
            System.out.print("not equals");
            System.exit(1);
        }
        return true;
    }
}
