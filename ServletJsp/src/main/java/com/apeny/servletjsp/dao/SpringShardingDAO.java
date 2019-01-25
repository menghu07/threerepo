package com.apeny.servletjsp.dao;

import com.apeny.servletjsp.domain.sharding.Shardingx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by monis on 2018/11/26.
 */
@Repository
public class SpringShardingDAO {

    private static final Logger LOGGER = LogManager.getLogger("system");

    @Resource(name = "shardingSpringTemplate")
    private JdbcTemplate shardingSpringTemplate;

    /**
     * Shardingx单笔入库
     * @param shardingx
     */
    public void insert(Shardingx shardingx) {
        String sql = "INSERT INTO t_Shardingx(SystemNo, SystemTime, Status, AccountTime) VALUES(?, ?, ?, ?)";
        shardingSpringTemplate.update(sql, shardingx.getSystemNo(), shardingx.getSystemTime(), shardingx.getStatus(), shardingx.getAccountTime());
    }

    /**
     * 批量插入数据
     * @param shardingxList
     */
    public void batchInsert(List<Shardingx> shardingxList) {
        String sql = "INSERT INTO t_Shardingx(SystemNo, SystemTime, Status, AccountTime) VALUES(?, ?, ?, ?)";
        List<Object[]> args = new ArrayList<>();
        shardingxList.forEach(shardingx -> {
            args.add(new Object[]{shardingx.getSystemNo(), shardingx.getSystemTime(), shardingx.getStatus(), shardingx.getAccountTime()});
        });
        shardingSpringTemplate.batchUpdate(sql, args);
    }

    /**
     * 更新数据假设一条记录
     * @param shardingx
     */
    public void update(Shardingx shardingx) {
        String sql = "UPDATE t_Shardingx SET Status = ?, AccountTime = ? WHERE SystemTime = ?";
        shardingSpringTemplate.update(sql, shardingx.getStatus(), shardingx.getAccountTime(), shardingx.getSystemTime());
    }

    /**
     * 批量更新数据
     * @param shardingxList
     */
    public void batchUpdate(List<Shardingx> shardingxList) {
        String sql = "UPDATE t_Shardingx SET Status = ?, AccountTime = ? WHERE SystemTime = ?";
        List<Object[]> args = new ArrayList<>();
        shardingxList.forEach(shardingx -> {
            args.add(new Object[]{shardingx.getStatus(), shardingx.getAccountTime(), shardingx.getSystemTime()});
        });
        shardingSpringTemplate.batchUpdate(sql, args);
    }

    /**
     * 更新不分表数据
     * @param x
     * @param y
     */
    public void updateNoSplit(String x, String y) {
        String sql = "UPDATE t_nosplitoNE SET y = ? WHERE x = ?";
        List<Object[]> args = new ArrayList<>();
        shardingSpringTemplate.update(sql, y, x);
    }
}
