package com.apeny.servletjsp.query;

import com.apeny.servletjsp.domain.sharding.FeeVoucher;
import com.apeny.servletjsp.domain.sharding.NoSplitOne;
import com.apeny.servletjsp.domain.sharding.Shardingx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by monis on 2018/11/26.
 */
@Repository
public class SpringShardingQuery {

    private static final Logger LOGGER = LogManager.getLogger("system");

    @Resource(name = "shardingSpringTemplate")
    private JdbcTemplate shardingSpringTemplate;

    public List<Shardingx> queryPage(int pageNo) {
        int start = (pageNo - 1) * 50 + 1;
        int end = pageNo * 50;
        String sql = "select /*- ORDERTABLE */ * from (select A.*, rownum rn from (SELECT SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx where AccountTime >= '20180225015255'"
                + " and AccountTime <= '20181025041516' order by SystemNo) A"
                + " where rownum >= ?) B where rn <= ?";
        return shardingSpringTemplate.query(sql, new RowMapper<Shardingx>() {

            @Override
            public Shardingx mapRow(ResultSet rs, int rowNum) throws SQLException {
                Shardingx shardingx = new Shardingx();
                shardingx.setSystemNo(rs.getString(1));
                shardingx.setSystemTime(rs.getString(2));
                shardingx.setStatus(rs.getInt(3));
                shardingx.setAccountTime(rs.getString(4));
                return shardingx;
            }
        }, start, end);
    }

    public Shardingx queryOne(String systemNo) {
        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE SystemNo = ?";
        return shardingSpringTemplate.queryForObject(sql, (rs, rowNum) -> {
            Shardingx shardingx = new Shardingx();
            shardingx.setSystemNo(rs.getString(1));
            shardingx.setSystemTime(rs.getString(2));
            shardingx.setStatus(rs.getInt(3));
            shardingx.setAccountTime(rs.getString(4));
            return shardingx;
        }, systemNo);
    }

    public Shardingx queryOneBySystemTime(String systemTime) {
        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE SystemTime = ?";
        return shardingSpringTemplate.queryForObject(sql, (rs, rowNum) -> {
            Shardingx shardingx = new Shardingx();
            shardingx.setSystemNo(rs.getString(1));
            shardingx.setSystemTime(rs.getString(2));
            shardingx.setStatus(rs.getInt(3));
            shardingx.setAccountTime(rs.getString(4));
            return shardingx;
        }, systemTime);
    }

    public List<Shardingx> queryLessThanSystemTime(String systemTime) {
//        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE ACCOUNTTIME < '20180108025150'"
//                + " AND SystemTime BETWEEN '20180101001010' AND ?";
//        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
//                + " SystemTime < ?";
//        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
//                + " SystemTime <= ?";
        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
                + " SystemTime > ?";
        return shardingSpringTemplate.query(sql, (rs, rowNum) -> {
            Shardingx shardingx = new Shardingx();
            shardingx.setSystemNo(rs.getString(1));
            shardingx.setSystemTime(rs.getString(2));
            shardingx.setStatus(rs.getInt(3));
            shardingx.setAccountTime(rs.getString(4));
            return shardingx;
        }, systemTime);
    }

    public List<Shardingx> queryGreaterAndLessSystemTime(String startTime, String endTime) {
//        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
//                + " SystemTime >= ? AND SystemTime < ?";
//        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
//                + " SystemTime >= ? AND SystemTime < ? AND SystemTime < ?";
        String sql = "select SystemNo, SystemTime, Status, AccountTime FROM t_Shardingx WHERE"
                + " SystemTime >= ? AND SystemTime < ? OR (SystemTime > ? AND SystemTime <= ?)";
        return shardingSpringTemplate.query(sql, (rs, rowNum) -> {
            Shardingx shardingx = new Shardingx();
            shardingx.setSystemNo(rs.getString(1));
            shardingx.setSystemTime(rs.getString(2));
            shardingx.setStatus(rs.getInt(3));
            shardingx.setAccountTime(rs.getString(4));
            return shardingx;
        }, startTime, endTime, startTime, "20180101161950");
    }

    public int queryCount() {
        String sql = "select count(*) from t_Shardingx where AccountTime >= '20180225015255' AND AccountTime <= '20181025041516'";
        return shardingSpringTemplate.queryForObject(sql, Integer.class);
    }

    public NoSplitOne queryNoSplitOne(String x) {
        String sql = "select x, y FROM t_nosplitoNE WHERE x = ?";
        return shardingSpringTemplate.queryForObject(sql, (rs, rowNum) -> {
            NoSplitOne noSplitOne = new NoSplitOne();
            noSplitOne.setX(rs.getString(1));
            noSplitOne.setY(rs.getString(2));
            return noSplitOne;
        }, x);
    }

    public FeeVoucher queryBySystemNo(String systemNo) {
        String sql = "select SYSTEMNO,SystemTime, SourceTxNo, InstitutionID,"
                + " TxType, SourceTxSN, SourceTxTime, Status, AccountTime FROM JF_FeeVoucher WHERE SystemNo = ?";
        return shardingSpringTemplate.queryForObject(sql, (rs, rowNum) -> {
            FeeVoucher feeVoucher = new FeeVoucher();
            feeVoucher.setSystemNo(rs.getString(1));
            feeVoucher.setSystemTime(rs.getString(2));
            feeVoucher.setSourceTxNo(rs.getString(3));
            feeVoucher.setInstitutionID(rs.getString(4));
            feeVoucher.setTxType(rs.getString(5));
            feeVoucher.setSourceTxNo(rs.getString(6));
            feeVoucher.setSystemTime(rs.getString(7));
            feeVoucher.setStatus(rs.getInt(8));
            feeVoucher.setAccountTime(rs.getString(9));
            return feeVoucher;
        }, systemNo);
    }

    public FeeVoucher queryBySourceTxNo(String sourceTxNo) {
        String sql = "select SYSTEMNO,SystemTime, SourceTxNo, InstitutionID,"
                + " TxType, SourceTxSN, SourceTxTime, Status, AccountTime FROM JF_FeeVoucher WHERE SourceTxNo = ?";
        return shardingSpringTemplate.queryForObject(sql, (rs, rowNum) -> {
            FeeVoucher feeVoucher = new FeeVoucher();
            feeVoucher.setSystemNo(rs.getString(1));
            feeVoucher.setSystemTime(rs.getString(2));
            feeVoucher.setSourceTxNo(rs.getString(3));
            feeVoucher.setInstitutionID(rs.getString(4));
            feeVoucher.setTxType(rs.getString(5));
            feeVoucher.setSourceTxSN(rs.getString(6));
            feeVoucher.setSourceTxTime(rs.getString(7));
            feeVoucher.setStatus(rs.getInt(8));
            feeVoucher.setAccountTime(rs.getString(9));
            return feeVoucher;
        }, sourceTxNo);
    }
}
