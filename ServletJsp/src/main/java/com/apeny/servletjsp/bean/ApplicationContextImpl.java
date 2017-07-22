package com.apeny.servletjsp.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class ApplicationContextImpl implements ApplicationContextAware {
	
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public ApplicationContext getApplicationContext() {
	    return context;
    }

	@Bean
	public JedisPoolConfig jedisPoolConfig() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setBlockWhenExhausted(true);
		config.setMaxWaitMillis(3000);
		config.setMaxIdle(20);
		config.setMaxTotal(30);
		config.setTestOnBorrow(true);
		config.setTestOnCreate(true);
		config.setTestOnReturn(true);
		//是否测试空闲的连接是无效并可以清除它
		config.setTestWhileIdle(true);
		//空闲十分钟才可以被清除
		config.setMinEvictableIdleTimeMillis(600000);
		//5分钟检查一次失效连接
		config.setTimeBetweenEvictionRunsMillis(300000);
		//每次扫描空闲连接数量
		config.setNumTestsPerEvictionRun(-1);
		return config;
	}
	
	@Bean
	public JedisCluster jedisCluster() {
		HostAndPort host1 = new HostAndPort("192.168.56.121", 10001);
		return new JedisCluster(host1, jedisPoolConfig());
	}
}
