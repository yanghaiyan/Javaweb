package com.zhengmenbb.book.service;

import com.zhengmenbb.book.dao.UserDao;
import com.zhengmenbb.book.exception.ParameterException;
import com.zhengmenbb.book.exception.ServiceException;
import com.zhengmenbb.book.model.User;
import com.zhengmenbb.book.util.StringUtil;
import com.zhengmenbb.common.BeanFactory;

public class UserService {

    private UserDao userDao = (UserDao)BeanFactory.getInstance().getBean("userDao");

    public User login(String userName, String password) throws ParameterException, ServiceException {

        ParameterException parameterException = new ParameterException();

        if (StringUtil.isEmpty(userName)) {
            parameterException.addErrorField("userName", "User Name is required");
        }
        if (StringUtil.isEmpty(password)) {
            parameterException.addErrorField("password", "Password is require");
        }
        if (parameterException.isErrorField()) {
            throw parameterException;
        }

        User user = userDao.getUserByName(userName);

        if (user == null) {
            throw new ServiceException(1000, "用户不存在");
        }

        if (!password.equals(user.getPassword())) {
            throw new ServiceException(1001, "密码不对");
        }

        return user;
    }

}
