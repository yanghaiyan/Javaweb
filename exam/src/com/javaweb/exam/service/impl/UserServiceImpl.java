package com.javaweb.exam.service.impl;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.dao.UserDao;
import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.exception.ServiceException;
import com.javaweb.exam.model.User;
import com.javaweb.exam.service.UserService;
import com.javaweb.exam.util.DataValiation;
import com.javaweb.exam.util.SessionUtil;

public class UserServiceImpl implements UserService{
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void logout() {
        SessionUtil.removeSession(AppConstants.USER);
    }

    @Override
    public User login(String userName, String password) throws ParameterException,ServiceException{
        DataValiation dataValiation = new DataValiation();
        dataValiation.UserValiation(userName, password);

        User user = userDao.getUserByName(userName);

        if (user == null) {
            throw new ServiceException(1000, "User Or Password Error");
        }

        if (!password.equals(user.getPassword())) {
            throw new ServiceException(1000, "User Or Password Error");
        }

        return user;
    }
}
