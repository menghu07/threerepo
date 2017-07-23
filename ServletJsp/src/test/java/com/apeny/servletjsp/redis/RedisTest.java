package com.apeny.servletjsp.redis;

import com.apeny.servletjsp.bean.JedisClusterSupport;
import com.apeny.servletjsp.vo.RedisObjectVO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.connection.ClusterSlotHashUtil;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.util.JedisClusterCRC16;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public class RedisTest {
    private FileSystemXmlApplicationContext context;
    private JedisClusterSupport cluster;

    @Before
    public void before() {
        context = new FileSystemXmlApplicationContext(
                "src/main/resources/config/applicationContext.xml");
        cluster = (JedisClusterSupport) context.getBean("jedisClusterSupport");
    }

    @Test
    public void testRedisDisconnect() {
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "src/main/resources/config/applicationContext.xml");
        JedisCluster cluster = (JedisCluster) context.getBean("jedisCluster");
        System.out.println(cluster);
        for (int i = 0; i < 10000; i++) {
            System.out.println("{Risk:InstTxWhiteList}:000020:20:11:6321423000000003503293");
        }
    }

    @Test
    public void testRedisPipeline() {

        System.out.println(cluster);
        Map<String, JedisPool> nodesMap = cluster.getClusterNodes();
        for (Map.Entry<String, JedisPool> node : nodesMap.entrySet()) {
            System.out.println("key: " + node.getKey() + "value: " + node.getValue());
        }
        Pipeline pipeline = cluster.pipeline(JedisClusterCRC16.getSlot("Risk:InstTxWhiteList:000020:20:11:6321423000000003503293"));
        List<Response<String>> responses = new ArrayList<>();
        System.out.println("begin loop " + System.nanoTime() + "ns");
        for (int i = 0; i < 100; i++) {
            Response<String> response = pipeline.set("Risk:InstTxWhiteList:000020:20:11:6321423000000003503293", "ans");
            responses.add(response);
        }
        System.out.println("begin sync " + System.nanoTime() + "ns");
        pipeline.sync();
        System.out.println("end sync" + System.nanoTime() + "ns");
        for (Response<String> r : responses) {
            r.get();
        }
        System.out.println("read end" + System.nanoTime() + "ns");
        responses.clear();
        try {
            pipeline.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 100; i++) {
            Response<String> response = pipeline.set("Risk:InstTxWhiteList:000020:20:11:6321423000000003503293", "ans");
            responses.add(response);
        }
        pipeline.sync();
        for (Response<String> r : responses) {
            r.get();
        }
        System.out.println("second read end" + System.nanoTime() + "ns");
    }

    @Test
    public void getAllPipelines() {
        long begin = System.nanoTime();
        System.out.println();

        List<Pipeline> pipelines = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            pipelines.add(cluster.pipeline(JedisClusterCRC16.getSlot("one" + i)));
        }
        int j = 0;
        List<Response<String>> responses = new ArrayList<>();
        for (Pipeline p : pipelines) {
            System.out.println(p + " : " + j);
            try {
                responses.add(p.set("one" + j++, "onevalue"));
                p.sync();
                JedisClusterSupport.pipelineToJedis.remove(p).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for (Response<String> r : responses) {
            r.get();
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pipelines.clear();
        for (int i = 0; i < 30; i++) {
            pipelines.add(cluster.pipeline(JedisClusterCRC16.getSlot("two" + i)));
        }
        j = 0;
        for (Pipeline p : pipelines) {
            System.out.println("second: " + p + " : " + j);
            try {
                p.set("two" + j++,"twovalue");
                p.sync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("import to redis end ellapse : " + (System.nanoTime() - begin));
    }

    @Test
    public void testImportRedis() {
        ImportToRedis importToRedis = (ImportToRedis) context.getBean("importToRedis");
        long begin = System.nanoTime();
        importToRedis.fromDB();
        importToRedis.importToRedis();
        System.out.println("end ellapse: " + (System.nanoTime() - begin) / 1000_000_000 + "s");
        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
