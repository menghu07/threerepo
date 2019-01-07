package com.apeny.rabbitmq.spring.spittle;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 无论这里抛出什么异常都会消费掉消息
 * Created by monis on 2018/12/23.
 */
public class SpittleConsumer11 implements MessageListener {

    @Override
    public void onMessage(Message message) {
//        String body = new String(message.getBody(), "utf-8");
//            System.out.println(Thread.currentThread() + "consumer11, receive message : " + body + ", messageProperties: " + message.getMessageProperties());
        throw new AmqpRejectAndDontRequeueException("不再入队异常");

    }
}
