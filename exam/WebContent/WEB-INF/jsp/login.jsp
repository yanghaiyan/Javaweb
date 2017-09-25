<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.javaweb.exam.AppConstants"%>
<%@page import="javax.swing.text.Document" %>
<%@page import="java.util.HashMap" %>
<%@page import="java.util.Map" %>
<%@page import="com.javaweb.exam.util.PathUtil" %>
<%@page import="com.javaweb.exam.util.PropertiesUtil" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Welcome Login</title>
    <link href="<%=PropertiesUtil.getStaticUrl()%>/css/style.css" rel="stylesheet" type="text/css">
    <link href="<%=PropertiesUtil.getStaticUrl()%>/css/login.css" rel="stylesheet" type="text/css">
    <script src="<%=PropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js" ></script>
    <script src="<%=PropertiesUtil.getStaticUrl()%>/js/login.js" >
    </script>
  </head>
  <body>
    <div class="all-login">
      <div class="left-login">
        <div class="left-login-image"></div>
        <div class="left-login-font">
          <label>Online Exam Web System</label>
        </div>
      </div>

      <div class="main-login">
        <span class="main-login-welcome">Welcome to login!</span>

        <div class="login-content">

          <div class="loginError" id="loginError" style="visibility: hidden;">
          </div>

          <form id="loginForm" class="loginForm" method="post">
            <div class="login-info">
              <span class="user"></span>
              <input name="userName" id="userName" type="text" placeholder="userName" class="login-input" />
              <label id="errorUserName" class="errorMessage"></label>
              <input type="hidden" name="go" value="<%=request.getAttribute("go")%>">
              <input type="hidden" name="queryString" id="queryString"/>
            </div>

            <div class="login-info">
              <span class="pwd"></span>
              <input name="password" id="password" type="password" placeholder="password" class="login-input" />
              <label id="errorPassword" class="errorMessage"></label>
            </div>

            <div class="login-oper">
              <input name="" type="button" value="Login" id="login" class="login-btn" />
            </div>

            <div class="login-oper-check">
              <div style="float: left;">
                <img id ="remeber" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_RemeberMe_Unselect_12X12.png.png">
              </div>

              <div class="login-oper-left">Remember me</div>&nbsp;
              <div class="login-oper-right">Forgot password?</div>
            </div>
          </form>
        </div>
      </div>
      <div class="bottom-login">
        <div class="bottom-login-font">Copyright @2014 Augmentum, Inc, All Rights Reserved</div>
      </div>
    </div>
  </body>
  <script type="text/javascript">
  $("#remeber").click(
     function() {
        var UnSelect = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_RemeberMe_Unselect_12X12.png.png";
        var select = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_RemeberMe_Select_12x12.png";
        var src = $("#remeber").attr("src");
        if (src === UnSelect) {
           $("#remeber").attr("src",select);
        } else {
          $("#remeber").attr("src",UnSelect);
        }
    });
  </script>
</html>
