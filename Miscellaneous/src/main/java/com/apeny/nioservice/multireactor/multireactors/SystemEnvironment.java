package com.apeny.nioservice.multireactor.multireactors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/3/4.
 */
public class SystemEnvironment {
    // 取得CPU核心數
    private static final int CORES = Runtime.getRuntime().availableProcessors();

    /**
     * subreactor 线程池
     */
    public static final ExecutorService SUBREACTOR_POOL = new ThreadPoolExecutor(3, 3,
            120, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10));

    /**
     * worker 线程池
     */
    public static final ExecutorService WORKER_POOL = new ThreadPoolExecutor(CORES, 8, 120,
            TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10000));
}
