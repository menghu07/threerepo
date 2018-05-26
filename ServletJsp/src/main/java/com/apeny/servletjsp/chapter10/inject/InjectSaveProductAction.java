package com.apeny.servletjsp.chapter10.inject;

import com.apeny.servletjsp.chapter10.Product;
import com.apeny.servletjsp.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by apeny on 2018/5/23.
 */
public class InjectSaveProductAction {

    private ProductDAO productDAO;

    /**
     * 保存Product
     *
     * @param product
     */
    public void save(Product product) {
       productDAO.saveProduct(product);
    }

    public Product queryOne(String name) {
        return productDAO.queryOne(name);
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
