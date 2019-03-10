package com.apeny.servletjsp.bean.shardingsphere;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import io.shardingsphere.core.rule.TableRule;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by monis on 2019/1/23.
 */
@Component("accountTimePreciseShardingAlgorithm")
public class AccountTimePreciseShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue, TableRule tableRule) {
        return null;
    }
}
