package com.apeny.rabbitmq.spring.spittle;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by monis on 2018/12/23.
 */
public class ManualSpitterProducer {
    public static void main(String[] args) {
        consumerAndProducer();
    }

    private static void consumerAndProducer() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("192.168.0.128", 5672);
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");
        connectionFactory.setVirtualHost("/");
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        //创建队列
        Queue queue = new Queue("manual.queue.1");
        rabbitAdmin.declareQueue(queue);
        //创建topic类型交换机
        TopicExchange topicExchange = new TopicExchange("manual.exchange.topic");
        rabbitAdmin.declareExchange(topicExchange);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(topicExchange).with("nole.*"));
        //设置监听器
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        Object listener = new Object() {
            public void handleMessage(Spittle nole) {
                System.out.println("i receive a hole: " + JSON.toJSONString(nole));
            }
        };
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(listener);
        container.setMessageListener(messageListenerAdapter);
        container.setQueueNames("manual.queue.1");
        container.start();
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        Spittle spittle = new Spittle();
        spittle.setId(1);
        spittle.setMessage("hilze");
        spittle.setSpitter("hile");
        spittle.setPostDate(new Date());
        template.convertAndSend("manual.exchange.topic", "nole.hix", spittle);
        try {
            TimeUnit.SECONDS.sleep(900);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            container.stop();
        }
    }
}
