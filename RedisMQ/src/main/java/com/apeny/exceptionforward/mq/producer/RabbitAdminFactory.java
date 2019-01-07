package com.apeny.exceptionforward.mq.producer;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
@Component
public class RabbitAdminFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public synchronized RabbitAdmin getRabbitAdmin(String rabbitAdmin, String connectionFactoryName) {
        if (applicationContext.containsBeanDefinition(rabbitAdmin)) {
            return applicationContext.getBean(rabbitAdmin, RabbitAdmin.class);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(RabbitAdmin.class);
        beanDefinitionBuilder.addConstructorArgReference(connectionFactoryName);
        ((DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory()).
                registerBeanDefinition(rabbitAdmin, beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(rabbitAdmin, RabbitAdmin.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
