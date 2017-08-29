package com.apeny.redisops;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by ahu on 2017年08月20日.
 */
public class RedisClientTest {

    @Test
    public void jedisTest() {

        Jedis jedis = null;
        Jedis jedis1 = null;
        try {
            jedis = new Jedis("192.168.56.121", 10009);
            System.out.println("key4 " + jedis.get("key4"));
            System.out.println("set key2 val1 " + jedis.set("key2", "val1"));
            //String
            System.out.println("string set: " + jedis.set("key1", "val1"));
            //List
            System.out.println("list lpush: " + jedis.lpush("lkey3", "val1", "val2", "val3"));
            //Hash
            System.out.println("hash hset: " + jedis.hset("hkey3", "field1", "val1"));
            //Set
            System.out.println("set sadd: " + jedis.sadd("skey5", "aa", "bb", "cc"));
            //ZSet
            HashMap<String, Double> zmembers = new HashMap<>();
            zmembers.put("mem1", 10d);
            zmembers.put("mem2", 23d);
            zmembers.put("mem3", 13d);
            System.out.println("zset zadd: " + jedis.zadd("zset2", zmembers));
            jedis1 = new Jedis("192.168.56.122", 10020);
            System.out.println("key8 from 122: " + jedis1.get("key8"));
            System.out.println("titles from 122: " + jedis1.lrange("titles", 0, -1));
            JSONObject jsonObject = JSONObject.parseObject("{\"userName\":\"aa\", \"age\": 23}");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            if (jedis1 != null) {
                jedis1.close();
            }
        }
    }

    @Test
    public void jedisPoolTest() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "192.168.56.121", 10009);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            System.out.println("key4 resource: " + jedis.get("key4"));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Test
    public void pipelineTest() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "192.168.56.121", 10009);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Pipeline pipeline = jedis.pipelined();
            pipeline.set("key6", "you in pipeline");
            pipeline.del("one", "two");
            pipeline.incr("counter1");
            List<Object> responses = pipeline.syncAndReturnAll();
            System.out.println("responses: " + responses);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Test
    public void redisLuaTest() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "192.168.56.121", 10009);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Object r = jedis.eval("return redis.call('get', 'key4')");
            System.out.println("eval r: " + r);
            String sha1 = jedis.scriptLoad("return redis.call('get', 'key5')");
            Object shar = jedis.evalsha(sha1);
            System.out.println("shar result: " + shar);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Test
    public void testBatchInsert() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        JedisPool jedisPool = new JedisPool(config, "192.168.56.121", 10009, 300000);
        Jedis jedis = null;
        Jedis jedis2 = null;
        int count = 500890000;
        try {
            jedis = jedisPool.getResource();
            jedis2 = jedisPool.getResource();
            System.out.println("jedis begin");
            int pre = new Random().nextInt(500000000);
            for (int i = 0; i < 1; i++) {
                try {
                    jedis.set(pre + "key" + i, "value：" + i + "zade:" + i % 1000);
                } catch (Exception ex) {
                    new Exception("jedis" + jedis, ex).printStackTrace();
                    System.out.println("key: value: " + jedis.getClient());
                }
                try {
                    jedis2.set(pre + "key" + i, "value：" + i + "zade:" + i % 1000);
                    jedis2.keys("*");
                } catch (Exception ex) {
                    new Exception("jedis2" + jedis2, ex).printStackTrace();
                    System.out.println("key: value: " + jedis2.getClient());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (jedis != null) {
//                jedis.close();
                System.out.println("old jedis: " + jedis);
            }
        }
    }
}
