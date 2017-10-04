package com.apeny.jvmpractice.garbage;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/** 测试程序因垃圾回收暂停的时间（老年代要做够大NewRatio=3:1或2:1）
 * Xmx1500m, Xmn900m, SurvivorRatio=1时，会抛OOM,老年代满了，新生代没满
 * Created by ahu on 2017年10月04日.
 */
public class StopTheWorld {
    public static void main(String[] args) throws InterruptedException {
        startup();
    }

    private static void startup() throws InterruptedException {
//        new OutputTime().start();
        stopTheWorld();
    }

    private static void stopTheWorld() throws InterruptedException {
        HashMap<Long, byte[]> hashMap = new HashMap<>();
        while (true) {
            if (hashMap.size() > 900 * 1024) {
                hashMap.clear();
            }
            for (int i = 0; i < 16; i++) {
                hashMap.put(System.nanoTime(), new byte[1024]);
            }
//            System.out.println(" size map: -----------" + hashMap.size());
//            System.gc();
//            System.gc();
//            TimeUnit.MILLISECONDS.sleep(10);
        }
    }

    static class OutputTime extends Thread {
        public void run() {
            long startTime = System.currentTimeMillis();
            while (true) {
                long useTime = System.currentTimeMillis() - startTime;
                System.out.println("time: " + useTime / 1000 + "." + useTime % 1000);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
