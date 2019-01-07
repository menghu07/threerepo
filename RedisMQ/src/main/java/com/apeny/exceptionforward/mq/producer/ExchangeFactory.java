package com.apeny.exceptionforward.mq.producer;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author apeny
 */
@Component
public class ExchangeFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public synchronized DirectExchange getDirectExchange(String exchangeName) {
        if (applicationContext.containsBeanDefinition(exchangeName)) {
            return applicationContext.getBean(exchangeName, DirectExchange.class);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DirectExchange.class);
        beanDefinitionBuilder.addConstructorArgValue(exchangeName);
        ((DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory()).
                registerBeanDefinition(exchangeName, beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(exchangeName, DirectExchange.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}