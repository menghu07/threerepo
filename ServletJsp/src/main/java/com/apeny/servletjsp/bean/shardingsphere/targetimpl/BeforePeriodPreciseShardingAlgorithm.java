package com.apeny.servletjsp.bean.shardingsphere.targetimpl;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.core.rule.TableRule;

import java.util.*;

/**
 * 向前查询一年 比如：一年24张表，就向前查询24张表；一年48张表，向前查询48张表
 * Created by monis on 2019/1/24.
 */
public class BeforePeriodPreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {
    private int supportedMonth = 12;

    public BeforePeriodPreciseShardingAlgorithm(int supportedMonth) {
        this.supportedMonth = supportedMonth;
    }

    public BeforePeriodPreciseShardingAlgorithm() {
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue, TableRule tableRule) {
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
        String firstSplitTable = "";
        int i = 0;
        String zeroTable = "";
        String lastTable;
        Iterator<String> iterator = availableTargetNames.iterator();
        //一年中包含表个数
        int maxBeforeTables = tableRule.getPerMonthTables() * supportedMonth;
        Queue<String> maxBeforeQueue = new LinkedList<>();
        String targetTable;
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
                targetTable = shouldTableName;
                maxBeforeQueue.add(targetTable);
                return maxBeforeQueue;
            }
            if (!iterator.hasNext()) {
                lastTable = availableName;
                break;
            }
            addToQueue(maxBeforeQueue, availableName, maxBeforeTables);
        }
        //最小值和最大值
        if (shouldTableName.compareToIgnoreCase(firstSplitTable) < 0) {
            return Collections.singletonList(zeroTable);
        }
        //假设查询时间在最表之后一年内，粗略计算
        //TODO 可以再优化些
        maxBeforeQueue.add(lastTable);
        return maxBeforeQueue;
    }

    private String tableSuffix(String columnName, String value) {
        String lowerColumnName = columnName.toLowerCase();
        if (lowerColumnName.endsWith("time")){
            //按time类型解析
            return value.substring(2, 8);
        }
        throw new IllegalArgumentException("列名称后缀必须是time");
    }

    /**
     * 最大保留之前一年的表
     * @param maxBeforeTable
     * @param beforeTable
     * @param maxSize
     */
    private void addToQueue(Queue<String> maxBeforeTable, String beforeTable, int maxSize) {
        if (maxBeforeTable.size() >= maxSize) {
            //容量限制会抛异常
            maxBeforeTable.remove();
        }
        maxBeforeTable.add(beforeTable);
    }
}
