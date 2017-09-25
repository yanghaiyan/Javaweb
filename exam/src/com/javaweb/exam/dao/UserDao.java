package com.javaweb.exam.dao;

import com.javaweb.exam.model.User;

public interface UserDao {
    public User getUserByName( String userName);
}