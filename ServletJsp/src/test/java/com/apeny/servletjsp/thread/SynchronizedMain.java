package com.apeny.servletjsp.thread;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SynchronizedMain {
    
    public static Map<String, String> NO_SYNC_MAP = new HashMap<String, String>();
    
    public static final Object MUTEX = new Object();
    
    public static PrintStream PS;
    
    public static int writeCount = 5;
    
    static {
        NO_SYNC_MAP.put("AA", "AA");
        NO_SYNC_MAP.put("AB", "AB");
        NO_SYNC_MAP.put("AC", "AC");
        NO_SYNC_MAP.put("AD", "AD");
        NO_SYNC_MAP.put("AE", "AE");
    }
    
    public static void main(String[] args) throws FileNotFoundException {
//        testIj();
        readAndThenWrite();
    }
    
    public static void readAndThenWrite() throws FileNotFoundException {
        PS= new PrintStream(new FileOutputStream("F:/logs/threads/out.txt"));
        System.setOut(PS);
        new Thread(new ReadThread()).start();
        new Thread(new WriteThread()).start();
    }
    
    public static void testIj() {
        int i = 0;
        System.out.println((i == (i = NO_SYNC_MAP.size())) + "== " + i);
        Integer ij = new Integer(12041);
        Integer kq = new Integer(12041);
        Set<Integer> set = new HashSet<>();
        set.add(ij);
        set.add(kq);
        System.out.println((ij == kq) + "== " + ij.equals(kq) + set.size());
    }
}

/**
 * 读Map线程
 * @author mengqh
 *
 */
class ReadThread implements Runnable {
    
    @Override
    public void run() {
        int i = 0;
        int j = -1;
        Set<Integer> counted = new HashSet<>();
        while (true) {
            i = SynchronizedMain.NO_SYNC_MAP.size();
            
            //一种假设 read的结果不变------------
            /*int oldCount = counted.size();
            counted.add(i);
            if (oldCount != 0 && (j = counted.size()) != oldCount) {
                System.out.println("break oldCount = " + oldCount + "; NO_SYNC_MAP = " + j + " ii = " + i);
                System.exit(1);
            }
            System.out.println("read thread equals oldCount = j : oldCount =" + oldCount + "; j = " + j + " ii = " + i);
            */
            //end-------------------------
            
            //另一种假设 read的结果和write相同------------
            int newCount;
            synchronized (SynchronizedMain.MUTEX) {
                newCount = SynchronizedMain.writeCount;
            }
            int oldCount = counted.size();
            counted.add(i);
            System.out.println(System.currentTimeMillis() + " read thread equals oldCount = j : oldCount =" + oldCount + "; j = " + j + " ii = " + i);
            if (oldCount != 0 && newCount != i) {
                System.out.println("break oldCount = " + oldCount + "; NO_SYNC_MAP = " + j + " ii = " + i);
                System.exit(1);
            }
            //end-------------------------
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 写Map线程
 * @author mengqh
 *
 */
class WriteThread implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = 0;
        while (i < 999999) {
            int j = new Random().nextInt(99999);
            Map<String, String> map = new HashMap<>();
            for (int z = 0; z < j; z++) {
                map.put("AA" + z, "AA" + z);
            }
            int z = map.size();
            System.out.println(System.currentTimeMillis() + " write thread has exec: " + z);
            synchronized (SynchronizedMain.MUTEX) {
                SynchronizedMain.writeCount = z;
            }
            SynchronizedMain.NO_SYNC_MAP = map;
            i++;
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
        
