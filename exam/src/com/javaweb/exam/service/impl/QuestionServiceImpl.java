package com.javaweb.exam.service.impl;

import java.util.List;

import com.javaweb.exam.dao.QuestionDao;
import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;
import com.javaweb.exam.service.QuestionService;
import com.javaweb.exam.util.DataValiation;
import com.javaweb.exam.util.StringUtil;

public class QuestionServiceImpl implements QuestionService{

    private QuestionDao questionDao;

    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public int getNewId() {
        return questionDao.getNewId();
    }

    @Override
    public int save(Question question) throws ParameterException {
        DataValiation dataValiation = new DataValiation();
        dataValiation.questionValiation(question);

        if (question.getQuestionId() > 0) {
            questionDao.update(question);
        } else {
            questionDao.add(question);
        }

        return question.getQuestionId();
    }

    @Override
    public int getQuestionCount(String fuzzyContent) {
        if (StringUtil.isEmpty(fuzzyContent)) {
            return questionDao.getQuestionCount();
        }

        return questionDao.getFuzzyCount(StringUtil.toFuzzySql(fuzzyContent));
    }

    @Override
    public Question getById(int id) {

        return questionDao.getById(id);
    }

    @Override
    public void deleteById(String id) {

        questionDao.updateDelete(id,1);
    }

    @Override
    public List<Question> query(Pagination pagination, String questionSort, String fuzzy) {
        if (StringUtil.isEmpty(fuzzy)) {
            return questionDao.query(pagination, questionSort);
        }

        return this.questionDao.queryFuzzy(pagination, questionSort, StringUtil.toFuzzySql(fuzzy));
    }
}
