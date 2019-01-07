package com.apeny.rabbitmq.workqueue;

import com.apeny.rabbitmq.MQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class WorkQueueProducer {
    public static void main(String[] args) {
        produceThreeWorkQueue();
    }

    private static void produceThreeWorkQueue() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String queueName = "qu_direct_three_workqueue";
        String routingKey = "key2";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            String time = new Date().toString();
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey, null, (time + "from producer[2]" + i ).getBytes());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
}
