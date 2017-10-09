package com.apeny.jvmpractice.heapconfig;

import java.util.ArrayList;
import java.util.List;

/**
 * totalMemory = PSYoungGen(total) + ParOldGen ： 总内存（总可用内存） =  新生代可用内存 + 老年代内存
 * Created by ahu on 2017年10月03日.
 * -XX:+UseConcMarkSweepGC 新生代使用ParrNew回收器，老年代使用CMS回收器
 * -XX:+UseG1GC 并发标记周期初始化标记会伴随一次新生代GC，引起程序停顿
 */
public class MaxMemoryEtc {

    public static void main(String[] args) {
//        allocateMemory();
//        oom();
        totalReduce();
    }

    private static void allocateMemory() {
        Runtime runtime = Runtime.getRuntime();
        long total;
        long free;
        System.out.println("-Xms: " + (10 * 1024) + "KB");
        System.out.println("实际可使用最大内存=2^16对齐(-Xmx - from) = maxMemory: " + runtime.maxMemory() / 1024);
        System.out.println("分配内存=totalMemory: " + (total = runtime.totalMemory()) / 1024);
        System.out.println("剩余可使用内存=freeMemory: " + (free = runtime.freeMemory()) / 1024);
        System.out.println("已使用内存totalMemory - freeMemory: " + (total - free) / 1024);
        byte[] useBytes = new byte[11 * 1024 * 1024];
        System.out.println("申请useBytes之后，实际可使用最大内存=2^16对齐(-Xmx - from) = maxMemory: " + runtime.maxMemory() / 1024);
        System.out.println("申请useBytes之后，分配内存=totalMemory: " + (total = runtime.totalMemory()) / 1024);
        System.out.println("申请useBytes之后，剩余可使用内存=freeMemory: " + (free = runtime.freeMemory()) / 1024);
        System.out.println("申请useBytes之后，已使用内存totalMemory - freeMemory: " + (total - free) / 1024);
    }

    private static void oom() {
        List<byte[]> byteArrayList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            byteArrayList.add(new byte[1024 * 1024]);
        }
    }

    private static void totalReduce() {
        while (true) {
            List<byte[]> byteArrayList = new ArrayList<>();
            for (int i = 0; i < 90; i++) {
                byteArrayList.add(new byte[1024 * 1024]);
            }
        }
    }
}
