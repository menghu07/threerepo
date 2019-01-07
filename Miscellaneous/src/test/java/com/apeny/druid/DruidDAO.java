package com.apeny.druid;

import com.apeny.druid.domain.ResponseCategory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by apeny on 2018/4/3.
 */
public class DruidDAO {
    public static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("config/druidApplicationContext.xml");
    }

    public static void selectResponseCategory(JdbcTemplate jdbcTemplate) {
        String sql = "SELECT code code2, DESCRIPTION, RESPONSECODE, TYPE, PARENTCODE, CREATOR, CREATETIME FROM ResponseCategory";
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
                try {
                    int i = 0;//rs.findColumn("ff");
                    ResultSetMetaData resultSetMetaData = rs.getMetaData();
                    int count = resultSetMetaData.getColumnCount();
                    Set<String> columnNames = new HashSet<>();
                    for (int j = 0; j < count; j++) {
                        columnNames.add(resultSetMetaData.getColumnLabel(j+1));
                    }
                    System.out.println(columnNames);

                    String columnLabel = resultSetMetaData.getColumnLabel(1);
                    System.out.println("iii " + i + "" + columnLabel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return r;
            }
        });
        System.out.println("list response Category list: " + responseCategoryList.size() + ", data: " + responseCategoryList);
    }
}
