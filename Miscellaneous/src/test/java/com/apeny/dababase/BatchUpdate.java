package com.apeny.dababase;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by apeny on 2017/11/25.
 */
public class BatchUpdate {
    static ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
//        Object object = ThrowException.class;
//        object = new ThrowException();
//        System.out.println("main end");
        testBatchUpdate();
    }

    /**
     * 批量插入不是事务!因为默认是自动提交的jdbc
     *
     * 违反唯一约束是不能够插入数据；数据格式错误时能够插入数据
     */
    private static void testBatchUpdate() {
        System.out.println(context.getBean("jdbcTemplate"));
        JdbcTemplate jdbcTemplate = context.getBean("jdbcTemplate", JdbcTemplate.class);
        try {
            System.out.println("auto commit: " + jdbcTemplate.getDataSource().getConnection().getAutoCommit());
//            jdbcTemplate.batchUpdate(new String[]{"insert into test2 VALUES (80)", "insert into test2 VALUES (20)", "insert into test2 values('yujx'')"});
            List<Object[]> args = new ArrayList<>();
            for (int i = 1; i < 1000; i++) {
                args.add(new Object[]{i});
            }
            args.add(new Object[]{"hile"});
            System.out.println("begin insert data");
//            args.add(new Object[]{"hile"});
            jdbcTemplate.batchUpdate("INSERT INTO test2 values(?)", args);
            System.out.println("complete insert data");
        } catch (SQLException e) {
            e.printStackTrace();
        }


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
