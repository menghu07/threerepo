package com.apeny.beautyofprogramming.gameentertainment;

import java.util.concurrent.TimeUnit;

/**
 * cpu 占有率
 * Created by monis on 2019/2/12.
 */
public class CPUOccupancy {

    public static void main(String[] args) {
//        percent50();
//        percent50Sleep10Millis();
        percent50CurrentTimeMillis();
    }

    /**
     * 50%CPU占有率
     */
    private static void percent50() {
        System.out.println("Processors: " + Runtime.getRuntime().availableProcessors());
    }

    /**
     * 睡眠10毫秒
     */
    private static void percent50Sleep10Millis() {
        while (true) {
            for (int i = 0; i < 960000000; i++) {

            }
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 使用currentTimeMillis
     */
    private static void percent50CurrentTimeMillis() {
        for (int i = 4; i < Runtime.getRuntime().availableProcessors(); i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //5毫秒
                    long tpercent50 = 5 * 1000000L;
                    while (true) {
                        long begin = System.nanoTime();
                        while (System.nanoTime() - begin <= tpercent50) {

                        }
                        try {
                            TimeUnit.NANOSECONDS.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }
}
