package com.apeny.rabbitmq.spring.spittle.mismatchconverter;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by monis on 2018/12/23.
 */
public class SpitterMismatchConsumerMain {
    public static void main(String[] args) {
        mismatchConsumer();
    }

    private static void mismatchConsumer() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/rabbitmq/applicationContext-mq-consumermismatch.xml");
    }
}