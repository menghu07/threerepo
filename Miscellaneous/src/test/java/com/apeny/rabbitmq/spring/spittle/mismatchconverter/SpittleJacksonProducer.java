package com.apeny.rabbitmq.spring.spittle.mismatchconverter;

import com.apeny.rabbitmq.spring.spittle.Spittle;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by monis on 2018/12/23.
 */
public class SpittleJacksonProducer {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/rabbitmq/applicationContext-mq-producer1.xml");
        RabbitTemplate rabbitTemplate = applicationContext.getBean("rabbitTemplate", RabbitTemplate.class);
        //两个message 一个30s 一个10s
        for (int i = 0; i < 1; i++) {
            System.out.println("sent message: #" + i);
            Spittle spittle = new Spittle();
            spittle.setId(i);
            spittle.setSpitter("loak hile");
            spittle.setMessage("na li, ya na li" + i);
            spittle.setPostDate(new Date());// spittle.queue.jackson1
            rabbitTemplate.convertAndSend("spittle.queue.jackson1",spittle, mes -> {
                mes.getMessageProperties().setExpiration("30000");
                return mes;
            });
        }
        for (int i = 0; i < 1; i++) {
            System.out.println("sent message: #" + 2);
            Spittle spittle = new Spittle();
            spittle.setId(i);
            spittle.setSpitter("loak hile");
            spittle.setMessage("na li, ya na li" + 2);
            spittle.setPostDate(new Date());// spittle.queue.jackson1
            rabbitTemplate.convertAndSend("spittle.queue.jackson1",
                     spittle, mes -> {
                mes.getMessageProperties().setExpiration("10000");
                return mes;
            });
        }
//        Object message = rabbitTemplate.receiveAndConvert("spittle.queue.jackson1");
//        System.out.println("message: received " + message);
//        ((ClassPathXmlApplicationContext) applicationContext).close();
    }
}
