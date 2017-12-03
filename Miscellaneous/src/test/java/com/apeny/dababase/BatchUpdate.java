package com.apeny.dababase;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by apeny on 2017/11/25.
 */
public class BatchUpdate {
    static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        Object object = ThrowException.class;
        object = new ThrowException();
        System.out.println("main end");
//        testBatchUpdate();
    }

    /**
     * 批量插入不是事务!
     */
    private static void testBatchUpdate() {
        System.out.println(context.getBean("jdbcTemplate"));
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        jdbcTemplate.batchUpdate(new String[]{"insert into t1 VALUES (20)", "insert into t1 values(2)"});

    }

    static {
        context = new ClassPathXmlApplicationContext("config/applicationContext.xml");
    }
}

class ThrowException {
    static {
        int i = 0;
        if (i == 0) {
            throw new RuntimeException("yyyy");
        }
    }
}
