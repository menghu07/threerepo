package com.apeny.servletjsp.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by apeny on 2018年02月05日.
 */
public class RedisStandaloneTest {

    @Test
    public void testAccess140() {
        Jedis jedis = new Jedis("192.168.0.128", 6379);
        System.out.println("ping result: " + jedis.ping());
    }
}
