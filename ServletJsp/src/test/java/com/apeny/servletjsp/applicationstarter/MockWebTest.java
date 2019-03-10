package com.apeny.servletjsp.applicationstarter;

import com.alibaba.fastjson.JSON;
import com.apeny.servletjsp.controller.shardingsphere.ShardingxController;
import com.apeny.servletjsp.dao.SpringShardingDAO;
import com.apeny.servletjsp.domain.sharding.FeeVoucher;
import com.apeny.servletjsp.query.SpringShardingQuery;
import com.apeny.servletjsp.util.SqlUtil;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.web.Log4jServletContainerInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by monis on 2019/1/22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/applicationContext.xml")
public class MockWebTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Before
    public void init() {
        Configurator.initialize(null, "classpath:config/log4j2.xml");
    }

    @Test
    public void testInsertOne() throws ParseException {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.insert();
    }

    @Test
    public void testInsertBatch() throws ParseException {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.batchInsert();
    }

    @Test
    public void testUpdate() throws ParseException {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.update();
    }

    @Test
    public void testBatchUpdate() throws ParseException {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.batchUpdate();
    }

    @Test
    public void testQueryOneBySystemNo() {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.queryOne("1801141034028905709672951");
    }

    @Test
    public void testQueryOneBySystemTime() {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.queryOneBySystemTime("20180101111950");
    }

    @Test
    public void testQueryWithOr() {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.queryRangeLessSystemTime("20180101111050", "20180101112350");
    }

    @Test
    public void testQueryNoSplitOne() {
        System.out.println("applicationContext: " + applicationContext);
        ShardingxController shardingxController = applicationContext.getBean("shardingxController", ShardingxController.class);
        shardingxController.queryNoSplitOne("22323343");
    }

    @Test
    public void testSimpleDateFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        String date = "180201";
        try {
            Date date1 = simpleDateFormat.parse(date);
            System.out.println("date1: " + date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteFeeVoucher() {
        SpringShardingDAO springShardingDAO = applicationContext.getBean("springShardingDAO", SpringShardingDAO.class);
        springShardingDAO.deleteAll("20190115101205295");
    }

    @Test
    public void testInsertFeeVoucher() {
        SpringShardingDAO springShardingDAO = applicationContext.getBean("springShardingDAO", SpringShardingDAO.class);
        for (int i = 0; i < 10000; i++) {
            FeeVoucher feeVoucher = new FeeVoucher();
            String guid = SqlUtil.generateOrderID();
            String systemTime = "20" + guid.substring(0, 15);
            feeVoucher.setSystemNo(guid);
            feeVoucher.setSystemTime(systemTime);
            feeVoucher.setSourceTxNo(guid);
            feeVoucher.setInstitutionID("00000000");
            feeVoucher.setTxType("123456");
            feeVoucher.setSourceTxSN(guid);
            feeVoucher.setSourceTxTime(SqlUtil.addSeconds(systemTime, -SqlUtil.rand10()));
            feeVoucher.setStatus(2);
            feeVoucher.setAccountTime(SqlUtil.addSeconds(systemTime, SqlUtil.rand365()));
            springShardingDAO.insertFeeVoucher(feeVoucher);
        }
    }

    @Test
    public void testQueryOne() {
        SpringShardingQuery springShardingQuery = applicationContext.getBean("springShardingQuery", SpringShardingQuery.class);
        System.out.println("result: " + JSON.toJSONString(springShardingQuery.queryBySystemNo("1801161152164783870891405")));
    }

    @Test
    public void testSourceTxNo() {
        SpringShardingQuery springShardingQuery = applicationContext.getBean("springShardingQuery", SpringShardingQuery.class);
        System.out.println("result: " + JSON.toJSONString(springShardingQuery.queryBySourceTxNo("1801161152164783870891405")));
    }

    @Test
    public void testSourceTxNoNext() {
        SpringShardingQuery springShardingQuery = applicationContext.getBean("springShardingQuery", SpringShardingQuery.class);
        System.out.println("result: " + JSON.toJSONString(springShardingQuery.queryBySourceTxNo("1805171152394879259467267")));
    }
}