package com.apeny.servletjsp.redis.importer;

import com.apeny.servletjsp.util.RedisPipelineUtil;
import com.apeny.servletjsp.vo.RedisObjectVO;
import redis.clients.jedis.Pipeline;

import java.util.List;

/**
 * Created by ahu on 2017/7/23.
 */
public class RedisImportWorker implements Runnable {
    private Pipeline pipeline;
    private List<RedisObjectVO> voList;
    public RedisImportWorker(Pipeline pipeline, List<RedisObjectVO> voList) {
        this.pipeline = pipeline;
        this.voList = voList;
    }
    @Override
    public void run() {
        RedisPipelineUtil.executeObject(pipeline, voList);
    }
}
