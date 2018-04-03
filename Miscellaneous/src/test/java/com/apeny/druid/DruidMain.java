package com.apeny.druid;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Created by apeny on 2018/4/3.
 */
public class DruidMain {
    public static void main(String[] args) {
        testSelectCategory();
    }

    private static void testSelectCategory() {
        DruidDAO.selectResponseCategory(DruidDAO.applicationContext.getBean("jdbcTemplate", JdbcTemplate.class));
    }
}
