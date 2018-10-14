package com.apeny.servletjsp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by apeny on 2018/6/9.
 */
@Controller
public class WxMessageController {

    /**
     * jdbcTemplate
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @ResponseBody
    @RequestMapping(path = "/wxmessage/checkwxserver.dox")
    public String checkWxServer(String signature, String timestamp, String nonce, String echostr) {
        System.out.println("i do nothing....");
        selectTableOne();
        return "ha ha test shiro";
    }

    private void selectTableOne() {
        String sql = "select i from t1";
        List<T1> list = jdbcTemplate.query(sql, new RowMapper<T1>() {
            @Override
            public T1 mapRow(ResultSet rs, int rowNum) throws SQLException {
                T1 t1 = new T1();
                t1.setI(rs.getInt(1));
                return t1;
            }
        });
        System.out.println(list);
    }

    class T1{
        private int i;

        public int getI() {
            return i;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("T1{");
            sb.append("i=").append(i);
            sb.append('}');
            return sb.toString();
        }

        public void setI(int i) {
            this.i = i;
        }
    }
}
