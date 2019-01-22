package com.apeny.servletjsp.controller.shardingsphere;

import com.alibaba.fastjson.JSON;
import com.apeny.servletjsp.dao.SpringShardingDAO;
import com.apeny.servletjsp.domain.sharding.Shardingx;
import com.apeny.servletjsp.query.SpringShardingQuery;
import com.apeny.servletjsp.util.SqlUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by monis on 2018/11/14.
 */
@Controller
public class ShardingxController {

    private static final Logger LOGGER = LogManager.getLogger("system");

    @Autowired
    private SpringShardingDAO springShardingDAO;

    @Resource(name = "springShardingQuery")
    private SpringShardingQuery springShardingQuery;

    @ResponseBody
    @RequestMapping(path = "/shardingx/insert.doy")
    public String insert() throws ParseException {
        Shardingx shardingx = new Shardingx();
        String systemNo = SqlUtil.generateOrderID();
        shardingx.setSystemNo(systemNo);
        shardingx.setSystemTime("20" + systemNo.substring(0, 12));
        shardingx.setStatus(new Random().nextInt(10));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        Date date = simpleDateFormat.parse("20" + systemNo.substring(0, 12));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, new Random().nextInt(1000000));
        shardingx.setAccountTime(simpleDateFormat.format(calendar.getTime()));
        springShardingDAO.insert(shardingx);
        return "inserted" + JSON.toJSONString(shardingx);
    }

    @ResponseBody
    @RequestMapping(path = "/shardingx/batchinsert.doy")
    public String batchInsert() throws ParseException {
        List<Shardingx> shardingxList = new ArrayList<>();
        for (int i = 0; i < 240000; i++) {
            Shardingx shardingx = new Shardingx();
            String systemNo = SqlUtil.generateOrderID();
            shardingx.setSystemNo(systemNo);
            shardingx.setSystemTime("20" + systemNo.substring(0, 12));
            shardingx.setStatus(new Random().nextInt(10));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
            Date date = simpleDateFormat.parse("20" + systemNo.substring(0, 12));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, new Random().nextInt(1000000));
            shardingx.setAccountTime(simpleDateFormat.format(calendar.getTime()));
            shardingxList.add(shardingx);
        }
        springShardingDAO.batchInsert(shardingxList);
        return "batchinserted";
    }

    @ResponseBody
    @RequestMapping(path = "/shardingx/querypage.doy")
    public String queryPage(int pageNo) {
        List<Shardingx> shardingxList = springShardingQuery.queryPage(pageNo);
        StringBuilder builder = new StringBuilder();
        builder.append("<table style='border:1px black solid;'>");
        for (Shardingx shardingx : shardingxList) {
            builder.append("<tr style='border:1px black solid;'>");
            builder.append("<td>").append(shardingx.getSystemNo()).append("</td>");
            builder.append("<td>").append(shardingx.getSystemTime()).append("</td>");
            builder.append("<td>").append(shardingx.getStatus()).append("</td>");
            builder.append("<td>").append(shardingx.getSystemTime()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }

    @ResponseBody
    @RequestMapping(path = "/shardingx/queryone.doy")
    public String queryOne(String systemNo) {
        Shardingx one = springShardingQuery.queryOne(systemNo);
        StringBuilder builder = new StringBuilder();
        builder.append("<table style='border:1px black solid;'>");
        if (one != null) {
            builder.append("<tr style='border:1px black solid;'>");
            builder.append("<td>").append(one.getSystemNo()).append("</td>");
            builder.append("<td>").append(one.getSystemTime()).append("</td>");
            builder.append("<td>").append(one.getStatus()).append("</td>");
            builder.append("<td>").append(one.getSystemTime()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }

    @ResponseBody
    @RequestMapping(path = "/shardingx/queryonebysystemtime.doy")
    public String queryOneBySystemTime(String systemTime) {
        Shardingx one = springShardingQuery.queryOneBySystemTime(systemTime);
        StringBuilder builder = new StringBuilder();
        builder.append("<table style='border:1px black solid;'>");
        if (one != null) {
            builder.append("<tr style='border:1px black solid;'>");
            builder.append("<td>").append(one.getSystemNo()).append("</td>");
            builder.append("<td>").append(one.getSystemTime()).append("</td>");
            builder.append("<td>").append(one.getStatus()).append("</td>");
            builder.append("<td>").append(one.getSystemTime()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }


    @ResponseBody
    @RequestMapping(path = "/shardingx/queryrangeltsystemtime.doy")
    public String queryRangeLessSystemTime(String systemTime) {
        List<Shardingx> shardingxList = springShardingQuery.queryLessThanSystemTime(systemTime);
        StringBuilder builder = new StringBuilder();
        builder.append("<table style='border:1px black solid;'>");
        for (Shardingx shardingx : shardingxList) {
            builder.append("<tr style='border:1px black solid;'>");
            builder.append("<td>").append(shardingx.getSystemNo()).append("</td>");
            builder.append("<td>").append(shardingx.getSystemTime()).append("</td>");
            builder.append("<td>").append(shardingx.getStatus()).append("</td>");
            builder.append("<td>").append(shardingx.getSystemTime()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }

    @ResponseBody
    @RequestMapping(path = "/shardingx/querycountone.doy")
    public String queryCountOne() {
        return "count = " + springShardingQuery.queryCount();
    }
}