package com.apeny.servletjsp.applicationstarter;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by monis on 2019/1/22.
 */
public class StartApplicationMain {
    public static void main(String[] args) {
        startApplication();
    }

    private static void startApplication() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("config/applicationContext.xml");
        applicationContext.start();
    }
}
