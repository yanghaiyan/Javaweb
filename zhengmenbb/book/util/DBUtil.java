package com.zhengmenbb.book.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.zhengmenbb.book.exception.DBException;

public class DBUtil {

    public static Connection getConnection() {
       //1:加载驱动
        try {
            Class.forName(PropertyUtil.getProperty("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //2：得到connection对象
        String jdcUrl = PropertyUtil.getProperty("jdbc.url");
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(jdcUrl, PropertyUtil.getProperty("jdbc.user"), PropertyUtil.getProperty("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();

        }
        return conn;
    }

    public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }

            if (stmt != null) {
                stmt.close();
            }

            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
