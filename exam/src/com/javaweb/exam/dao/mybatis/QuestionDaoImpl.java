package com.javaweb.exam.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.javaweb.exam.dao.QuestionDao;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;

public class QuestionDaoImpl extends SqlSessionDaoSupport implements QuestionDao{
    protected static final String SQL_ID_ADD = ".add";
    protected static final String SQL_ID_UPDATE = ".update";
    private static final String CLASS_NAME = Question.class.getName();

    @Override
    public void add(Question question) {
        getSqlSession().insert(CLASS_NAME + SQL_ID_ADD, question);
    }

    @Override
    public void update(Question question) {
        getSqlSession().update(CLASS_NAME + SQL_ID_UPDATE, question);
    }

    @Override
    public int getQuestionCount() {
        return getSqlSession().selectOne(CLASS_NAME + ".getQuestionCount",null);
    }

    public void updateId ()
    {
        getSqlSession().update(CLASS_NAME + ".updateId");
    }

    @Override
    public int getNewId() {
        updateId ();
        return getSqlSession().selectOne(CLASS_NAME + ".getNewId");
    }

    @Override
    public int getFuzzyCount(String fuzzyContent) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("fuzzyContent", fuzzyContent);
        return getSqlSession().selectOne(CLASS_NAME + ".getFuzzyCount",params);
    }

    @Override
    public List<Question> query(Pagination pagination, String questionSort) {
        pagination.setTotalCount(this.getQuestionCount());

        if (pagination.getCurrentPage() > pagination.getPageCount()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", pagination.getOffset());
        params.put("pageSize", pagination.getPageSize());
        params.put("questionSort", questionSort);
        return getSqlSession().selectList(CLASS_NAME + ".query", params);
    }

    @Override
    public Question getById(int id) {
        return getSqlSession().selectOne(CLASS_NAME + ".getById", id);
    }

    @Override
    public void updateDelete(String questionId, int deleted) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deleted", deleted);
        params.put("questionId", questionId);
        getSqlSession().update(CLASS_NAME + ".updateDeleted", params);
    }

    @Override
    public List<Question> queryFuzzy(Pagination pagination, String questionSort, String fuzzy) {

        pagination.setTotalCount(this.getQuestionCount());
        if (pagination.getCurrentPage() > pagination.getPageCount()) {
            pagination.setCurrentPage(pagination.getPageCount());
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("offset", pagination.getOffset());
        params.put("pageSize", pagination.getPageSize());
        params.put("questionSort", questionSort);
        params.put("fuzzy",fuzzy);

        return getSqlSession().selectList(CLASS_NAME + ".queryFuzzy", params);
    }
}
