package com.apeny.servletjsp.bean.shardingsphere.targetimpl;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import io.shardingsphere.core.rule.TableRule;

import java.util.*;

/**
 * 只向前扩充一年的表
 * Created by monis on 2019/1/24.
 */
public class BeforePeriodRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    private int supportedMonth = 12;

    public BeforePeriodRangeShardingAlgorithm(int supportedMonth) {
        this.supportedMonth = supportedMonth;
    }

    public BeforePeriodRangeShardingAlgorithm() {
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue, TableRule tableRule) {
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
        String actualTable = iterator.next();
        String lastCompleteTable = "";
        //一年中包含表个数
        int maxBeforeTables = tableRule.getPerMonthTables() * supportedMonth;
        Queue<String> maxBeforeQueue = new LinkedList<>();
        while (true) {
            if (shouldBeginTableName.compareToIgnoreCase(actualTable) <= 0
                    && shouldEndTableName.compareToIgnoreCase(actualTable) >= 0) {
                result.add(actualTable);
                lastCompleteTable = actualTable;
            } else {
                if (!iterator.hasNext()) {
                    //181201 last <=181201 <181201
                    //181201 last <=190100 <190100
                    if (shouldEndTableName.compareToIgnoreCase(lastCompleteTable) > 0) {
                        //将last表加入结果集中
                        result.add(actualTable);
                    }
                }
                if (shouldEndTableName.compareToIgnoreCase(actualTable) < 0) {
                    break;
                }
                addToQueue(maxBeforeQueue, actualTable, maxBeforeTables);
            }
            if (!iterator.hasNext()) {
                break;
            }
            actualTable = iterator.next();
        }
        result.addAll(0, maxBeforeQueue);
        return result;
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
