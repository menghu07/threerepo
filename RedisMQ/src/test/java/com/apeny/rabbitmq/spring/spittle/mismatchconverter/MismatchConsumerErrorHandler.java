package com.apeny.rabbitmq.spring.spittle.mismatchconverter;

import org.springframework.util.ErrorHandler;

/**
 * Created by monis on 2018/12/30.
 */
public class MismatchConsumerErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        System.out.println(" i account exception: " + t.toString());
    }
}
