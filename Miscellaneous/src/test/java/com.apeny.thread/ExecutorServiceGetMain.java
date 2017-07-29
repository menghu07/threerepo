package com.apeny.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by apeny on 2017年07月29日.
 * 这个测试的结论：
 * 1. 任务未完成java.util.concurrent.FutureTask@5cad8086，抛出异常时；
 *
 */
public class ExecutorServiceGetMain {
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();

        List<Future<Boolean>> futures = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            futures.add(service.submit(new NoJobWorker(i)));
        }
        Iterator<Future<Boolean>> descending = futures.iterator();
        for (; descending.hasNext(); ) {
            Future<Boolean> future = descending.next();
            try {
                if (!future.isCancelled()) {
                    future.get();
                }
            } catch (InterruptedException e) {
                System.out.println("未完成任务" + future + e);
            } catch (ExecutionException e) {
                System.out.println("任务未完成" + future + e);
            } catch (Exception ex) {
                System.out.println("其他错误" + future + ex);
            }
        }
        System.out.println("main thread ended.......");
    }
}
