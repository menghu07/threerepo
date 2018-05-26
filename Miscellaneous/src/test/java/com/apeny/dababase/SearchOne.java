package com.apeny.dababase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by apeny on 2018/5/26.
 */
public class SearchOne {
    private static String url;
    private static String driverClass;
    private static String username;
    private static String password;
    private static Connection connection;

    public static void main(String[] args) {
        queryOne();
    }

    private static void queryOne() {
        String selectSql = "select * from db1.company";
        String sql = "select * from db1.company where name = 'fa' or 1=1 --fff'";
        String orcsql = "select * from product where name = 'ff' or 1 = 1 ---f";
        PreparedStatement preparedStatement = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(orcsql);
//            preparedStatement = connection.prepareStatement(orcsql);
//            ResultSet resultSet = preparedStatement.executeQuery();
            connection.commit();
            while (resultSet.next()) {
                System.out.println("inserted value " + resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("thread " + Thread.currentThread() + "commit");
    }

    static {
        Properties ps = new Properties();
        try {
            ps.load(new FileInputStream("src/main/resources/properties/druid.db.properties"));
            url = ps.getProperty("url");
            driverClass = ps.getProperty("driverClassName");
            username = ps.getProperty("username");
            password = ps.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(ps.getProperty("url") + driverClass);
    }
}
