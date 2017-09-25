<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.javaweb.exam.AppConstants" %>
<%@page import="com.javaweb.exam.model.User" %>
<%@page import="com.javaweb.exam.model.Question" %>
<%@page import="com.javaweb.exam.util.StringUtil" %>
<%@page import="com.javaweb.exam.util.PathUtil" %>
<%@page import="com.javaweb.exam.util.PropertiesUtil" %>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Created question</title>
    <link href="<%=PropertiesUtil.getStaticUrl()%>/css/style.css" rel="stylesheet" type="text/css">
    <link href="<%=PropertiesUtil.getStaticUrl()%>/css/creatquestion.css" rel="stylesheet" type="text/css">
    <script src="<%=PropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js" >
    </script>
    <script type="text/javascript">
    </script>
  </head>
  <body>
    <div id="varpperCreatQuestion">
       <div class="header">
        <div class="header-left"></div>
        <div class="header-main">Online Exam Web System</div>
        <div class="header-right">
          <div class="language">中文</div>
            <div class="user-icon"></div>
            <div class="user">
            <% User user = (User) session.getAttribute(AppConstants.USER); %>
            <div class="user-name"><%=user.getUserName() %></div>
            <div class="logout">Logout</div>
          </div>
        </div>
      </div>

      <div class="breadcrumb" >
      <div class="question-manager"><a style="color:#2E4358"  href="<%=PathUtil.getFullPath("question/show")%> " >Question Management</a></div>
      <div class="exam-manager">Exam Management</div>
      </div>
      <%
        Question question = (Question)request.getAttribute("question");
        String questionCode = StringUtil.toQuestionId(question.getQuestionCode());
      %>

      <div class="head-list-show">Question Management > Question List > <span><%=questionCode%></span></div>
      <div class="main" >
        <div class="info-question">
          <form class="question-form" name="question-form" id="editQuestionform" method="post">
            <div class="title">
              <span>QuestionID:</span>
              <input name="questionId" id="questionId" value=<%=question.getQuestionId() %> style="display: none;"/>
              <input class="title-input" name="displayCode" id="displayCode" value="<%=questionCode%>" readonly="readonly" />
              <input name="questionCode" id="questionCode" value="<%=question.getQuestionCode()%>" style="display: none;" />
            </div>

            <div class="content">
               <span>Question:</span>

               <div class="question-text"> 
                 <span id="question-content">
                   <%if (question.getQuestionId() > 0) {%>
                   <%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getQuestionContent()))%>
                   <%} else {%>
                   Please input content
                   <%} %>
                 </span> 
                 
                 <textarea class="question-textarea" id="questionContent" name="questionContent" rows="4" cols="40"><%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getQuestionContent())) %></textarea>
                 <span class="errorMessage" id="contentErrorMessage"> Content is required</span>
               </div>
              </div>
              
            <div class="question-choice">
                 <div class="answer-title">Answer:</div>
                 <div class="question-choice-content">
                   <div class="one-choice">
                     <div id="icon-1" class="choice-icon">
                       <img id="answerA" class="oneChoice" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Unselected_16x16.png">
                     </div>
                     <span>A</span>
                     <input type="text" name="choiceA" id="choiceA"  class="choice-input" value="<%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getChoiceA())) %>" />
                     <span class="errorMessage" id="choiceAErrorMessage"> Choice is required</span>
                   </div>
  
                   <div class="one-choice">
                     <div id="icon-2" class="choice-icon">
                       <img id="answerB" class="oneChoice" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Unselected_16x16.png" />
                     </div>
                     
                     <span >B</span>
                     <input type="text" class="choice-input" name="choiceB" id="choiceB" value="<%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getChoiceB()))%>" />
                     <span class="errorMessage" id="choiceBErrorMessage"> Choice is required</span>
                   </div>
  
                   <div class="one-choice">
                     <div id="icon-3" class="choice-icon">
                       <img id="answerC" class="oneChoice" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Unselected_16x16.png">
                     </div>
                     <span>C</span>
                     <input type="text" name="choiceC" class="choice-input" id="choiceC" value="<%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getChoiceC()))%>" />
                     <span class="errorMessage" id="choiceCErrorMessage"> Choice is required</span>
                   </div>
  
                   <div class="one-choice">
                   <div id="icon-4" class="choice-icon">
                     <img class="oneChoice" id="answerD" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Unselected_16x16.png">
                   </div>
                   <span>D</span>
                   <input type="text" class="choice-input" name="choiceD" id="choiceD" value="<%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getChoiceD()))%>" />
                   <span class="errorMessage" id="choiceDErrorMessage"> Choice is required</span>
                 </div>
                </div>
              </div>

            <div class="form-footer">
              <input type="button" id="save" value="Save" />
              <input type="button" id="cannel" value="Cannel"/>
            </div>

            <input type="text" class="answer" name="answer" id="answer" value="<%=StringUtil.htmlEncode(StringUtil.doWithNull(question.getAnswer())) %>" />
          </form>
        </div>
      </div>
    </div>
  </body>
  <script type="text/javascript">
  $(function() {  
      var unChoicekUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Unselected_16x16.png";
      var choiceUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Radio_Selected_16x16.png";
      var choice =["A", "B", "C", "D"];
      var params;

      if ($("#answer").val() === ""){
           $("#answer").val("A");
           $("#answerA").attr("src", choiceUrl);
           $("#choiceA").css("background-color", "#D2DAE3");
      } else{
          whereChoice();
      }

      function whereChoice() {
          var i = 0;
          while(true) {
            if ($("#answer").val() === choice[i]) {
                $("#answer" + choice[i]).attr("src", choiceUrl);
                $("#choice" + choice[i]).css("background-color", "#D2DAE3");
                break;
            }
            i++;
          }
      }
      
      function questionData() {
          parArr = {};

          var questionId = $('input[name=questionId]').val();
          parArr.questionId = questionId;

          var questionCode = $('input[name=questionCode]').val();
          parArr.questionCode = questionCode;

          var questionContent = $('textarea[name=questionContent]').val();
          parArr.questionContent = questionContent;

          var choiceB = $('input[name=choiceB]').val();
          parArr.choiceB = choiceB;
          var choiceA = $('input[name=choiceA]').val();
          parArr.choiceA = choiceA;
          var choiceD = $('input[name=choiceD]').val();
          parArr.choiceD = choiceD;
          var choiceC = $('input[name=choiceC]').val();
          parArr.choiceC = choiceC;

          var answer = $('input[name=answer]').val();
          parArr.answer = answer;
          params = JSON.stringify(parArr);
     }

    $(".oneChoice").click(function() {
       $(".oneChoice").each(function(index) {
          $(this).click(function() {
             $(".oneChoice").attr("src", unChoicekUrl);
             $(".choice-input").css("background-color", "#FFFFFF");
             $(this).attr("src", choiceUrl);
             $("#choice" + choice[index]).css("background-color", "#D2DAE3");
             $("#answer").val(choice[index]);
          })
    });
  });

    $("#save").click(function() { 
      var isSumbit = true; 
      $('input').each(function() {
        var thisInput = "#"+ $(this).attr("name") + "ErrorMessage";
        if ($(this).val() != "") {
           $(thisInput).css("visibility", "hidden");
        } else {
           $(thisInput).css("visibility", "visible");
           isSumbit = false;
        }
      });

      if ($("#questionContent").val() != "") {
          $("#contentErrorMessage").css("visibility", "hidden");
      } else {
          $("#contentErrorMessage").css("visibility", "visible");
          isSumbit = false;
      }

      if (isSumbit) {
          questionData();
          editSumbit();
      }
    });

    $("#cannel").click(function() {
      location.href = "<%=PathUtil.getFullPath("question/show")%>";
    });

    $("#questionContent").keyup(function() {
       var len = $(this).val().length;
       var contentSize = <%=PropertiesUtil.getProperties("questionChoiceSize")%>;
  
       if(len > contentSize) {
        $(this).val($(this).val().substring(0, contentSize));
       }
    });

     $(".choice-input").keyup(function() {
        var len = $(this).val().length;
        var contentSize = <%=PropertiesUtil.getProperties("questionChoiceSize")%>;

        if(len > contentSize){
         $(this).val($(this).val().substring(0, contentSize));
        }
       });

     function editSumbit(){
         $.ajax ({
            type: "POST",
            url: "/exam/page/question/save",
            contentType: "application/json",
            dataType: "json", 
            data: params,
            success: function(data){
              if (data.status == 1) {
                 location.href="/exam/page/" + data.data;
              }
            }
        });
     }
});

  </script>
</html>

