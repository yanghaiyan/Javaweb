package com.zhengmenbb.book.controller;

import java.util.Map;

import com.zhengmenbb.book.Constants;
import com.zhengmenbb.book.exception.ParameterException;
import com.zhengmenbb.book.exception.ServiceException;
import com.zhengmenbb.book.model.User;
import com.zhengmenbb.book.service.UserService;
import com.zhengmenbb.book.util.StringUtil;
import com.zhengmenbb.common.BeanFactory;
import com.zhengmenbb.common.ModelAndView;

public class UserController {

    private final String LOGIN_PAGE = "/WEB-INF/jsp/login.jsp";

    private UserService userService = (UserService)BeanFactory.getInstance().getBean("userService");

    public ModelAndView login(Map<String, String> request, Map<String, Object> session) {

        User user = (User)session.get(Constants.USER);

        ModelAndView modelAndView = new ModelAndView();

        if (user != null) {
            modelAndView.setView("hadLogin");
        } else {
            String go = request.get("go");
            if (StringUtil.isEmpty(go)) {
                go = "";
            }
            modelAndView.addObject("go", go);
            modelAndView.setView("input");
        }

        return modelAndView;
    }

    public ModelAndView saveLogin(Map<String, String> request, Map<String, Object> session)  {
        String userName = request.get("userName");
        String password = request.get("password");

        ModelAndView modelAndView = new ModelAndView();

        try {
            User user = null;
            user = userService.login(userName, password);
            user.setPassword(null);

            modelAndView.addSessionAttribute(Constants.USER, user);

            String go = request.get("go");

            String queryString = request.get("queryString");

            if (!StringUtil.isEmpty(queryString)) {
                if (queryString.startsWith("#")) {
                    queryString = queryString.substring(1);
                }
                go = go + "?" + queryString;
            }

            modelAndView.setRedirect(true);

            String uri = StringUtil.isEmpty(go) ? "mybook.action" :  go;
            modelAndView.setView(uri);


        } catch (ParameterException parameterException) {
            Map<String, String> errorFields = parameterException.getErrorFields();
            modelAndView.addObject(Constants.ERROR_FIELDS, errorFields);
            modelAndView.setView(LOGIN_PAGE);
        } catch (ServiceException serviceException) {
            modelAndView.addObject(Constants.TIP_MESSAGE, serviceException.getMessage() + "[" + serviceException.getCode() + "]");
            modelAndView.setView(LOGIN_PAGE);
        }
        return modelAndView;
    }
}
