package com.apeny.rabbitmq.spring.spittle;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by monis on 2018/12/23.
 */
public class SpittleProducer {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/rabbitmq/applicationContext-mq-producer1.xml");
        RabbitTemplate rabbitTemplate = applicationContext.getBean("rabbitTemplate", RabbitTemplate.class);
        for (int i = 0; i < 1; i++) {
            System.out.println("sent message: #" + i);
            Spittle spittle = new Spittle();
            spittle.setId(i);
            spittle.setSpitter("loak hile");
            spittle.setMessage("na li, ya na li");
            spittle.setPostDate(new Date());
            rabbitTemplate.convertAndSend("spittle.key1", spittle);
//            rabbitTemplate.convertAndSend("spittle.key2", spittle);
            rabbitTemplate.convertAndSend("spittle.key3", spittle);
        }
//        ((ClassPathXmlApplicationContext) applicationContext).close();
    }
}