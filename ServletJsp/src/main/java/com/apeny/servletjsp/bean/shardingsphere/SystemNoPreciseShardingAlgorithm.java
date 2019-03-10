package com.apeny.servletjsp.bean.shardingsphere;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Created by monis on 2019/1/23.
 * 与分片字段处理逻辑近似，从中取出时间标识，只查询路由出的表
 */
public class SystemNoPreciseShardingAlgorithm implements PreciseShardingAlgorithm {

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, PreciseShardingValue shardingValue) {
        return null;
    }
}
