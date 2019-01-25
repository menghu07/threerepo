package com.apeny.servletjsp.bean.shardingsphere;

import io.shardingsphere.api.algorithm.sharding.RangeShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.RangeShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by monis on 2019/1/23.
 */
@Component("systemNoRangeShardingAlgorithm")
public class SystemNoRangeShardingAlgorithm implements RangeShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {

        return null;
    }
}
