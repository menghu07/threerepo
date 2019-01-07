package com.apeny.rabbitmq.directqueue;

import com.apeny.rabbitmq.MQConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class RoutingAutoDeleteProducer {
    private static int i = 0;

    public static void main(String[] args) {
        produceThreeWorkQueue();
    }

    private static void produceThreeWorkQueue() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String routingKey1 = "qu_direct_autodelete_key";
        String queueName1 = "qu_direct_autodelete";
        try {
            Channel channel = connection.createChannel();
            channel.queueDeclare(queueName1, true, false, true, null);
            channel.queueBind(queueName1, exchange, routingKey1);
            String time = new Date().toString();
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey1, null, (time + "ddddauto delete message producer[2]" + i).getBytes());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("push message to mq");
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