package com.exam.controller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.AppContext;
import com.javaweb.exam.controller.UserController;
import com.javaweb.exam.util.PathUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:question-mvc.xml"})
public class UserControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void setUp() throws Exception {
        AppContext.setContextPath("/question");
        AppContext appContext = AppContext.getContext();
        appContext.addObject(AppConstants.APP_CONTEXT_SESSION, new MockHttpSession());
    }

    @After
    public void tearDown() throws Exception {
        AppContext appContext= AppContext.getContext();
        appContext.clear();
    }

/*
    @Test
    public void testSaveLogin() {
        UserController userController = (UserController) this.applicationContext.getBean("userController");
        JsonData loginData = userController.saveLogin("yhy", "123456", "", "");
        String url = loginData.getData();
        Assert.assertEquals("/exam/page/question/show", url);
        Assert.assertNotNull(SessionUtil.getSession(AppConstants.USER));
    }
*/
    @Test
    public void testLoginout() {
        UserController userController = (UserController) this.applicationContext.getBean("userController");
        ModelAndView modelAndView = userController.logout();
        RedirectView redirectView = (RedirectView)modelAndView.getView();
        Assert.assertEquals(PathUtil.getFullPath("user/login"), redirectView.getUrl());
    }

}
