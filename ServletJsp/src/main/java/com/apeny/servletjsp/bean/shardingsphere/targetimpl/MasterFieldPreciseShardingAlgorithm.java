package com.apeny.servletjsp.bean.shardingsphere.targetimpl;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;
import java.util.Iterator;

/**
 * 精确分表
 * Created by monis on 2019/1/24.
 * @author monis
 */
public class MasterFieldPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
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
        if (value == null || value.length() < 8) {
            throw new IllegalArgumentException();
        }
        String tableName = shardingValue.getLogicTableName();
        String columnName = shardingValue.getColumnName();
        String dateValue = tableSuffix(columnName, value);
        int date = Integer.parseInt(dateValue.substring(4, 6)) / 16;
        String shouldTableName = tableName + dateValue.substring(0, 4) + (date < 10 ? "0" : "") + date;
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
                return shouldTableName;
            }
            if (!iterator.hasNext()) {
                lastTable = availableName;
                break;
            }
        }
        //最小值和最大值
        if (shouldTableName.compareToIgnoreCase(firstSplitTable) < 0) {
            return zeroTable;
        }
        return lastTable;
    }

    private String tableSuffix(String columnName, String value) {
        String lowerColumnName = columnName.toLowerCase();
        if (lowerColumnName.endsWith("no")) {
            //按no类型解析
            if (value.length() == 20 || value.length() == 25) {
                return value.substring(0, 6);
            } else if (value.length() == 27) {
                return value.substring(2, 8);
            }
            throw new IllegalArgumentException("字段值长度必须20/25/27");
        } else if (lowerColumnName.endsWith("time")){
            //按time类型解析
            return value.substring(2, 8);
        }
        throw new IllegalArgumentException("列名称后缀必须时no或time");
    }
}
