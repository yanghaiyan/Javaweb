package com.exam.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.javaweb.exam.controller.QuestionController;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;
import com.javaweb.exam.model.User;
import com.javaweb.exam.service.QuestionService;
import com.javaweb.exam.util.PathUtil;
import com.javaweb.exam.util.SessionUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "classpath:question-mvc.xml"})
public class questionControllerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Before
    public void setUp() throws Exception {
        AppContext.setContextPath("/question");
        AppContext appContext = AppContext.getContext();

        User user = new User();
        user.setId(1);
        user.setUserName("yhy");

        HttpSession session = new MockHttpSession();
        session.setAttribute(AppConstants.APP_CONTEXT_USER, user);
        appContext.addObject(AppConstants.APP_CONTEXT_SESSION, session);
        appContext.addObject(AppConstants.APP_CONTEXT_USER, user);
    }

    @After
    public void tearDown() throws Exception {
        AppContext appContext= AppContext.getContext();
        appContext.clear();
    }

    @Test
    public void testSave() {
        QuestionController questionController = (QuestionController) this.applicationContext.getBean("questionController");
        Question question = new Question();

        question.setQuestionCode(36);
        question.setQuestionContent("tempgfhgfhhdhdgedgregr");
        question.setChoiceA("A");
        question.setChoiceB("B");
        question.setChoiceC("A");
        question.setChoiceD("A");
        question.setAnswer("A");
        questionController.save(question);

        Assert.assertEquals(SessionUtil.getSession("SUCESS_FLASH_MESSAGE"), "SAVA_SUCESS");
    }

    @Test
    public void testEdit() {
        QuestionController questionController = (QuestionController) this.applicationContext.getBean("questionController");
        ModelAndView modelAndView = questionController.edit(10);
        RedirectView redirectView = (RedirectView)modelAndView.getView();
        Assert.assertEquals(PathUtil.getFullPath("question/edit"), redirectView.getUrl());
    }

    @Test
    public void testQuery() {

        QuestionService questionservice = (QuestionService) this.applicationContext.getBean("QuestionService");
        Pagination pagination = new Pagination();
        pagination.setCurrentPage(1);
        pagination.setPageSize(5);
        List<Question> questions = questionservice.query(pagination, "ASC", "");
        //Assert.assertNotSame(pagination.getPageSize(), questions.size());
    }

    @Test
    public void testDeleted() {
        QuestionController questionController = (QuestionController) this.applicationContext.getBean("questionController");
        questionController.delete(10, "1,3", 1);
        Assert.assertEquals(SessionUtil.getSession(AppConstants.SUCCESS_FLASH_MESSAGE), "Delete SUCCESSFUL");
    }
}
