package com.apeny.servletjsp.bean.shardingsphere;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by monis on 2018/11/25.
 */
@Component("systemTimePreciseShardingAlgorithm")
public class SystemTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String>{

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //20181010121314
        //半月一表
        //t_order180100 20180101-20180115
        //t_order180101 20180116-20180131
        //t_order180200 20180201-20180215
        //t_order180201 20180216-20180228
        //必须分表
        if (shardingValue == null || availableTargetNames.isEmpty() || availableTargetNames.size() <= 2) {
            throw new IllegalArgumentException();
        }
        String value = shardingValue.getValue();
        if (value.length() < 8) {
            throw new IllegalArgumentException();
        }
        String tableName = shardingValue.getLogicTableName();
        int date = Integer.parseInt(value.substring(6, 8)) / 16;
        String shouldTableName = tableName + value.substring(2, 6) + (date < 10 ? "0" : "") + date;
        String firstSplitTable = null;
        int i = 0;
        String zeroTable = null;
        String lastTable;
        Iterator<String> iterator = availableTargetNames.iterator();
        while (true) {
            String availableName = iterator.next();
            if (i == 0) {
                zeroTable = availableName;
            }
            if (i == 1) {
                firstSplitTable = availableName;
            }
            i++;
            if (availableName.equalsIgnoreCase(shouldTableName)) {
                return Arrays.asList(shouldTableName);
            }
            if (!iterator.hasNext()) {
                lastTable = availableName;
                break;
            }
        }
        //最小值和最大值
        if (shouldTableName.compareToIgnoreCase(firstSplitTable) < 0) {
            return Arrays.asList(zeroTable);
        }
        return Arrays.asList(lastTable);
    }
}
