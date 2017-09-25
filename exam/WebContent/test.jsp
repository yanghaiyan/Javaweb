<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.javaweb.exam.util.PropertiesUtil" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title> jquery完美实现textarea输入框限制字数</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8" />
 <script src="<%=PropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js" ></script>
<script type="text/javascript">
 function validate() {
		    var isSumbit = true;
		    var userNameValue = document.getElementById("userName").value;
		    var passwordValue = document.getElementById("password").value;
		    var emailValue = document.getElementById("email").value;
		    var repeatPasswordValue = document.getElementById("repeatPassword").value;
		    var passwordIsNull = false;
		    var userNameIsNull = false;
		    
		    if (!userNameValue) {
		        userNameIsNull = true;
		        isSumbit = false;
		    }
		    
		    if (!passwordValue) {
		        passwordIsNull = true;
		        isSumbit = false;
		    }

	        if (!passwordIsNull && !userNameIsNull) {
                if(repeatPasswordValue !== passwordValue) {
                  isSumbit = false;
                }
	         }
	        
	        if(!emailValue) {
	              isSumbit = false;
	        }
	       
		   if (!document.getElementById("termsAgreeable").checked) {
		        isSumbit = false;
		    }

		    return isSumbit;
		}

</script>
</head>
<body>
<form method="post" action="register" onsubmit=false >
  <div>
      <label for="userName">Username:</label>
      <input type="text" id="userName" name="userName" />
  </div>
  <div>
      <label for="password">Password:</label>
      <input type="password" id="password" name="password" />
  </div>
  <div>
      <label for="repeatPassword">Repeat Password:</label>
      <input type="password" id="repeatPassword" name="repeatPassword" />
   </div>
   <div>
       <label for="email">Email:</label>
       <input type="text" id="email" name="email" />
   </div>
   <div>
       <input id="termsAgreeable" name="termsAgreeable" type="checkbox" />
       <label for="termsAgreeable">I agree to the <a href="terms-of-use" target="_blank">terms of use</a>.</label>
   </div>
   <input type="submit" value="Submit" />
</form>
</body>
</html>