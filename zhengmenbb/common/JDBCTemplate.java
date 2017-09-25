package com.zhengmenbb.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zhengmenbb.book.exception.DBException;
import com.zhengmenbb.book.util.DBUtil;

public class JDBCTemplate<T> {


    public List<T> query(String sql, JDBCCallBack<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<T> data = new ArrayList<T>();
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            jdbcCallback.setParams(stmt);
            rs = stmt.executeQuery();
            while(rs.next()) {
               T object = jdbcCallback.rsToObject(rs);
               data.add(object);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return data;
    }

    public T queryOne(String sql, JDBCCallBack<T> jdbcCallback) {

        List<T> data = query(sql, jdbcCallback);
        if (data != null && !data.isEmpty()) {
            return data.get(0);
        }
        return null;
    }

    public int update(String sql, JDBCCallBack<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            jdbcCallback.setParams(stmt);
            count = stmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(null, stmt, conn);
        }
        return count;
    }

    public int update(String sql) {
        return this.update(sql, new JDBCAbstractCallBack<T>() {

        });

    }

    public int insert(String sql, JDBCCallBack<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int id = 0;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            jdbcCallback.setParams(stmt);

            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
               id = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return id;
    }

    public void insertWithoutKey(String sql, JDBCCallBack<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            jdbcCallback.setParams(stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(null, stmt, conn);
        }

    }

    public int getCount(String sql, JDBCCallBack<T> jdbcCallback) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DBException();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
        return count;
    }

    public int getCount(String sql) {
        return this.getCount(sql, new JDBCAbstractCallBack<T>() {
        });


    }
}
