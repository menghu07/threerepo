package com.apeny.rabbitmq;

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
public class OneConsumer {
    static int i = 0;

    public static void main(String[] args) {
//        consumeOneAutoDelete();
        consumeOne();
//        consumeOneReject();
    }

    /**
     * direct exchange
     */
    private static void consumeOne() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_one";
            String routingKey = "key1";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            StringBuilder messages = new StringBuilder();
//            channel.basicQos(1);
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
                        TimeUnit.MICROSECONDS.sleep(1000000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println(envelope.getDeliveryTag() + "has message: " + new String(body));
                }
            };
            channel.basicConsume(queueName, false, consumer);
            System.out.printf("last messages:ffff " + messages);
//            channel.close();
//            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * direct exchange
     * 有connection但没有channel(即consumer)，测试时未发现自动关闭channel
     */
    public static void consumeOneAutoDelete() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String queueName = "qu_direct_auto_delete";
            StringBuilder messages = new StringBuilder();
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties,
                                           byte[] body)
                        throws IOException {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println("consumerTag" + consumerTag + "delivery tag: " + envelope.getDeliveryTag() + "has message: " + new String(body));
                }
            };
            channel.basicConsume(queueName, false, consumer);
            System.out.println("last messages:ffff " + messages);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * direct exchange
     * 拒绝消息
     */
    @Test
    public void consumeOneReject() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_one";
            String routingKey = "key1";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            StringBuilder messages = new StringBuilder();
            Set<String> rejected = new HashSet<>();
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
//                    try {
//                        TimeUnit.MICROSECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String mess = new String(body);
                    //如果没拒绝过，拒绝
                    if (rejected.add(mess)) {
                        //收到但拒绝收到的消息
                        System.out.println("rejected size: " + rejected.size() + ";consumerTag" + consumerTag + ";delivery tag: " + envelope.getDeliveryTag() + ";has message: " + new String(body));
                        channel.basicReject(envelope.getDeliveryTag(), true);
                    } else {
                        System.out.println("consumerTag" + consumerTag + ";delivery tag: " + envelope.getDeliveryTag() + ";has message: " + new String(body));
                        //确认收到消息
                        channel.basicAck(envelope.getDeliveryTag(), true);
                    }
                }
            };
            channel.basicConsume(queueName, consumer);
            System.out.println("last messages:ffff " + messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * direct exchange
     * 批量确认，确认之前收到的消息
     */
    @Test
    public void consumeAckN() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_one";
            String routingKey = "key1";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            StringBuilder messages = new StringBuilder();
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
                        System.out.printf("No." + i + ";sleep before: " + new Date());
                        TimeUnit.MILLISECONDS.sleep(3000);
                        System.out.printf("No." + i + ";sleep after: " + new Date());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    System.out.println(Thread.currentThread() + ";No. " + i + ";" + new Date() + "; consumerTag" + consumerTag + ";delivery tag: " + envelope.getDeliveryTag() + ";has message: " + new String(body));
                    if (i % 5 == 0) {
                        //确认收到消息
                        channel.basicAck(envelope.getDeliveryTag(), true);
                    }
                    i++;
                }
            };
            channel.basicConsume(queueName, consumer);
            System.out.println("last messages:ffff " + messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * direct exchange
     * 一个channel对应两个queue 消息确认时会乱，按channel编号deliveryTag顺序，多个队列统一编号，确认时也是多个队列同时确认
     */
    @Test
    public void consumeTwoQueueOneChannel() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String exchange = "main_ex_direct";
            String queueName = "qu_direct_one";
            String queueName2 = "qu_direct_two";
            String routingKey = "key1";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchange, routingKey);
            List<Long> queue1Tag = new ArrayList<>();
            List<Long> queue2Tag = new ArrayList<>();
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
                        System.out.printf("No." + i + ";sleep before: " + new Date());
                        TimeUnit.MILLISECONDS.sleep(1000);
                        System.out.println("No." + i + ";sleep after: " + new Date());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    super.handleDelivery(consumerTag, envelope, properties, body);
//                    System.out.println(Thread.currentThread() + ";No. " + i + ";" + new Date() + "; consumerTag" + consumerTag + ";delivery tag: " + envelope.getDeliveryTag() + ";has message: " + new String(body));
                    String mess = new String(body);
                    Long deliveryTag = envelope.getDeliveryTag();
                    if (mess.indexOf("[1]") > 0) {
                        queue1Tag.add(deliveryTag);
                    } else {
                        queue2Tag.add(deliveryTag);
                    }
                    if (i % 5 == 0) {
                        //确认收到消息
                        channel.basicAck(deliveryTag, true);
                        System.out.println("i send a acknowledgement; deliveryTag: " + deliveryTag);
                    }
                    System.out.println("tag1: " + queue1Tag + "; deliveryTag: " + deliveryTag +  "; routingKey:" + envelope.getRoutingKey());
                    System.out.println("tag2: " + queue2Tag + "; deliveryTag: " + deliveryTag + "; routingKey:" + envelope.getRoutingKey());
                    i++;
                }
            };
            channel.basicConsume(queueName, consumer);
            channel.basicConsume(queueName2, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getOneMessage() {
        Connection connection = MQConnectionFactory.getConnection();
        try {
            Channel channel = connection.createChannel();
            String queueName = "qu_direct_one";
            GetResponse response = channel.basicGet(queueName, false);
            System.out.println("get message: " + new String(response.getBody()));
            channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @After
    public void wait2() {
        try {
            System.out.println("sleep five seconds");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void time() {
        try {
            System.out.printf("No." + i + ";sleep before: " + new Date());
            TimeUnit.MICROSECONDS.sleep(3000000);
            System.out.printf("No." + i + ";sleep after: " + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
