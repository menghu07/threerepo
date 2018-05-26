package com.apeny.servletjsp.chapter10.inject;

import com.apeny.servletjsp.chapter10.Product;
import com.apeny.servletjsp.util.JdbcUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by apeny on 2018/5/26.
 */
public class ProductDAO {

    private DataSource dataSource;

    public void saveProduct(Product product) {
        try {
            Connection connection = dataSource.getConnection();
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

    public Product queryOne(String name) {
        Product product = new Product();
        try {
            Connection connection = JdbcUtil.getConnection();
            connection.setAutoCommit(false);
            String sql = "select name, description, price from product where trim(name) = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                product.setName(resultSet.getString(1));
                product.setDescription(resultSet.getString(2));
                product.setPrice(resultSet.getInt(3));
            }
            System.out.println("find Product: " + product.getName() + ", " + product.getDescription() + ", " + product.getPrice());
            if (resultSet.next()) {
                String newName = resultSet.getString(1);
                String des = resultSet.getString(2);
                int price = resultSet.getInt(3);
                System.out.println("name = " + newName + ", description = " + des + ", price = " + price);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
