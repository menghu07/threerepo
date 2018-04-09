package com.apeny.thread;

import java.util.concurrent.SynchronousQueue;

/**
 * Created by apeny on 2018/4/9.
 */
public class SynchronousQueueMain {
    public static void main(String[] args) {
        SynchronousQueue<Integer> integerSynchronousQueue = new SynchronousQueue<>();
        integerSynchronousQueue.offer(102);
    }
}
