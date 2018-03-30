package com.apeny.redisops;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * Created by apeny on 2018/3/30.
 */
public class TestJedisClients {
    public static void main(String[] args) {

    }

    @Test
    public void jedisTest() {
        Jedis jedis = null;
        Jedis jedis1 = null;
        Socket socket = new Socket();
        int i = 0;
        try {
            while (i <= 4061) {
                jedis = new Jedis("192.168.0.128", 30001, 1000, 2000);
                jedis.clusterSlots();
                String info = jedis.info();
                int begin = info.indexOf("connected_clients");
                i++;
                System.out.println(info.substring(begin, begin + 50) + " begin:  >>>>>>>>" + i);
//                jedis1 = new Jedis("192.168.0.128", 30001);
//                jedis1.clusterSlots();
//                String info1 = jedis1.info();
//                int begin1 = info1.indexOf("connected_clients");
//                i++;
//                System.out.println(info1.substring(begin1, begin1 + 50) + " i>>>>>>>>" + i);
////                TimeUnit.MILLISECONDS.sleep(10);
            }
            socket.connect(new InetSocketAddress("192.168.0.128", 30001), 1000);
            i = 0;
            while (i < 10) {
                Socket socket1 = new Socket();
                socket1.connect(new InetSocketAddress("192.168.0.128", 30001), 1000);
                i++;
            }
            System.out.println("key8 from 122: " + jedis1.get("key8"));
            System.out.println("titles from 122: " + jedis1.lrange("titles", 0, -1));
            JSONObject jsonObject = JSONObject.parseObject("{\"userName\":\"aa\", \"age\": 23}");
        } catch (Exception ex) {
            System.out.println("max i: " + i);
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
}
