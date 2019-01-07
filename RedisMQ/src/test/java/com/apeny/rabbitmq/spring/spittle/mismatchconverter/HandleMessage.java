package com.apeny.rabbitmq.spring.spittle.mismatchconverter;

import com.alibaba.fastjson.JSON;

/**
 * Created by monis on 2019/1/5.
 */
public class HandleMessage {

    public void handleMessage(Object message) {
        System.out.println("haha : " + JSON.toJSONString(message));
    }
}
