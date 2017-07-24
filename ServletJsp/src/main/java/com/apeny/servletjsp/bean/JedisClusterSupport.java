package com.apeny.servletjsp.bean;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.*;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ahu on 2017/7/22.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class JedisClusterSupport extends JedisCluster {

    public static final ConcurrentMap<Pipeline, Jedis> pipelineToJedis = new ConcurrentHashMap<>();

    public JedisClusterSupport(Set<HostAndPort> nodes) {
        super(nodes);
    }

    public JedisClusterSupport(Set<HostAndPort> nodes, GenericObjectPoolConfig config) {
        super(nodes, config);
    }

    public JedisClusterSupport(Set<HostAndPort> jedisClusterNode, int connectionTimeout, int soTimeout,
                        int maxAttempts, final GenericObjectPoolConfig poolConfig) {
        super(jedisClusterNode, connectionTimeout, soTimeout, maxAttempts, poolConfig);
    }

    public Pipeline pipeline(int slot) {
        JedisSlotBasedConnectionHandler handler = (JedisSlotBasedConnectionHandler) this.connectionHandler;
        Jedis connection = handler.getConnectionFromSlot(slot);
//        connection.close();
        Pipeline p = connection.pipelined();
        pipelineToJedis.put(p, connection);
        return p;
    }


}
