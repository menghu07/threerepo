package com.apeny.servletjsp.chapter10;

import com.apeny.servletjsp.util.JdbcUtil;

import java.sql.*;

/**
 * Created by apeny on 2018/5/23.
 */
public class SaveProductAction {

    /**
     * 保存Product
     *
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
}
