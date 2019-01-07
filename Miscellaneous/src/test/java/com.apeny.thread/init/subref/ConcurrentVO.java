package com.apeny.thread.init.subref;

/**
 * Created by monis on 2018/9/8.
 */
public class ConcurrentVO {
    public ConcurrentVO() {
    }

    ConcurrentVO(long i) {
        this.i = i;
    }

    private long i;

    public long getI() {
        return i;
    }

    public void setI(long i) {
        this.i = i;
    }
}
