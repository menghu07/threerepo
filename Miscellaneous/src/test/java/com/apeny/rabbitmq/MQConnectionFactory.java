package com.apeny.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by monis on 2018/12/15.
 */
public class MQConnectionFactory {
    private static final String HOST_NAME = "192.168.0.128";
    private static final int PORT = 5672;
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";
    private static final String VIRTUAL_HOST = "/";

    private static Connection connection;

    /**
     * 创建MQ连接
     * @return
     */
    public static synchronized Connection getConnection() {
        if (connection == null) {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(HOST_NAME);
            connectionFactory.setPort(PORT);
            connectionFactory.setUsername(USER_NAME);
            connectionFactory.setPassword(PASSWORD);
            try {
                connection = connectionFactory.newConnection();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
