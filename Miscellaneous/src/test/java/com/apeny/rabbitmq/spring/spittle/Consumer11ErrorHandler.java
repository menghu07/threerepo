package com.apeny.rabbitmq.spring.spittle;

import org.springframework.util.ErrorHandler;

/**
 * Created by monis on 2018/12/30.
 */
public class Consumer11ErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        System.out.println(" i account exception: " + t.toString());
    }
}
