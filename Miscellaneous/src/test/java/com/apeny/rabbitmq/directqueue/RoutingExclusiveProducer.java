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
public class RoutingExclusiveProducer {
    private static int i = 0;

    public static void main(String[] args) {
        produceThreeWorkQueue();
    }

    private static void produceThreeWorkQueue() {
        Connection connection = MQConnectionFactory.getConnection();
        String exchange = "main_ex_direct";
        String routingKey1 = "qu_direct_exclusive_key";
        String queueName1 = "qu_direct_exclusive";
        try {
            Channel channel = connection.createChannel();
            //exclusive=true，只有此连接可以使用这个queue，在连接关闭时删除queue
            channel.queueDeclare(queueName1, true, true, false, null);
            channel.queueBind(queueName1, exchange, routingKey1);
            String time = new Date().toString();
            for (int i = 0; i < 100; i++) {
                channel.basicPublish(exchange, routingKey1, null, (time + "exclusive message producer[2]" + i).getBytes());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.printf("rabbitmq push data has bean completed");
            //exclusive本连接自己消费
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
            channel.basicConsume(queueName1, false, consumer);
            TimeUnit.SECONDS.sleep(10000);
            channel.close();
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
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
