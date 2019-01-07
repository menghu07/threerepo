package com.apeny.exceptionforward.domain;

/**
 *
 * @author monis
 * @date 2019/1/5
 */
public class ExceptionForwardRecord {

    private String systemNo;

    private String systemTime;

    private String virtualHost;

    private String exchangeName;

    private String queueName;

    private String routingKey;

    private String message;

    private String sourceSystemNo;

    private String sourceTxType;

    private String sourceBizType;

    private int status;

    private int processTimes;

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public String getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(String systemTime) {
        this.systemTime = systemTime;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSourceSystemNo() {
        return sourceSystemNo;
    }

    public void setSourceSystemNo(String sourceSystemNo) {
        this.sourceSystemNo = sourceSystemNo;
    }

    public String getSourceTxType() {
        return sourceTxType;
    }

    public void setSourceTxType(String sourceTxType) {
        this.sourceTxType = sourceTxType;
    }

    public String getSourceBizType() {
        return sourceBizType;
    }

    public void setSourceBizType(String sourceBizType) {
        this.sourceBizType = sourceBizType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProcessTimes() {
        return processTimes;
    }

    public void setProcessTimes(int processTimes) {
        this.processTimes = processTimes;
    }
}