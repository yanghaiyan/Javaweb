package com.javaweb.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.AppContext;
import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.exception.ServiceException;
import com.javaweb.exam.model.JsonData;
import com.javaweb.exam.model.User;
import com.javaweb.exam.service.UserService;
import com.javaweb.exam.util.StringUtil;

@Controller
@RequestMapping(AppConstants.APP_URL_USER_PREFIX)
public class UserController extends BaseController{
    private final String LOGIN_JSP = "login";
    private final String QUESTION_SHOW_PATH = "question/show";

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "go", defaultValue = "") String go) {

        User user = this.getUser();
        ModelAndView modelAndView = new ModelAndView();

        if (user != null ) {
            modelAndView.setView(this.getRedirectView(QUESTION_SHOW_PATH));
        } else {
            modelAndView.addObject("go", go);
            modelAndView.setViewName(LOGIN_JSP);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonData saveLogin(
            @RequestParam(value = "userName", defaultValue = "") String userName,
            @RequestParam(value = "password", defaultValue = "") String password,
            @RequestParam(value = "queryString", defaultValue = "") String queryString,
            @RequestParam(value = "go", defaultValue = "") String go
            ) {
        JsonData loginData = new JsonData();

        try {
            User user = null;
            user = userService.login(userName, password);
            user.setPassword(null);
            this.addSession(AppConstants.USER, user);

            if (!StringUtil.isEmpty(queryString)) {
                if (queryString.startsWith("#")) {
                    queryString = queryString.substring(1);
                }
                go = go + "?" + queryString;
            }

            String redirectView = StringUtil.isEmpty(go) ? "/exam/page/"+QUESTION_SHOW_PATH: (AppContext.getContextPath() + "/" + go);
            loginData.setStatus(1);
            loginData.setData(redirectView);
        } catch (ParameterException parameterException) {
            loginData.setStatus(0);
            loginData.setData(parameterException.getMessage());
        } catch (ServiceException serviceException) {
            loginData.setStatus(0);
            loginData.setData(serviceException.getMessage() + "[" + serviceException.getCode() + "]");
        }

        return loginData;
    }

    @RequestMapping(value = "/logout", method= RequestMethod.GET)
    public ModelAndView logout() {
        ModelAndView modelAndView = new ModelAndView();
        this.invalidate();
        modelAndView.setView(this.getRedirectView("user/" + LOGIN_JSP));
        return modelAndView;
    }
}
