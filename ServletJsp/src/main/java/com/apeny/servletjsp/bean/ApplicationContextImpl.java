package com.apeny.servletjsp.bean;

import com.apeny.servletjsp.bean.shardingsphere.TxTimePreciseShardingAlgorithm;
import com.apeny.servletjsp.bean.shardingsphere.TxTimeRangeShardingAlgorithm;
import com.apeny.servletjsp.bean.shardingsphere.targetimpl.*;
import com.apeny.servletjsp.redis.ImportToRedis;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

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
		config.setMaxWaitMillis(10000);
		config.setMaxIdle(20);
		config.setMaxTotal(5000);
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

	@Bean
	public JedisClusterSupport jedisClusterSupport() {
		HostAndPort host1 = new HostAndPort("192.168.117.235", 6380);
        Set<HostAndPort> hosts = new HashSet<>();
        hosts.add(host1);
		return new JedisClusterSupport(hosts, 2000, 20000, 3, jedisPoolConfig());
	}

	@Bean
	public ImportToRedis importToRedis(JedisClusterSupport support) {
        return new ImportToRedis(support);
	}

	@Bean
    public PreciseShardingAlgorithm txTimePreciseShardingAlgorithm() {
        return new TxTimePreciseShardingAlgorithm();
    }

    @Bean
    public TxTimeRangeShardingAlgorithm txTimeRangeShardingAlgorithm() {
	    return new TxTimeRangeShardingAlgorithm();
    }

    @Bean
    public MasterFieldPreciseShardingAlgorithm masterFieldPreciseShardingAlgorithm() {
	    return new MasterFieldPreciseShardingAlgorithm();
    }

    @Bean
    public MasterFieldRangeShardingAlgorithm masterFieldRangeShardingAlgorithm() {
	    return new MasterFieldRangeShardingAlgorithm();
    }

    @Bean
    public NeighborPreciseShardingAlgorithm neighborPreciseShardingAlgorithm() {
	    return new NeighborPreciseShardingAlgorithm();
    }

    @Bean
    public NeighborRangeShardingAlgorithm neighborRangeShardingAlgorithm() {
	    return new NeighborRangeShardingAlgorithm();
    }

    @Bean
    public BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm6() {
	    return new BeforePeriodPreciseShardingAlgorithm(6);
    }

    @Bean
    public BeforePeriodPreciseShardingAlgorithm beforePeriodPreciseShardingAlgorithm12() {
	    return new BeforePeriodPreciseShardingAlgorithm();
    }

    @Bean
    public BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm6() {
	    return new BeforePeriodRangeShardingAlgorithm(6);
    }

    @Bean
    public BeforePeriodRangeShardingAlgorithm beforePeriodRangeShardingAlgorithm12() {
	    return new BeforePeriodRangeShardingAlgorithm();
    }
}
