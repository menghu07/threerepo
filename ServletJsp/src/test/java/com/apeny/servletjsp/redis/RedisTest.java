package com.apeny.servletjsp.redis;

import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import redis.clients.jedis.JedisCluster;

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
}
