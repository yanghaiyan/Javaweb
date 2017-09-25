package com.javaweb.exam.service;

import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.exception.ServiceException;
import com.javaweb.exam.model.User;
/*
 * Verify that the user name and password are correct
 */
public interface UserService {
    public User login(String userName, String password) throws ParameterException,ServiceException;
    public void logout();
}
