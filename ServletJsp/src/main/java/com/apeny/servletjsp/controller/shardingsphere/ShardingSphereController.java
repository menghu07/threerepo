package com.apeny.servletjsp.controller.shardingsphere;

import com.apeny.servletjsp.domain.sharding.Order;
import com.apeny.servletjsp.util.SqlUtil;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.api.config.TableRuleConfiguration;
import io.shardingsphere.api.config.strategy.InlineShardingStrategyConfiguration;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by monis on 2018/11/14.
 */
@Controller
public class ShardingSphereController implements ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger("system");

    private Environment environment;

    private ApplicationContext applicationContext;

    @Autowired
    private PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer;
    private int num;
    private String orderID;

    @ResponseBody
    @RequestMapping(path = "/shardingsphere/rawsharding.doy")
    public String rawsharding() {
        String standHere = "";
        rawDataSource();
        return "ffff" + num;
    }

    /**
     * spring sharding
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/shardingsphere/springharding.doy")
    public String springSharding() {
        springDataSource();
        return "spring:" + num;
    }

    /**
     * pageno spring sharding
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(path = "/shardingsphere/sortspringgharding.doy")
    private String sortSpringData(int pageNo) {
        return springSortByStatus(pageNo);
    }

    private void rawDataSource() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        PropertySources propertySources = propertySourcesPlaceholderConfigurer.getAppliedPropertySources();
        PropertySource propertySource = propertySources.get("localProperties");
        //配置第一个数据源
        BasicDataSource dataSource1 = new BasicDataSource();
        dataSource1.setUrl((String) propertySource.getProperty("ds0.url"));
        dataSource1.setUsername((String) propertySource.getProperty("ds0.username"));
        dataSource1.setPassword((String) propertySource.getProperty("ds0.password"));
        dataSource1.setDriverClassName((String) propertySource.getProperty("ds0.driverClassName"));
        BasicDataSource basicDataSource2 = new BasicDataSource();
        basicDataSource2.setUrl((String) propertySource.getProperty("ds1.url"));
        basicDataSource2.setUsername((String) propertySource.getProperty("ds1.username"));
        basicDataSource2.setPassword((String) propertySource.getProperty("ds1.password"));
        basicDataSource2.setDriverClassName((String) propertySource.getProperty("ds1.driverClassName"));
        dataSourceMap.put("ds0", dataSource1);
        dataSourceMap.put("ds1", basicDataSource2);
        //配置Order表规则
        TableRuleConfiguration orderTableRuleConfiguration = new TableRuleConfiguration();
        orderTableRuleConfiguration.setLogicTable("t_order");
        orderTableRuleConfiguration.setActualDataNodes("ds${0..1}.t_order${0..1}");
        //分库，分表规则
        orderTableRuleConfiguration.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds${user_id % 2}"));
        orderTableRuleConfiguration.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        shardingRuleConfiguration.getTableRuleConfigs().add(orderTableRuleConfiguration);
        try {
            DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfiguration, new ConcurrentHashMap<>(), new Properties());
            Connection connection = dataSource.getConnection();
            String insertSQL = "insert into t_order(user_id, status) values( " + num + ", 'fff')";
            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
//            preparedStatement.setInt(1, num);
//            preparedStatement.setInt(2, num);
//            preparedStatement.setString(3, "news" + num);
            int count = preparedStatement.executeUpdate();
            System.out.println("affected count: " + count);
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        System.out.println("hhhhaxx");
    }

    private void springDataSource() {
        JdbcTemplate jdbcTemplate = applicationContext.getBean("shardingSpringTemplate", JdbcTemplate.class);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 480000; i++) {
            try {
                num = Math.abs(new Random().nextInt());
                orderID = SqlUtil.generateOrderID();
                int userID = Math.abs(num / 2 + new Random(8888).nextInt(19));
                String insertSQL = "insert into t_order(order_id, user_id, status) values('" + orderID + "'," + userID + ",'Spring"
                        + num + "')";
                System.out.println("sql: " + insertSQL);
                jdbcTemplate.update(insertSQL);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error(e);
            }
        }
        System.out.println("use time: " + ((System.currentTimeMillis() - begin) / 1000) + "s");
    }

    private String springSortByStatus(int pageNo) {
        int start = (pageNo - 1 ) * 50 + 1;
        int end = pageNo * 50;
        JdbcTemplate jdbcTemplate = applicationContext.getBean("shardingSpringTemplate", JdbcTemplate.class);
        long begin = System.currentTimeMillis();
        String querySQL = "select * from (select A.*, rownum rn from (select order_id, user_id, status from t_order where status >= 'Spring623249158'"
                + " and status <= 'Spring986975506' order by order_id) A"
                + " where rownum >= ?) B where rn <= ?";
        List<Order> orderList = jdbcTemplate.query(querySQL, new RowMapper<Order>() {
            @Override
            public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
                Order order = new Order();
                order.setOrderID(rs.getLong(1));
                order.setUserID(rs.getLong(2));
                order.setStatus(rs.getString(3));
                return order;
            }
        }, start, end);
        System.out.println("use time: " + ((System.currentTimeMillis() - begin) / 1000) + "s");
        StringBuilder builder = new StringBuilder();
        builder.append("<table>");
        for (Order order : orderList) {
            builder.append("<tr>");
            builder.append("<td>").append(order.getOrderID()).append("</td>");
            builder.append("<td>").append(order.getUserID()).append("</td>");
            builder.append("<td>").append(order.getStatus()).append("</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
        return builder.toString();
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}