package com.apeny.rabbitmq.spring.spittle.mismatchconverter;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/12/23.
 */
public class SpittleMismatchConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        String body = null;
        try {
            body = new String(message.getBody(), "utf-8");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread() + "consumer11, receive message : " + body + ", messageProperties: " + message.getMessageProperties());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
