package com.apeny.servletjsp.chapter10;

import com.apeny.servletjsp.util.JdbcUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by apeny on 2018/5/23.
 */
public class SaveProductAction {

    /**
     * 保存Product
     * @param product
     */
    public void save(Product product) {
        try {
            Connection connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "insert into product(name, description, price) values(?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, (int) product.getPrice());
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
