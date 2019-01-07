package com.apeny.dababase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/spring-jdbc.xml")
public class Count10708Test {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testTimeout() throws InterruptedException {
        long begin = System.currentTimeMillis();
        String sql = "select /*ORDERTABLE*/ * from (select A.*, rownum rn from (SELECT SystemNo, SystemTime, Status, AccountTime FROM t_shardingx180300 where AccountTime >= '20180225015255'"
                + " and AccountTime <= '20181025041516' order by SystemNo) A"
                + " where rownum >= ?) B where rn <= ?";
        List<Shardingx> hi = jdbcTemplate.query(sql, new RowMapper<Shardingx>() {
            @Override
            public Shardingx mapRow(ResultSet rs, int rowNum) throws SQLException {
                Shardingx shardingx = new Shardingx();
                shardingx.setSystemNo(rs.getString(1));
                shardingx.setSystemTime(rs.getString(2));
                shardingx.setStatus(rs.getInt(3));
                shardingx.setAccountTime(rs.getString(4));
                return shardingx;
            }
        }, 0, 60000);
        System.out.println((System.currentTimeMillis() - begin) / 1000.0 + "s");
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setInt(2, 60000);
            ResultSet resultSet = preparedStatement.executeQuery();
            long begin2 = System.currentTimeMillis();
            Collection<List<Object>> result = new LinkedList<>();
            while (resultSet.next()) {
                List<Object> row = new ArrayList<>(5);
                for (int columnIndex = 1; columnIndex <= resultSet.getMetaData().getColumnCount(); columnIndex++) {
                    row.add(resultSet.getObject(columnIndex));
                }
                result.add(row);
            }
            System.out.println("length: " + result.size());
            System.out.println((System.currentTimeMillis() - begin2) / 1000.0 + "s");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class Shardingx {

    private String systemNo;

    private String systemTime;

    private int status;

    private String accountTime;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccountTime() {
        return accountTime;
    }

    public void setAccountTime(String accountTime) {
        this.accountTime = accountTime;
    }
}
