package com.apeny.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class OneProducer {
    public static void main(String[] args) {
        produceOne();
//        produceTwo();
    }

    private static void produceOne() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_one";
        String routingKey = "key1";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclarePassive(queueName);
//            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey, null, ("from producer[1]" + i ).getBytes());
            }
            System.out.printf("rabbitmq push data has bean completed");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void produceTwo() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_two";
        String routingKey = "key2";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            for (int i = 0; i < 100; i++) {
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
        //TODO channel是什么时候关闭的 Test就会关闭channel
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
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey, null, ("ha ha ha from producer" + i + bigMessage).getBytes());
            }
            System.out.println("rabbitmq push data has bean completed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 有exchange没有路由到的queue
     */
    @Test
    public void testNoQueue() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String routingKey = "nohavequeue";
        try {
            Channel channel = connection.createChannel();
            StringBuilder bigMessage = new StringBuilder();
            for (int i = 0; i < 1000; i++) {
                bigMessage.append("iiiii" + i);
            }
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey, null, ("ha ha ha from producer" + i + bigMessage).getBytes());
                TimeUnit.SECONDS.sleep(1);
            }
            System.out.println("rabbitmq push data has bean completed");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void timeWait() {
        try {
            TimeUnit.SECONDS.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
