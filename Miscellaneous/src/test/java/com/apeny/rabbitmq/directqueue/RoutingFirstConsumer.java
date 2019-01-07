package com.apeny.rabbitmq.directqueue;

import com.apeny.rabbitmq.MQConnectionFactory;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/12/15.
 */
public class RoutingFirstConsumer {
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
            Channel channel2 = connection.createChannel();
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_q1";
            String routingKey =  "qu_direct_orange";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            channel.basicQos(1);
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
            channel2.basicConsume(queueName, false, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void process(String message) {
        System.out.println("No." + i++ + ", first consumer process message: " + message);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
