package com.apeny.jvmpractice.heapconfig;

import java.nio.ByteBuffer;

/**
 * java 默认server模式启动
 * Created by ahu on 2017年10月03日.
 */
public class DirectAllocate {
    public static void main(String[] args) {
//        directAccess();
//        directAccess();
        directAllocate();
    }

    /**
     * 直接内存读写，直接内存读写快
     */
    private static void directAccess() {
        long begin = System.nanoTime();
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(50000);
        for (int i = 0; i < 100_000_000; i++) {
            for (int j = 0; j < 100; j++) {
                byteBuffer.putLong(j);
            }
            byteBuffer.flip();
            for (int j = 0; j < 100; j++) {
                byteBuffer.getLong();
            }
            byteBuffer.clear();
        }
        long end = System.nanoTime();
        System.out.println("test direct write: " + (end - begin));
        long beginHeap = System.nanoTime();
        ByteBuffer byteBufferHeap = ByteBuffer.allocate(50000);
        for (int i = 0; i < 100_000_000; i++) {
            for (int j = 0; j < 100; j++) {
                byteBufferHeap.putLong(j);
            }
            byteBufferHeap.flip();
            for (int j = 0; j < 100; j++) {
                byteBufferHeap.getLong();
            }
            byteBufferHeap.clear();
        }
        long endHeap = System.nanoTime();
        System.out.println("test heap write: " + (endHeap - beginHeap));
    }

    /**
     * 直接内存分配
     */
    private static void directAllocate() {
        long begin = System.nanoTime();
        for (int i = 0; i < 100_000_000; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(50000);
        }
        long end = System.nanoTime();
        System.out.println("直接内存分配用时：" + (end - begin));
        long beginHeap = System.nanoTime();
        for (int i = 0; i < 100_000_000; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(50000);
        }
        long endHeap = System.nanoTime();
        System.out.println("堆内存分配用时：" + (endHeap - beginHeap));

    }
}
