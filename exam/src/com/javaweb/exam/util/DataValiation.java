package com.javaweb.exam.util;

import com.javaweb.exam.exception.ParameterException;
import com.javaweb.exam.model.Question;

public class DataValiation {

    public void questionValiation(Question question) throws ParameterException {
        ParameterException parameterException = new ParameterException();

        if (StringUtil.isEmpty(question.getQuestionContent())) {
            parameterException.addErrorFields("Content", "Content is required");
        }

        if (StringUtil.isEmpty(question.getChoiceA())) {
            parameterException.addErrorFields("choiceA", "choiceA is required");
        }

        if (StringUtil.isEmpty(question.getChoiceB ())) {
            parameterException.addErrorFields("choiceB", "choiceB is required");
        }

        if (StringUtil.isEmpty(question.getChoiceC())) {
            parameterException.addErrorFields("choiceB", "choiceB is required");
        }

        if (StringUtil.isEmpty(question.getChoiceD())) {
            parameterException.addErrorFields("choiceC", "choiceC is required");
        }

        if (StringUtil.isEmpty(question.getAnswer())) {
            parameterException.addErrorFields("answer", "Answer is required");
        }

        int Contentsize = Integer.parseInt(PropertiesUtil.getProperties("questionContentSize"));
        System.out.println(question.getQuestionContent().length());
        if(question.getQuestionContent().length() > Contentsize){

            parameterException.addErrorFields("Content", "Content exceeds the maximum length");
        }

        int choicetSize = Integer.parseInt(PropertiesUtil.getProperties("questionChoiceSize"));
        if (question.getChoiceA().length() > choicetSize) {
            parameterException.addErrorFields("choiceA", "choiceA exceeds the maximum length");
        } else if (question.getChoiceB().length() > choicetSize) {
            parameterException.addErrorFields("choiceB", "choiceB exceeds the maximum length");
        } else if (question.getChoiceC().length() > choicetSize) {
            parameterException.addErrorFields("choiceC", "choiceC exceeds the maximum length");
        } else if (question.getChoiceD().length() > choicetSize) {
            parameterException.addErrorFields("choiceD", "choiceD exceeds the maximum length");
        }

        if (parameterException.isErrorFiled()) {
            throw parameterException;
        }
    }

    public void UserValiation(String userName, String password) throws ParameterException {
        ParameterException parameterException = new ParameterException();

        if (StringUtil.isEmpty(userName)) {
            parameterException.addErrorFields("userName", "UserName is required");
        }

        if (StringUtil.isEmpty(password)) {
            parameterException.addErrorFields("password", "password is required");
        }
        if(parameterException.isErrorFiled()) {
            throw parameterException;
        }

    }
}
