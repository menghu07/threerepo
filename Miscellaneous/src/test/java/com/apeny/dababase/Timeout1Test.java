package com.apeny.dababase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:config/applicationContext.xml")
@Rollback()
@Transactional(timeout = 2, transactionManager = "txManager")
public class Timeout1Test {
    @Autowired
    private DataSource ds;
    @Test
    public void testTimeout() throws InterruptedException {  
        System.out.println(System.currentTimeMillis());  
        Thread.sleep(3000L);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
        jdbcTemplate.execute(" update t1 set i = 33");
        System.out.println(System.currentTimeMillis());
    }
}  