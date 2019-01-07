package com.apeny.exceptionforward.dao;

import com.apeny.exceptionforward.domain.ExceptionForwardRecord;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author monis
 * @date 2019/1/5
 */
@Repository
public class ExceptionForwardRecordDAO {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void insert(ExceptionForwardRecord entity) {
        String sql = "INSERT INTO ExceptionForwardRecord(SystemNo, SystemTime, VirtualHost, ExchangeName, QueueName, RoutingKey, Message,"
                + " SourceSystemNo, SourceTxType, SourceBizType, Status, ProcessTimes)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, entity.getSystemNo(), entity.getSystemTime(), entity.getVirtualHost(),
                entity.getExchangeName(), entity.getQueueName(), entity.getRoutingKey(), entity.getMessage(), entity.getSourceSystemNo(),
                entity.getSourceTxType(), entity.getSourceBizType(), entity.getStatus(), entity.getProcessTimes());
    }
}
