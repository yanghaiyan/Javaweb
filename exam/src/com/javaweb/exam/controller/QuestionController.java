package com.javaweb.exam.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.javaweb.exam.AppConstants;
import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.model.JsonData;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;
import com.javaweb.exam.service.QuestionService;

@Controller
@RequestMapping(AppConstants.APP_URL_QUESTION_PREFIX)
public class QuestionController extends BaseController {
    private static final String EDIT_QUESTION_JSP = "question/edit";
    private static final String QUESTION_SHOW_JSP = "question/show";
    private static final String QUESTION_SHOW_PAGE = "question/show";

    @Autowired
    private  QuestionService questionService;

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonData save(@RequestBody Question question) {
        JsonData jsonData = new JsonData();

        try {
            questionService.save(question);

            jsonData.setStatus(1);
            jsonData.setData(QUESTION_SHOW_PAGE);
            this.addSession("SUCESS_FLASH_MESSAGE", "SAVA_SUCESS");
        } catch (ParameterException parameterException) {
            jsonData.setStatus(0);
            jsonData.setData(parameterException.getMessage());
        }

        return jsonData;
    }

    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int id){
        Question question = null;
        ModelAndView modelAndView = new ModelAndView();

        if (id == 0) {
            int questionCode = questionService.getNewId();
            question = new Question();
            question.setQuestionCode(questionCode);
        } else {
            question = questionService.getById(id);
        }

        modelAndView.addObject("question", question);
        modelAndView.setViewName(EDIT_QUESTION_JSP);
        return modelAndView;
}

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public ModelAndView show(@RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
                             @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                             @RequestParam(value = "questionSort", defaultValue = "ASC") String questionSort,
                             @RequestParam(value = "fuzzyContent", defaultValue = "") String fuzzyContent
            ) {

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);

        List<Question> questions = new ArrayList<Question>();
        ModelAndView modelAndView = new ModelAndView();

        questions = questionService.query(pagination, questionSort, fuzzyContent);
        int questionCount = questionService.getQuestionCount(fuzzyContent);
        pagination.setTotalCount(questionCount);

        modelAndView.addObject("fuzzyQuesy", fuzzyContent);
        modelAndView.addObject("pagination", pagination);
        modelAndView.addObject("questions", questions);
        modelAndView.addObject("pageSize", pagination.getPageSize());
        modelAndView.addObject("questionSort", questionSort);
        modelAndView.setViewName(QUESTION_SHOW_JSP);

        return modelAndView;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(
                        @RequestParam(value = "deletePage", defaultValue = "5") int pageSize,
                        @RequestParam(value = "deletedArray", defaultValue = "1") String deletedArray,
                        @RequestParam(value = "deleteCurrentPage", defaultValue = "1") int currentPage
                    ) {

        ModelAndView modelAndView = new ModelAndView();
        questionService.deleteById(deletedArray);

        Pagination pagination = new Pagination();
        pagination.setCurrentPage(currentPage);
        pagination.setPageSize(pageSize);

        this.addSession(AppConstants.SUCCESS_FLASH_MESSAGE, "Delete SUCCESSFUL");
        modelAndView.setView(this.getRedirectView(QUESTION_SHOW_PAGE +"?pageSize=" + pageSize+"&currentPage=" + currentPage));

        return modelAndView;
    }
}
