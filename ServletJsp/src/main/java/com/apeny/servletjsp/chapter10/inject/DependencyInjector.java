package com.apeny.servletjsp.chapter10.inject;

import com.apeny.servletjsp.chapter10.SaveProductAction;
import com.apeny.servletjsp.chapter10.Validator;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by apeny on 2018/5/26.
 */
public class DependencyInjector {
    private BasicDataSource dataSource;

    /**
     * 开启数据源
     */
    public void start() {
        try {
            InputStream inputStream = DependencyInjector.class.getResourceAsStream("/properties/druid.db.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭数据库源
     */
    public void shutdown() {
        try {
            dataSource.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Object getObject(Class<?> type) {
        if (type == Validator.class) {
            return new Validator();
        } else if (type == InjectSaveProductAction.class) {
            return createSaveProductAction();
        }
        throw new IllegalArgumentException();
    }

    private InjectSaveProductAction createSaveProductAction() {
        InjectSaveProductAction injectSaveProductAction = new InjectSaveProductAction();
        injectSaveProductAction.setProductDAO(createProductDAO());
        return injectSaveProductAction;
    }

    private ProductDAO createProductDAO() {
        ProductDAO productDAO = new ProductDAO();
        productDAO.setDataSource(dataSource);
        return productDAO;
    }
}
