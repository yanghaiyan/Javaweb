package com.zhengmenbb.book.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zhengmenbb.book.model.User;
import com.zhengmenbb.common.BeanFactory;
import com.zhengmenbb.common.JDBCAbstractCallBack;
import com.zhengmenbb.common.JDBCTemplate;

public class UserDao {

    @SuppressWarnings("unchecked")
    JDBCTemplate<User> jdbcTemplate = (JDBCTemplate<User>)BeanFactory.getInstance().getBean("jdbcTemplate");


    public User getUserByName(final String userName) {

        if (userName == null || userName.equals("")) {
            return null;
        }


        return jdbcTemplate.queryOne("SELECT * FROM user WHERE user_name = ?", new JDBCAbstractCallBack<User>() {

            @Override
            public User rsToObject(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setAvatar(rs.getString("avatar"));
                user.setMobileNumber(rs.getString("mobile_number"));
                user.setDept(rs.getString("dept"));
                user.setEmail(rs.getString("email"));
                return user;
            }

            @Override
            public void setParams(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, userName);
            }

        });


    }
}
