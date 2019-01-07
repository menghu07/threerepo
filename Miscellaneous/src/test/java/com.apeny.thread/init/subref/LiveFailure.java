package com.apeny.thread.init.subref;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/9/9.
 */
public class LiveFailure {
    private static boolean stopRequest;

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = Arrays.asList(new Integer[]{1, 0});
        System.out.println(new int[1].getClass());
        System.out.println(new int[]{4, 34});
        print(new int[]{3, 34});
//        print(new Integer[]{3, 34});
        System.out.println(Arrays.asList(new Integer[]{1, 0}));
        Thread backGround = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                while (!stopRequest) {
//                    System.out.println("i execute" + i);
                    i++;
                }
                System.out.println("i executed..............");
            }
        });
        backGround.start();
        TimeUnit.SECONDS.sleep(1);
        stopRequest = true;
//        TimeUnit.SECONDS.sleep(100000);
        System.out.println("程序一秒结束？");
    }

    private static  void print(int... x) {
        System.out.println(" type " + x);
    }
}
