package com.apeny.exceptionforward.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.apeny.exceptionforward.domain.User;

/**
 * @author monis
 * @date 2019/1/6
 */
public class ForwardHandleMessage {

    public void handleMessage(Object message) {
        if (message instanceof String) {
            System.out.println("i want to see, whether i can receive message" + message);
            User user = JSON.parseObject(message.toString(), User.class);
            System.out.println("ha ha ha : success has User: " + JSON.toJSONString(user) + ", user object: " + user);
        } else {
            System.out.println("handle as pojo" + JSON.toJSONString(message));
        }
    }
}
