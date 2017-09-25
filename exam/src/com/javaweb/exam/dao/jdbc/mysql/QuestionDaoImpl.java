package com.javaweb.exam.dao.jdbc.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.javaweb.exam.DataConstants;
import com.javaweb.exam.dao.QuestionDao;
import com.javaweb.exam.model.Pagination;
import com.javaweb.exam.model.Question;

public class QuestionDaoImpl implements QuestionDao {
    @Override
    public int getNewId() {
        // TODO 
        return 0;
    }

    private JdbcTemplate jdbcTemplate;

    @Override
    public int getFuzzyCount(String fuzzyContent) {
        // TODO 
        return 0;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Question rsToQuestion(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getInt(DataConstants.questionData[0]));
        question.setQuestionCode(rs.getInt(DataConstants.questionData[1]));
        question.setQuestionContent(rs.getString(DataConstants.questionData[2]));
        question.setChoiceA(rs.getString(DataConstants.questionData[3]));
        question.setChoiceB(rs.getString(DataConstants.questionData[4]));
        question.setChoiceC(rs.getString(DataConstants.questionData[5]));
        question.setChoiceD(rs.getString(DataConstants.questionData[6]));
        question.setAnswer(rs.getString(DataConstants.questionData[7]));

        return question;
    }
    @Override
    public void add(final Question question) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql="INSERT INTO question (content, choice_a, choice_b, choice_d, choice_c, answer, question_code, deleted)"
                         + "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
                
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, question.getQuestionContent());
                stmt.setString(2, question.getChoiceA());
                stmt.setString(3, question.getChoiceB());
                stmt.setString(4, question.getChoiceC());
                stmt.setString(5, question.getChoiceD());
                stmt.setString(6, question.getAnswer());
                stmt.setInt(7, question.getQuestionCode());
                stmt.setString(8, "0");
                
                return stmt;
            }
        }, keyHolder);

        question.setQuestionId(keyHolder.getKey().intValue());
    }

    @Override
    public void update(final Question question) {
        String sql ="UPDATE question SET content = ?, choice_a = ?, choice_b = ?, choice_c = ?, choice_d = ?, answer = ?, question_code = ? WHERE id = " + question.getQuestionId();
        jdbcTemplate.update(sql,new PreparedStatementSetter()  {
        
        	@Override
            public void setValues(PreparedStatement stmt) throws SQLException {
                stmt.setString(1, question.getQuestionContent());
                stmt.setString(2, question.getChoiceA());
                stmt.setString(3, question.getChoiceB());
                stmt.setString(4, question.getChoiceC());
                stmt.setString(5, question.getChoiceD());
                stmt.setString(6, question.getAnswer());
                stmt.setInt(7, question.getQuestionCode());
            }
        });
    }

    @Override
    public int getQuestionCount() {
        String sql ="SELECT count(*) AS questionCount FROM question WHERE deleted = 0";
        return jdbcTemplate.queryForInt(sql);
    }

    @Override
    public List<Question> query(final Pagination pagination, String questionSort) {

        String sql="SELECT* FROM question WHERE deleted = 0 LIMIT ?,?" + questionSort;
        RowMapper<Question> rowMapper = new RowMapper<Question> (){
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rsToQuestion(rs);
            }};
        List<Question>questions = jdbcTemplate.query(sql,rowMapper);

        return questions;
    }

    @Override
    public Question getById(final int id) {
        String sql = "SELECT * FROM question WHERE id =" + id;
        RowMapper<Question> rowMapper = new RowMapper<Question> (){
            @Override
            public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rsToQuestion(rs);
            }
        };

        return jdbcTemplate.queryForObject(sql, rowMapper);
    }

    @Override
    public void updateDelete (String questionId, int delete) {
        String sql="UPDATE question SET deleted = 1 WHERE id IN (" + questionId + ")";
        jdbcTemplate.update(sql);
    }

    @Override
    public List<Question> queryFuzzy(Pagination pagination, String questionSort, String fuzzy) {
        // do something 
        return null;
    }


}
