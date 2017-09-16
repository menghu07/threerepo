package com.apeny.httprpc;

import com.alibaba.fastjson.JSON;

/**
 * Created by ahu on 2017年09月16日.
 */
public class JsonUtil {

    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) {
        return JSON.parseObject(jsonString, clazz);
    }
}
