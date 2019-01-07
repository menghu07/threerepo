package com.apeny.rabbitmq.spring.spittle;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by monis on 2018/12/30.
 */
public class MQRejectAndLogRecoverer extends RejectAndDontRequeueRecoverer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "jsonMessageConverter")
    private MessageConverter msgConverter;

    @Override
    public void recover(Message message, Throwable cause) {
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        headers.put("x-exception-stacktrace", getStackTraceAsString(cause));
        headers.put("x-exception-message", cause.getCause() != null ? cause.getCause().getMessage() : cause.getMessage());
        headers.put("x-original-exchange", message.getMessageProperties().getReceivedExchange());
        headers.put("x-original-routingKey", message.getMessageProperties().getReceivedRoutingKey());
//        this.rabbitTemplate.send(message.getMessageProperties().getReceivedExchange(), message.getMessageProperties().getReceivedRoutingKey(), message);
        logger.error("handler msg (" + msgConverter.fromMessage(message) + ") err, republish to mq.", cause);
        System.out.println("recover do nothing, only print: " + cause);
    }

    private String getStackTraceAsString(Throwable cause) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        cause.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }

    public RabbitTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public MessageConverter getMsgConverter() {
        return msgConverter;
    }

    public void setMsgConverter(MessageConverter msgConverter) {
        this.msgConverter = msgConverter;
    }
}
