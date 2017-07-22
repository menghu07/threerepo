package com.apeny.servletjsp.redis;

import com.apeny.servletjsp.bean.JedisClusterSupport;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.util.JedisClusterCRC16;

import java.util.ArrayList;
import java.util.List;

public class RedisTest {

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
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(
                "src/main/resources/config/applicationContext.xml");
        JedisClusterSupport cluster = (JedisClusterSupport) context.getBean("jedisClusterSupport");
        System.out.println(cluster);
        Pipeline pipeline = cluster.pipeline(JedisClusterCRC16.getSlot("Risk:InstTxWhiteList:000020:20:11:6321423000000003503293"));
        List<Response<String>> responses = new ArrayList<>();
        System.out.println("begin loop " + System.nanoTime() + "ns");
        for (int i = 0; i < 500000; i++) {
            Response<String> response = pipeline.set("Risk:InstTxWhiteList:000020:20:11:6321423000000003503292", "ans");
            responses.add(response);
        }
        System.out.println("begin sync " + System.nanoTime() + "ns");
        pipeline.sync();
        System.out.println("end sync" + System.nanoTime() + "ns");
        for (Response<String> r : responses) {
            r.get();
        }
        System.out.println("read end"+ System.nanoTime() + "ns");

    }
}
