package com.javaweb.exam.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.javaweb.common.FullDate2StringSerializer;
import com.javaweb.common.String2FullDateSerializer;

public class Question {
    private int questionId;
    private String questionContent;
    private String answer;
    private String choiceA;
    private String choiceB;
    private String choiceC;
    private String choiceD;
    private int questionCode;
    private Date createdTime;
    private Date updatedTime;

    @JsonSerialize(using = FullDate2StringSerializer.class)
    public Date getCreatedTime() {
        return createdTime;
    }

    @JsonDeserialize(using = String2FullDateSerializer.class)
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @JsonSerialize(using = FullDate2StringSerializer.class)
    public Date getUpdatedTime() {
        return updatedTime;
    }

    @JsonDeserialize(using = String2FullDateSerializer.class)
    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public int getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(int questionCode) {
        this.questionCode = questionCode;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getChoiceA() {
        return choiceA;
    }

    public void setChoiceA(String choiceA) {
        this.choiceA = choiceA;
    }

    public String getChoiceB() {
        return choiceB;
    }

    public void setChoiceB(String choiceB) {
        this.choiceB = choiceB;
    }

    public String getChoiceC() {
        return choiceC;
    }

    public void setChoiceC(String choiceC) {
        this.choiceC = choiceC;
    }

    public String getChoiceD() {
        return choiceD;
    }

    public void setChoiceD(String choiceD) {
        this.choiceD = choiceD;
    }

    @Override
    public String toString() {
        return "Question [questionId=" + questionId + ", questionContent=" + questionContent + ", answer=" + answer
                + ", choiceA=" + choiceA + ", choiceB=" + choiceB + ", choiceC=" + choiceC + ", choiceD=" + choiceD
                + ", questionCode=" + questionCode + "]";
    }
}
