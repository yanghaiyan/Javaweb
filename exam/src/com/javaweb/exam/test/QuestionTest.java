package com.javaweb.exam.test;

import com.javaweb.exam.dao.mybatis.QuestionDaoImpl;
import com.javaweb.exam.util.StringUtil;

public class QuestionTest {

    public void test() {
        QuestionDaoImpl imp= new QuestionDaoImpl();
        String fuzzy = "that";
        String fuzzyContent = StringUtil.toFuzzySql(fuzzy);
        System.out.println(imp.getFuzzyCount(fuzzyContent));
    }
}
