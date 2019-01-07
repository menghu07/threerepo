package com.apeny.rabbitmq.workqueue;

import com.apeny.rabbitmq.MQConnectionFactory;
import com.rabbitmq.client.*;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class WorkQueueSecondConsumer {
    static int i = 0;

    public static void main(String[] args) {
        consumeOne();
    }

    /**
     * direct exchange
     */
    private static void consumeOne() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            channel.basicQos(1);
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_three_workqueue";
            String routingKey = "key2";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            Consumer consumer = new DefaultConsumer(channel) {
                /**
                 * No-op implementation of {@link Consumer#handleDelivery}.
                 */
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    try {
                        process(new String(body));
                    } finally {
                        channel.basicAck(envelope.getDeliveryTag(), false);
                    }
                }
            };
            channel.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void process(String message) {
        System.out.println("No." + i++ + ", first consumer process message: " + message);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
