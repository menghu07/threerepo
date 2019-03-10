package com.apeny.servletjsp.bean.shardingsphere.targetimpl;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;

import java.util.*;

/**
 * Created by monis on 2019/1/24.
 */
public class NeighborRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        if (shardingValue == null || availableTargetNames.isEmpty()) {
            throw new IllegalArgumentException();
        }
        List<String> result = new ArrayList<>();
        Range<String> range = shardingValue.getValueRange();
        if (range == null) {
            throw new IllegalArgumentException();
        }
        if (range.isEmpty()) {
            return Collections.emptyList();
        }
        String tableName = shardingValue.getLogicTableName();
        String beginSuffix = "";
        if (range.hasLowerBound()) {
            String begin = range.lowerEndpoint();
            String dateValue = tableSuffix(shardingValue.getColumnName(), begin);
            int date = Integer.parseInt(dateValue.substring(4, 6)) / 16;
            beginSuffix = dateValue.substring(0, 4) + (date < 10 ? "0" : "") + date;
        }
        //上下都扩充一张表
        String shouldBeginTableName = tableName + beginSuffix;
        String endSuffix = "last";
        if (range.hasUpperBound()) {
            String end = range.upperEndpoint();
            String dateValue = tableSuffix(shardingValue.getColumnName(), end);
            int date = Integer.parseInt(dateValue.substring(4, 6)) / 16;
            endSuffix = dateValue.substring(0, 4) + (date < 10 ? "0" : "") + date;
        }
        String shouldEndTableName = tableName + endSuffix;
        //查询条件 小于最小分表 返还表包含zerotable 大于最大分表 返还lasttable
        int i = 0;
        Iterator<String> iterator = availableTargetNames.iterator();
        String zeroTable = iterator.next();
        String beforeTable = zeroTable;
        String neighborBeforeTable = zeroTable;
        String actualTable = iterator.next();
        String firstTable = actualTable;
        String afterTable = iterator.next();
        String neighborAfterTable = "";
        while (true) {
            if (shouldBeginTableName.compareToIgnoreCase(actualTable) <= 0
                    && shouldEndTableName.compareToIgnoreCase(actualTable) >= 0) {
                result.add(actualTable);
                if (i == 0) {
                    neighborBeforeTable = beforeTable;
                }
                neighborAfterTable = afterTable;
                i++;
            }
            if (!iterator.hasNext()) {
                break;
            }
            beforeTable = actualTable;
            actualTable = afterTable;
            afterTable = iterator.next();
        }
        if (result.isEmpty()) {
            if (firstTable.compareToIgnoreCase(shouldEndTableName) > 0) {
                //左侧最小值
                return Arrays.asList(zeroTable, firstTable);
            } else {
                //右侧最大值
                return Arrays.asList(actualTable, afterTable);
            }
        }
        //最大表<时间范围开始表
        result.add(0, neighborBeforeTable);
        result.add(neighborAfterTable);
        return result;
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
