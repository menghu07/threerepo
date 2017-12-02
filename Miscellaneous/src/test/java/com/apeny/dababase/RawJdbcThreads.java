package com.apeny.dababase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 两个线程共用一个connection
 * Created by apeny on 2017/12/2.
 */
public class RawJdbcThreads {
    private static String url;
    private static String driverClass;
    private static String username;
    private static String password;
    private static Connection connection;
    private static int value = 1290239;

    public static void main(String[] args) {
        oneExecThenAnotherCommit();
    }

    private static void init() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动线程一个执行命令一个提交命令
     */
    private static void oneExecThenAnotherCommit() {
        init();
        Thread1 thread1 = new Thread1("insert thread");
        Thread2 thread2 = new Thread2("commit");
        thread1.start();
        thread2.start();
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread end......");
    }

    /**
     *
     */
    private static class Thread1 extends Thread {
        Thread1(String name) {
            super(name);
        }

        /**
         * 输入语句
         */
        @Override
        public void run() {
            String sql = "insert into t1 values(?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, value);
                int count = preparedStatement.executeUpdate();
                System.out.println(Thread.currentThread() + " update count: " + count);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static class Thread2 extends Thread {
        Thread2(String name) {
            super(name);
        }

        /**
         *
         */
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                String selectSql = "select * from db1.company";
                String sql = "select * from t1 where i = " + value;
                PreparedStatement preparedStatement = connection.prepareStatement(selectSql);
                ResultSet resultSet = preparedStatement.executeQuery(selectSql);
                while (resultSet.next()) {
                    System.out.println("inserted value " + resultSet.getInt(1));
                }
                connection.commit();
                System.out.println("thread " + Thread.currentThread() + "commit");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    static {
        Properties ps = new Properties();
        try {
            ps.load(new FileInputStream("src/main/resources/properties/db.properties"));
            url = ps.getProperty("url");
            driverClass = ps.getProperty("driverClassName");
            username = ps.getProperty("username");
            password = ps.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ps.getProperty("url") + driverClass);
    }
}
