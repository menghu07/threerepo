package com.apeny.jvmpractice.version16;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 1.6版本和1.8版本ConcurrentHashMap不同
 * Created by apeny on 2017/12/22.
 */
public class ConcurrentHashMap16 {
    public static void main(String[] args) {

    }

    /**
     * 声明为ConcurrentHashMap会有问题
     */
    public static void testConcurrentHashMap() {
        Map<String, String> map = new ConcurrentHashMap<String, String>();
        Set<String> set = map.keySet();
    }
}
