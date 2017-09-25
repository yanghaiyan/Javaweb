package com.javaweb.exam.dao.jdbc.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.javaweb.exam.dao.UserDao;
import com.javaweb.exam.model.User;
import com.javaweb.exam.util.StringUtil;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserByName( String userName) {

        if(StringUtil.isEmpty(userName)) {
            return null;
        }
        String sql = "SELECT * FROM user WHERE user_name = ?";
        Object [] args = new Object[] {userName};
        List<User> users = jdbcTemplate.query(sql, args, new RowMapper<User>(){
            @Override
            public User mapRow(ResultSet rs, int arg1) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("user_name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        });

        if (users != null && users.size() > 0) {
            return users.get(0);
        }
        return null;
    }
}
