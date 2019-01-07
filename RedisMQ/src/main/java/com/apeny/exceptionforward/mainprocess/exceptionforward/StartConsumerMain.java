package com.apeny.exceptionforward.mainprocess.exceptionforward;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;

/**
 * Created by monis on 2019/1/6.
 */
public class StartConsumerMain {
    public static void main(String[] args) {
        startConsumer();
    }

    private static void startConsumer() {
        new ClassPathXmlApplicationContext("config/rabbitmq/exceptionforward/forward-ApplicationContext.xml");
    }
}
