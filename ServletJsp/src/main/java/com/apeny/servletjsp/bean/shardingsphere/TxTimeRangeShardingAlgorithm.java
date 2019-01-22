package com.apeny.servletjsp.bean.shardingsphere;

import com.google.common.collect.Range;
import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by monis on 2019/1/20.
 */
@Component("txTimeRangeShardingAlgorithm")
public class TxTimeRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        if (shardingValue == null || availableTargetNames.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Collection<String> result = new ArrayList<>();
        Range<String> range = shardingValue.getValueRange();
        if (range == null) {
            throw new IllegalArgumentException();
        }
        String tableName = shardingValue.getLogicTableName();
        String beginSuffix = "";
        if (range.hasLowerBound()) {
            String begin = range.lowerEndpoint();
            int beginDate = Integer.parseInt(begin.substring(6, 8)) / 16;
            beginSuffix =  begin.substring(2, 6) + (beginDate < 10 ? "0" : "") + beginDate;
        }
        String shouldBeginTableName = tableName + beginSuffix;
        String endSuffix = "last";
        if (range.hasUpperBound()) {
            String end = range.upperEndpoint();
            int endDate = Integer.parseInt(end.substring(6, 8)) / 16;
            endSuffix = end.substring(2, 6) + (endDate < 10 ? "0" : "") + endDate;
        }
        String shouldEndTableName = tableName + endSuffix;
        for (String actualTable : availableTargetNames) {
            if (shouldBeginTableName.compareToIgnoreCase(actualTable) <= 0 && shouldEndTableName.compareToIgnoreCase(actualTable) >= 0) {
                result.add(actualTable);
            }
        }
        return result;
    }
}
