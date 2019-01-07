package com.apeny.servletjsp.bean.shardingsphere;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by monis on 2018/11/25.
 */
@Component("txTimePreciseShardingAlgorithm")
public class TxTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String>{

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //20181010121314
        //半月一表
        //t_order180100 20180101-20180115
        //t_order180101 20180116-20180131
        //t_order180200 20180201-20180215
        //t_order180201 20180216-20180228
        if (shardingValue == null || availableTargetNames.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String value = shardingValue.getValue();
        if (value.length() < 8) {
            throw new IllegalArgumentException();
        }
        String tableName = shardingValue.getLogicTableName();
        int date = Integer.parseInt(value.substring(6, 8)) / 16;
        String shouldTableName = tableName + value.substring(2, 6) + (date < 10 ? "0" : "") + date;
        for (String avaibleName : availableTargetNames) {
            if (avaibleName.equalsIgnoreCase(shouldTableName)) {
                return shouldTableName;
            }
        }
        throw new IllegalArgumentException();
    }
}
