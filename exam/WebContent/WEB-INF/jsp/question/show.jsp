<%@ page import="com.javaweb.exam.util.StringUtil"%>
<%@ page import="javax.swing.text.Document" %>
<%@ page import="com.javaweb.exam.model.Pagination" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.javaweb.exam.AppConstants" %>
<%@ page import="com.javaweb.exam.model.Question" %>
<%@ page import="com.javaweb.exam.util.PathUtil" %>
<%@ page import="com.javaweb.exam.util.PropertiesUtil" %>
<%@ taglib uri="/WEB-INF/block.tld" prefix="block"%> 
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>Question List</title>
     <link href="<%=PropertiesUtil.getStaticUrl()%>/css/style.css" rel="stylesheet" type="text/css">
     <link href="<%=PropertiesUtil.getStaticUrl()%>/css/question.css" rel="stylesheet" type="text/css">
     <script src="<%=PropertiesUtil.getStaticUrl()%>/js/common.js" ></script>
     <script src="<%=PropertiesUtil.getStaticUrl()%>/js/jquery-1.10.2.min.js" ></script>
     <script type="text/javascript">
     </script>
  </head>
  <body>
    <div class="disable-screen" id="deleteQuestionMark" ></div>
    <div class="pop-win" id="deleteWin">
        <div class="pop-win-img"><img id="closeWin" src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_Close_16x16.png"></div>
        <span>Are you sure delete the selected question</span>
        <div class="pop-win-input">
            <input type="button" value="YES" id="pop-win-yes">
            <input type="button" value="No" id="pop-win-No" />
        </div>
    </div>
    <div id="varpper">
      <div id="errorFlashMessage"></div>
      <block:display name="headBlock" />

       <%
         String fuzzyQuesy = (String) request.getAttribute("fuzzyQuesy");
         Pagination pagination = (Pagination)request.getAttribute("pagination");
         String questionSort = (String)request.getAttribute("questionSort");
         String showQuestionUrl = PathUtil.getFullPath("question/show?");
         if (!StringUtil.isEmpty(fuzzyQuesy)) {
            showQuestionUrl = PathUtil.getFullPath("question/show?fuzzyQuesy=" + fuzzyQuesy + "&");
         }

         int pageSize = (Integer)request.getAttribute("pageSize");
         int pageToalNum =pagination.getPageCount();
       %>
      <div id="questionMain" class="main" >
        <div class="left">
          <div class="list-question">Question List</div>
          <div class="creat-question"><a style="color: #0A0A0A;"href="<%=PathUtil.getFullPath("question/edit/0")%>">Creat question List</a></div>
       </div>
       
        <div class="content">
          <div class="search">
          <form id="fuzzyQuery" action="<%=PathUtil.getFullPath("question/show")%>?currentPage=<%=pagination.getCurrentPage() %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>" method="GET">
            <input type="text"  id="fuzzyContent" name="fuzzyContent" value="<%=StringUtil.doWithNull(fuzzyQuesy)%>"/>
            <img src="<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Search_15x20.png" id="search-icon">
          </form>
          </div>

          <div class="information">
            <div class="description">
              <ul>
                <li class="li-id" value="">ID
                <img id="idIcon" src="<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Increase_10x15.png">
                </li>
                <li class="li-description">Description</li>
                <li class="li-edit">Edit</li>
                <li class="li-icon" ><img id="selectAll" src="<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Unselected_15x15.png"></li>
              </ul>
            </div>

            <div class="question-list">
            <%
              List<Question> questions = (List<Question>)request.getAttribute("questions");
              int i = 0;
              for (Question question : questions) {
                  i++;
            %>
              <div class="question-line">
                <ul >
                  <li class="index"><%=i %><input type="text" id="questionId<%=i %>" value="<%=question.getQuestionId() %>" style="display: none;" /></li>

                  <li class="id"><%=StringUtil.toQuestionId(question.getQuestionCode())%></li>

                  <li class="question-content"><%=question.getQuestionContent() %></li>

                  <li class="question-edit">
                    <a href="<%=PathUtil.getFullPath("question/edit/" + question.getQuestionId())%>" >
                      <img class="img-edit"src="<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Edit_15x15.png">
                    </a>
                  </li>
 
                  <li class="question-select" id="question-select-<%=i %>">
                  <div class="select-icon" >
                    <img name="selectname" class="select-one" id="select-<%=i %>" onclick="selectOne(<%=i %>)" src="<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Unselected_15x15.png">
                  </div>
                  </li>
                </ul>
              </div>
              <% } %>
              <form id="deletedQuestionform" action="<%=PathUtil.getFullPath("question/delete")%>" method="post">
               <input type="text" value="" name="deletedArray" id="deletedArray" />
               <input type="button" value="delete" class="bt-delete" id="delete" />
               <input type="text" name=deletePage style="display: none;" value=<%=pageSize%> />
               <input type="text" name="deleteCurrentPage" style="display: none;" value=<%=pagination.getPageCount()%> />
               <input type="text" style="display: none;" id="questionSort" name="questionSort" value=<%=questionSort%> />
              </form>

              <div class="pagination">
                <div class="page-left">
                  <span class="pre-page">
                    <%if (!pagination.isFirstPage() && pagination.getPageCount() > 1) {%>
                    <a href="<%=showQuestionUrl %>currentPage=<%=pagination.getCurrentPage() - 1 %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>">
                      <img src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_PageLeft_20x15.png">
                    </a>
                    <%} else { %>
                    <img src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_PageLeft_20x15.png">
                    <%}%>
                  </span>

                  <div class="page-all-num">
                    <%
                     int firstPage= ((pagination.getCurrentPage()/3)+1);
                     if (pagination.getCurrentPage() > 3) {
                        firstPage= ((pagination.getCurrentPage()/3)+2);
                     }
                     int pageMax = (pageToalNum >= firstPage + 3) ? firstPage+2 : pageToalNum;
                     for (int nowPage = firstPage; nowPage <= pageMax; nowPage++) {
                    %>
                     <span class="page-num" id="page-<%=nowPage %>">
                     <%if (nowPage == pagination.getCurrentPage()) { %>
                        <a id="nowPage" href="<%=showQuestionUrl%>currentPage=<%=nowPage %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>"><%=nowPage %></a>
                     <%} else {%>
                        <a href="<%=showQuestionUrl%>currentPage=<%=nowPage %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>"><%=nowPage %></a>
                     <%} %>
                     </span>
                     <%}%>

                     <%
                       String dotStyle="";
                       if (pageMax+1>pageToalNum) { 
                           dotStyle = "display: none;";
                       }
                     %>
                      <span class="page-dot" style="<%=dotStyle%>">...</span>

                     <% if (pageMax!=pageToalNum) { %>
                     <span class="last-pageNum">
                       <%if (pageToalNum == pagination.getCurrentPage()) { %>
                          <a id="nowPage" href="<%=showQuestionUrl%>currentPage=<%=pageToalNum %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>"><%=pageToalNum %></a>
                       <%} else {%>
                          <a href="<%=showQuestionUrl%>currentPage=<%=pageToalNum %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>"><%=pageToalNum %></a>
                       <%}%>
                      <%}%>
                    </span>
                  </div>

                  <span class="next-page">
                  <%if (!pagination.isLastPage() && pagination.getPageCount() > 1) {%>
                    <a href="<%=showQuestionUrl %>currentPage=<%=pagination.getCurrentPage() + 1 %>&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>">
                      <img src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_PageRight_20x15.png">
                    </a>
                  <%} else {%>
                      <img src="<%=PropertiesUtil.getStaticUrl()%>/images/BTN_PageRight_20x15.png">
                  <%} %>
                  </span>
               </div>
                <%
                  String selectFive="";
                  String selectTen="";
                  String styleTen="";
                  String styleFive="";
                  if (pageSize == 10) {
                      selectTen="selected=\"selected\"";
                  }

                  else {
                      selectFive="selected=\"selected\"";
                  }
                %>
               <div class="page-right">
                 <select id="pageSize" class ="selector"onchange="getPagesize()" Style="color: #FE9901;">
                 <option class="page-select" <%=selectFive%> value=5 >5</option>
                 <option class="page-select" <%=selectTen%> value=10 >10</option>
                 </select>
                 <span>Per page</span>
                 <input type="text" id="currentPage" class="input-page-num" />
                 <input type="button" value="Go" class="bt-go" id="paginationGo" />
               </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    </div>
  </body>
  <script type="text/javascript">
      var increaseUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Increase_10x15.png"
      var decresekUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Decrese_10x15.png";
      var unCherkUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Unselected_15x15.png";
      var cherkUrl = "<%=PropertiesUtil.getStaticUrl()%>/images/ICN_Selected_15x15.png";
      var idSort = "<%= questionSort%>";

      if (idSort === "ASC") {
         $("#idIcon").attr("src", increaseUrl);
      } else {
        $("#idIcon").attr("src", decresekUrl);
      }

      function isAllSelect() {
        var j; //id number 
        var max = <%=i%>; //The max of id 
        for (j = 1;j <= max; j++) {
             var imgid = "#select-" + j;
             var src = $(imgid).attr("src");
             if (src === unCherkUrl) {
                break;
             }
         }

         //whether all select
         if (j == (max + 1)) {
            $("#selectAll").attr("src",cherkUrl);
         } else {
            $("#selectAll").attr("src",unCherkUrl);
         }
    }

    // select one 
    function selectOne (id) {
        var imgid = "#select-"+id;
        var src = $(imgid).attr("src");

        if (src === cherkUrl) {
           $(imgid).attr("src", unCherkUrl);
        } else {
            $(imgid).attr("src", cherkUrl);
        }

        isAllSelect();
   }

    $("#closeWin").click(function() {
        $("#deleteWin").css('display', "none");
        $("#deleteQuestionMark").css('display' , "none");
    });

    // goto page by user input number
    $("#paginationGo").click(function () {
        var pageCount = $("#currentPage").val();
        if (!isInteger(pageCount)) {
            $("#errorFlashMessage").text(" please input correctly number");
            $("#errorFlashMessage").css('display', "block");

            setTimeout(function() {
                $("#errorFlashMessage").css('display', "none");
            }, 3000);
            return ;
        }

        var totalPageNum = <%=pageToalNum%>;

        if (pageCount > totalPageNum) {
            pageCount = totalPageNum;
        }
        location.href = "<%=showQuestionUrl %>currentPage=" + pageCount +"&pageSize=<%=pageSize%>&questionSort=<%=questionSort %>";
     });
  
      //get PageSize by option
    $(".selector").change( function(){
        location.href = "<%=showQuestionUrl %>pageSize=" + $(".selector").val() + "&questionSort=<%=questionSort %>";
    });

    // select all selectedicon
    $("#selectAll").click(
        function() {
            var src = $("#selectAll").attr("src");
            if (src == cherkUrl) {
                $("#selectAll").attr("src", unCherkUrl);
                $(".select-one").attr("src", unCherkUrl);
            } else {
                $("#selectAll").attr("src", cherkUrl);
                $(".select-one").attr("src", cherkUrl);
            }
    });

    $("#fuzzyContent").click(
        function() {
            if ($("#fuzzyContent").val() == "") {
                $("#fuzzyContent").css("placeholder", "&nbsp  Please input the keyword");
            }
    });

       //Order By Creaedtime
    $("#idIcon").click(function() {
        var src = $("#idIcon").attr("src");
        if (src == increaseUrl) {
            $("#idIcon").attr("src", decresekUrl);
            <% questionSort = "DESC";%>
            location.href = "<%=showQuestionUrl %>pageSize=<%=pageSize%>&questionSort=DESC";
        } else {
            $("#idIcon").attr("src", increaseUrl);
            <% questionSort = "ASC";%>
            location.href = "<%=showQuestionUrl %>pageSize=<%=pageSize%>&questionSort=ASC"
        }
    });

    $("#search-icon").click(
      function() {
          $("#fuzzyQuery").submit();
    });

     // deleted
    $("#delete").click (function () {
        $("#deleteQuestionMark").show();
        $("#deleteWin").show();
    });

    var indexDelete = new Array();
    var deleteNum = 0;

    $("#pop-win-yes").click(function() {
          var isSumbit = false; 
          var Max = <%=i%>;
          for (var j = 1;j <= Max; j++) {
               var imgid = "#select-" + j;
               var questionId ="#questionId" + j;
               var src = $(imgid).attr("src");
               if (src === cherkUrl) {
                  indexDelete[deleteNum++]=$(questionId).val();
              }
          }

          if (deleteNum == 0) {
             alert(" Please choice deleted")
          } else {
             $("#deletedArray").val(indexDelete);
             $("#deletedQuestionform").submit();
          }
    });
  </script>
</html>
