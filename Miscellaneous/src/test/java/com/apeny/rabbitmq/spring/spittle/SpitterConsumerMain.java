package com.apeny.rabbitmq.spring.spittle;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by monis on 2018/12/23.
 */
public class SpitterConsumerMain {
    public static void main(String[] args) {
        DOMConfigurator.configure("src/main/resources/config/log4j.xml");
        consumer11();
    }

    private static void consumer1() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/rabbitmq/applicationContext-mq-consumer1.xml");
    }

    private static void consumer11() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/rabbitmq/applicationContext-mq-consumer11.xml");
    }
}