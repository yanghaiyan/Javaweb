<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.javaweb.exam.util.PathUtil" %>
<%@ page import="com.javaweb.exam.util.PropertiesUtil" %>
<%@ page import="com.javaweb.exam.model.User" %>
<%@ page import="com.javaweb.exam.AppConstants" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
     <link href="<%=PropertiesUtil.getStaticUrl()%>/css/style.css" rel="stylesheet" type="text/css">
     <script src="<%=PropertiesUtil.getStaticUrl()%>/js/common.js" ></script>
     <script src="<%=PropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js" ></script>
  </head>
  <body>
      <div class="header">
        <div class="header-left"></div>
        <div class="header-main">Online Exam Web System</div>
        <div class="header-right">
          <div class="language">中文</div>
          <div class="user-icon"></div>

            <div class="user">
              <% User user = (User) session.getAttribute(AppConstants.USER); %>
              <div class="user-name"><%=user.getUserName() %></div>
              <div class="logout"><a href="<%=PathUtil.getFullPath("user/logout")%>" >Logout</a></div>
            </div>
        </div>
      </div>
      <div class="breadcrumb" >
        <div class="question-manager"><a href="#">Question Management</a></div>
        <div class="exam-manager">Exam Management</div>
      </div>
  </body>
</html>