package com.apeny.exceptionforward.query;

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
public class ExceptionForwardRecordQuery {

    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public ExceptionForwardRecord findOne(String systemNo) {
        String sql = "SELECT * FROM ExceptionForwardRecord WHERE SystemNo = ?";
        return jdbcTemplate.query(sql, new Object[]{systemNo}, new ResultSetExtractor<ExceptionForwardRecord>() {
            @Nullable
            @Override
            public ExceptionForwardRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
                ExceptionForwardRecord exceptionForwardRecord = new ExceptionForwardRecord();
                if (rs.next()) {
                    exceptionForwardRecord.setSystemNo(rs.getString("SYSTEMNO"));
                    exceptionForwardRecord.setSystemTime(rs.getString("SYSTEMTIME"));
                    exceptionForwardRecord.setVirtualHost(rs.getString("VIRTUALHOST"));
                    exceptionForwardRecord.setExchangeName(rs.getString("EXCHANGENAME"));
                    exceptionForwardRecord.setQueueName(rs.getString("ExchangeName"));
                    exceptionForwardRecord.setRoutingKey(rs.getString("ROUTINGKEY"));
                    exceptionForwardRecord.setMessage(rs.getString("MESSAGE"));
                    exceptionForwardRecord.setSourceSystemNo(rs.getString("SOURCESYSTEMNO"));
                    exceptionForwardRecord.setSourceTxType(rs.getString("SOURCETXTYPE"));
                    exceptionForwardRecord.setSourceBizType(rs.getString("SOURCEBIZTYPE"));
                    exceptionForwardRecord.setStatus(rs.getInt("STATUS"));
                    exceptionForwardRecord.setProcessTimes(rs.getInt("PROCESSTIMES"));
                } else {
                    exceptionForwardRecord = null;
                }
                return exceptionForwardRecord;
            }
        });
    }
}
