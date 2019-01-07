package com.apeny.rabbitmq.directqueue;

import com.apeny.rabbitmq.MQConnectionFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class RoutingProducer {
    public static void main(String[] args) {
        produceThreeWorkQueue();
    }

    private static void produceThreeWorkQueue() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String routingKey1 = "qu_direct_orange";
        String routingKey2 = "qu_direct_green";
        String routingKey3 = "qu_direct_black";
        String queueName1 = "qu_direct_q1";
        String queueName2 = "qu_direct_q2";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName1, true, false, false, null);
            channel.queueBind(queueName1, exchange, routingKey1);
            channel.queueDeclare(queueName2, true, false, false, null);
            channel.queueBind(queueName2, exchange, routingKey2);
            channel.queueBind(queueName2, exchange, routingKey3);
            String time = new Date().toString();
            for (int i = 0; i < 100; i++) {
                if (i % 3 == 0) {
                    channel.basicPublish(exchange, routingKey1, null, (time + "orange message producer[2]" + i).getBytes());
                } else if (i % 3 == 1) {
                    channel.basicPublish(exchange, routingKey2, null, (time + "green message producer[2]" + i).getBytes());
                } else {
                    channel.basicPublish(exchange, routingKey3, null, (time + "black message producer[2]" + i).getBytes());
                }
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
