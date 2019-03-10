package com.apeny.servletjsp.bean.shardingsphere.targetimpl;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by monis on 2019/1/24.
 */
public class NeighborPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        //=操作符
        //SourceTxTime || SourceTxNo
        //按顺序排列
        //当前表、后一张表、前一张表做为候选表，查询到结果就返回
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
        String zeroTable;
        String firstTable;
        String afterTable;
        String beforeTable;
        Iterator<String> iterator = availableTargetNames.iterator();
        zeroTable = iterator.next();
        beforeTable = zeroTable;
        String availableName = iterator.next();
        firstTable = availableName;
        afterTable = iterator.next();
        while (availableName.compareToIgnoreCase(shouldTableName) < 0) {
            beforeTable = availableName;
            availableName = afterTable;
            if (iterator.hasNext()) {
                afterTable = iterator.next();
            } else {
                break;
            }
        }
        //找到目标表
        if (availableName.equalsIgnoreCase(shouldTableName)) {
            return Arrays.asList(availableName, afterTable, beforeTable);
        } else {
            //没有找到目标表
            if (firstTable.compareToIgnoreCase(shouldTableName) < 0) {
                return Arrays.asList(availableName, beforeTable);
            } else {
                return Arrays.asList(zeroTable, firstTable);
            }
        }
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
        throw new IllegalArgumentException("列名称后缀必须是no或time");
    }
}
