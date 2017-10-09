package com.apeny.jvmpractice.basictype;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * 栈上分配
 * Created by ahu on 2017年10月02日.
 */
public class AllocationStack {

    private static User u;

    public static void main(String[] args) {
        File file = new File("out.txt");
        try (PrintStream outputStream = new PrintStream(new FileOutputStream(file))) {
            System.setErr(outputStream);
            System.setOut(outputStream);
            escapeAnalyze();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void escapeAnalyze() {
        int count = 900_000_000;
        long begin = System.nanoTime();
        int i = 0;
        for (; i < count; i++) {
            try {
                nonescapeObject();
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
//            System.out.println("ii " + i);
        }
        long end = System.nanoTime();
        System.out.println("iii = "+ i + " you know? use time: " + (end - begin) / (1000 * 1000 * 1000) + "s");
    }

    /**
     * 逃逸对象
     */
    public static void escapeObject() {
        u = new User();
        u.id = 43;
        u.userName = "axi";
    }

    /**
     * 非逃逸对象
     */
    public static void nonescapeObject() throws InterruptedException {
        User user = new User();
        user.id = 23;
        user.userName = "中文no escape9999999999";
//        TimeUnit.NANOSECONDS.sleep(1);
//        System.out.println("allocate: ");
        for (int i = 0; i < 99999999; i++) {
            Math.log(i);
        }
    }

    public static class User {
        private int id;
        private String userName;
    }
}
