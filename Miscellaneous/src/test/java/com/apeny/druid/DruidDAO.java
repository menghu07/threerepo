package com.apeny.druid;

import com.apeny.druid.domain.ResponseCategory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apeny on 2018/4/3.
 */
public class DruidDAO {
    public static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("config/druidApplicationContext.xml");
    }

    public static void selectResponseCategory(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT code, DESCRIPTION, RESPONSECODE, TYPE, PARENTCODE, CREATOR, CREATETIME FROM ResponseCategory";
//        List<ResponseCategory> responseCategoryList = jdbcTemplate.queryForList(sql, ResponseCategory.class);
        List<ResponseCategory> responseCategoryList = jdbcTemplate.query(sql, new RowMapper<ResponseCategory>() {
            @Override
            public ResponseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                ResponseCategory r = new ResponseCategory();
                r.setCode(rs.getString(1));
                r.setDescription(rs.getString(2));
                r.setResponseCode(rs.getString(3));
                r.setType(rs.getInt(4));
                r.setParentCode(rs.getString(5));
                r.setCreator(rs.getString(6));
                r.setCreateTime(rs.getString(7));
                return r;
            }
        });
        System.out.println("list response Category list: " + responseCategoryList.size() + ", data: " + responseCategoryList);
    }
}
