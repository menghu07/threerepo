package com.apeny.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一次发送一个消息
 * Created by monis on 2018/12/15.
 */
public class OneProducerMessageOnce {
    public static void main(String[] args) {
    }

    @Test
    public void produceOne() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_one";
        String routingKey = "key1";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            for (int i = 0; i < 1; i++) {
                channel.basicPublish(exchange, routingKey, null, ("from producer[1]" + i ).getBytes());
            }
            System.out.printf("rabbitmq push data has bean completed");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void produceTwo() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_two";
        String routingKey = "key2";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            for (int i = 0; i < 1; i++) {
                channel.basicPublish(exchange, routingKey, null, ("from producer[2]" + i ).getBytes());
            }
            System.out.printf("rabbitmq push data has bean completed");
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

    }


    /**
     * queue auto delete(没有连接，并且没有未处理的消息)
     */
    @Test
    public void produceAutoDelete() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_auto_delete";
        String routingKey = "key1";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, true, null);
            channel.queueBind(queueName, exchange, routingKey);
            StringBuilder bigMessage = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                bigMessage.append("iiiii" + i);
            }
            for (int i = 0; i < 10000; i++) {
                channel.basicPublish(exchange, routingKey, null, ("ha ha ha from producer" + i + bigMessage).getBytes());
            }
            System.out.println("rabbitmq push data has bean completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
