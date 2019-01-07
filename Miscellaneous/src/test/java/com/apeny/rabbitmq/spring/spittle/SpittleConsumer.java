package com.apeny.rabbitmq.spring.spittle;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by monis on 2018/12/23.
 */
public class SpittleConsumer implements MessageListener{

    @Override
    public void onMessage(Message message) {
        try {
            String body = new String(message.getBody(), "utf-8");
            System.out.println("receive message : " + body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
