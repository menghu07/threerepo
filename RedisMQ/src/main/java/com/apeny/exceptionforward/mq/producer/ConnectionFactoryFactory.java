package com.apeny.exceptionforward.mq.producer;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
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
public class ConnectionFactoryFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Value("${rabbit.hosts}")
    private String hostname;

    @Value("${rabbit.port}")
    private int port;

    @Value("${rabbit.username}")
    private String username;

    @Value("${rabbit.password}")
    private String password;

    public synchronized CachingConnectionFactory getConnectionFactory(String factoryName, String virtualHost) {
        if (applicationContext.containsBeanDefinition(factoryName)) {
            return applicationContext.getBean(factoryName, CachingConnectionFactory.class);
        }
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(CachingConnectionFactory.class);
        beanDefinitionBuilder.addConstructorArgValue(hostname);
        beanDefinitionBuilder.addConstructorArgValue(port);
        beanDefinitionBuilder.addPropertyValue("username", username);
        beanDefinitionBuilder.addPropertyValue("password", password);
        beanDefinitionBuilder.addPropertyValue("virtualHost", virtualHost);
        ((DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory()).registerBeanDefinition(factoryName,
                beanDefinitionBuilder.getBeanDefinition());
        return applicationContext.getBean(factoryName, CachingConnectionFactory.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}