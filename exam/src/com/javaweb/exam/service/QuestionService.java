package com.javaweb.exam.service;

import java.util.List;

import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;

public interface QuestionService {
    public int getNewId();
    public Question getById(int id);
    public void deleteById(String id);
    public int getQuestionCount(String fuzzy);
    public int save(Question question) throws ParameterException;
    public List<Question> query(Pagination pagination, String questionSort, String fuzzy);
}
