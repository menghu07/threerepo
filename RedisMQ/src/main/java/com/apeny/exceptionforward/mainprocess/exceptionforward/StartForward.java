package com.apeny.exceptionforward.mainprocess.exceptionforward;

import com.apeny.exceptionforward.service.ExceptionForwardService;
import com.apeny.system.SystemEnvironment;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
public class StartForward {

    public static void main(String[] args) {
        startApplication();
//        insertOne();
//        findOne();
//        createProducer();
        sendOneMessage();
    }

    private static void insertOne() {
        ExceptionForwardService exceptionForwardService = SystemEnvironment.applicationContext.getBean("exceptionForwardService", ExceptionForwardService.class);
        exceptionForwardService.insertOne();
    }


    private static void findOne() {
        ExceptionForwardService exceptionForwardService = SystemEnvironment.applicationContext.getBean("exceptionForwardService", ExceptionForwardService.class);
//        exceptionForwardService.findOne("20190105201641000000000000000006");
    }

    private static void createProducer() {
        ExceptionForwardService exceptionForwardService = SystemEnvironment.applicationContext.getBean("exceptionForwardService", ExceptionForwardService.class);
        System.out.println("rabbitTemplate: " + exceptionForwardService.createProducerOne());
    }

    private static void sendOneMessage() {
        ExceptionForwardService exceptionForwardService = SystemEnvironment.applicationContext.getBean("exceptionForwardService", ExceptionForwardService.class);
        exceptionForwardService.sendOneMessage("20190105235944000000000000000006");
    }
    private static void startApplication() {
        SystemEnvironment.applicationContext = new ClassPathXmlApplicationContext("config/applicationContext.xml");
    }
}
