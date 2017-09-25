package com.javaweb.exam.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaweb.exam.exception.DBException;

/**
 *
 * @author Nathan.Yang
 * get database connection
 */
public class DBUtil {

    public static Connection getConnection() {
        Connection conn = null;

        try {
            System.out.println("jdc: " + PropertiesUtil.getProperties("jdbc.driver"));
            Class.forName(PropertiesUtil.getProperties("jdbc.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String jdcUrl = PropertiesUtil.getProperties("jdbc.url");

        try {
            conn = DriverManager.getConnection(jdcUrl, PropertiesUtil.getProperties("jdbc.user"), PropertiesUtil.getProperties("jdbc.password"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }

        return conn;
    }

    public static void Close(Connection conn, PreparedStatement stmt, ResultSet res) {
        try {
            if (res != null) {
                res.close();
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

    public static void setAutoCommit(Connection conn, boolean autoCommit) {
        try {
            //org.springframework.web.filter.CharacterEncodingFilter
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }

    }

    public static void commit(Connection conn) {
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }

    }

    public static void rollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        }

    }
}