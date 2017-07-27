package com.apeny.servletjsp.redis;

import com.apeny.servletjsp.bean.JedisClusterSupport;
import com.apeny.servletjsp.redis.importer.RedisImportWorker;
import com.apeny.servletjsp.util.RedisPipelineUtil;
import com.apeny.servletjsp.vo.RedisObjectVO;
import redis.clients.jedis.Pipeline;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ahu on 2017/7/23.
 */
public class ImportToRedis {
    public final static ConcurrentMap<Integer, List<RedisObjectVO>> toRedis = new ConcurrentHashMap<>();
    public final static AtomicInteger count = new AtomicInteger();
    private JedisClusterSupport clusterSupport;

    public ImportToRedis(JedisClusterSupport clusterSupport) {
        this.clusterSupport = clusterSupport;
    }

    public void fromDB() {
        List<RedisObjectVO> objectFromDB = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            RedisObjectVO vo = new RedisObjectVO();
            vo.setKey("w" + i);
            vo.setValue("v" + i);
            objectFromDB.add(vo);
        }
        RedisPipelineUtil.putSlot2Keys(objectFromDB, toRedis);
    }

    public void importToRedis() {
        ExecutorService executor = new ThreadPoolExecutor(8, 10, 60, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(100000));
        for (Map.Entry<Integer, List<RedisObjectVO>> valuesInSlot : toRedis.entrySet()) {
            Pipeline pipeline = clusterSupport.pipeline(valuesInSlot.getKey());
            executor.execute(new RedisImportWorker(pipeline, valuesInSlot.getValue()));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
