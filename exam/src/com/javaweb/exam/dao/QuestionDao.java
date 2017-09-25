package com.javaweb.exam.dao;

import java.util.List;

import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;

public interface QuestionDao {
    public int getNewId();
    public int getQuestionCount();
    public Question getById(int id);
    public void add( Question question);
    public void update( Question question);
    public int getFuzzyCount(String fuzzyContent);
    public void updateDelete (String questionId, int deleted);
    public List<Question> query(Pagination pagination, String questionSort);
    public List<Question> queryFuzzy(Pagination pagination, String questionSort, String fuzzy);
}
