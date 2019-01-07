package com.apeny.exceptionforward.mq.producer;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
@Component
public class QueueFactory implements ApplicationContextAware{

    private ApplicationContext applicationContext;

    public synchronized Queue getQueue(String queueName) {
        if (applicationContext.containsBeanDefinition(queueName)) {
            return applicationContext.getBean(queueName, Queue.class);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Queue.class);
        beanDefinitionBuilder.addConstructorArgValue(queueName);
        ((DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory()).registerBeanDefinition(queueName,
                beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(queueName, Queue.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
