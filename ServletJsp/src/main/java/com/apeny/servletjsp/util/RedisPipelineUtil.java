package com.apeny.servletjsp.util;

import com.apeny.servletjsp.bean.JedisClusterSupport;
import com.apeny.servletjsp.redis.ImportToRedis;
import com.apeny.servletjsp.vo.RedisObjectVO;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.util.JedisClusterCRC16;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ahu on 2017/7/23.
 */
public class RedisPipelineUtil {

    public static void executeString(Pipeline pipeline, List<String> keys) {
        if (pipeline == null || keys == null || keys.isEmpty()) {
            return;
        }
        for (String key : keys) {
            pipeline.get(key);
        }
        pipeline.sync();
    }

    public static void putSlot2Keys(List<RedisObjectVO> ros, ConcurrentMap<Integer, List<RedisObjectVO>> toRedis) {
        if (ros == null || toRedis == null) {
            return;
        }
        for (RedisObjectVO ro : ros) {
            Integer slotId = JedisClusterCRC16.getSlot(ro.getKey());
            List<RedisObjectVO> valuesInSlot = Collections.synchronizedList(new ArrayList<RedisObjectVO>());
            valuesInSlot.add(ro);
            if ((valuesInSlot = toRedis.putIfAbsent(slotId, valuesInSlot)) != null) {
                valuesInSlot.add(ro);
            }
        }
        System.out.println("toRedis size: " + toRedis.size());
    }

    public static void executeObject(Pipeline pipeline, List<RedisObjectVO> keys) {
        if (pipeline == null || keys == null || keys.isEmpty()) {
            return;
        }
        long beginToRedis = System.nanoTime();
        List<Response<String>> rs = new ArrayList<>();
        for (RedisObjectVO key : keys) {
            rs.add(pipeline.set(key.getKey(), key.getValue()));
        }
        try {
            pipeline.sync();
            JedisClusterSupport.pipelineToJedis.remove(pipeline).close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("end Time size: " + keys.size() + " ellapse: " + (System.nanoTime() - beginToRedis) / 1_000_000 + "ms");
        ImportToRedis.toRedis.remove(JedisClusterCRC16.getCRC16(keys.get(0).getKey()));
    }

    public static Pipeline getPipeline(JedisClusterSupport cluster, Integer slotId) {
        return cluster.pipeline(slotId);
    }
}
