package com.apeny.exceptionforward.service;

import com.alibaba.fastjson.JSON;
import com.apeny.exceptionforward.dao.ExceptionForwardRecordDAO;
import com.apeny.exceptionforward.domain.ExceptionForwardRecord;
import com.apeny.exceptionforward.domain.User;
import com.apeny.exceptionforward.mq.producer.ConnectionFactoryFactory;
import com.apeny.exceptionforward.mq.producer.RabbitAdminFactory;
import com.apeny.exceptionforward.query.ExceptionForwardRecordQuery;
import com.apeny.exceptionforward.util.GUID;
import com.apeny.exceptionforward.util.TimeUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
@Service
public class ExceptionForwardService {

    @Resource(name = "exceptionForwardRecordDAO")
    private ExceptionForwardRecordDAO exceptionForwardRecordDAO;

    @Resource(name = "exceptionForwardRecordQuery")
    private ExceptionForwardRecordQuery exceptionForwardRecordQuery;

    @Autowired
    private ConnectionFactoryFactory connectionFactoryFactory;

    @Autowired
    private RabbitAdminFactory rabbitAdminFactory;

    public void insertOne() {
        ExceptionForwardRecord exceptionForwardRecord = new ExceptionForwardRecord();
        exceptionForwardRecord.setSystemNo(GUID.generate(GUID.L32));
        exceptionForwardRecord.setSystemTime(TimeUtils.getTimestampsss());
        exceptionForwardRecord.setVirtualHost("/");
        exceptionForwardRecord.setExchangeName("exchange.forward.fee");
        exceptionForwardRecord.setQueueName("queue.fee.feesumacount");
        exceptionForwardRecord.setRoutingKey("queuekey.fee.feesumacount");
        exceptionForwardRecord.setMessage(JSON.toJSONString(new User(GUID.generate(GUID.L32), "noname", TimeUtils.getTimestampsss(), "123")));
        exceptionForwardRecord.setSourceSystemNo(GUID.generate(GUID.L32));
        exceptionForwardRecord.setSourceTxType("0000000001");
        exceptionForwardRecord.setSourceBizType("000000");
        exceptionForwardRecord.setStatus(10);
        exceptionForwardRecordDAO.insert(exceptionForwardRecord);
    }

    private ExceptionForwardRecord findOne(String systemNo) {
        ExceptionForwardRecord exceptionForwardRecord = exceptionForwardRecordQuery.findOne(systemNo);
        System.out.println("found exceptionForwardRecord: " + JSON.toJSONString(exceptionForwardRecord));
        return exceptionForwardRecord;
    }

    public void sendOneMessage(String systemNo) {
        ExceptionForwardRecord forwardRecord = findOne(systemNo);
        String virtualHost = forwardRecord.getVirtualHost();
        connectionFactoryFactory.getConnectionFactory(virtualHost, virtualHost);
        RabbitAdmin rabbitAdmin = rabbitAdminFactory.getRabbitAdmin(virtualHost + "RabbitAdmin", virtualHost);
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        //发送消息
        rabbitTemplate.convertAndSend(forwardRecord.getExchangeName(), forwardRecord.getRoutingKey(), forwardRecord.getMessage());
    }

    public RabbitTemplate createProducerOne() {
        CachingConnectionFactory connectionFactory = connectionFactoryFactory.getConnectionFactory("/", "/");
        RabbitAdmin rabbitAdmin = rabbitAdminFactory.getRabbitAdmin("commonRabbitAdmin", "/");
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();
        //发送消息
        System.out.println("connectionFactory: " + connectionFactory);
        return rabbitTemplate;
    }
}