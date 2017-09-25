$(function() {
    var queryString = location.hash;
    $("#queryString").val(queryString);

    function loginsumbit() {
        $.ajax ({
            type: "POST",
            url: "login",
            data: $("#loginForm").serialize(),
            success: function(data){
              if (data.status === 1) {
                 location.href=data.data;
              } else {
                  $("#loginError").css('visibility', 'visible');
                  $("#loginError").text(data.data);
                }
            }
        });
    }

 $("body").keydown(function() {
    if (event.keyCode == "13") {
       $("#login").click();
    }
});    

    $("#login").click(function() {
        var errMsg = "";
        var isSumbit = true;
        var passwordIsNull = false;
        var userNameIsNull = false;

        if (!$("#userName").val()) {
            isSumbit = false;
            userNameIsNull = false;
            $("#errorUserName").css('visibility', 'visible');
            errMsg = "userName is required";
        } else {
            $("#errorUserName").css('visibility', 'hidden');
        }

        if (!$("#password").val()) {
            isSumbit = false;
            passwordIsNull = true;
            $("#errorPassword").css('visibility', 'visible');
            errMsg = "password is required";
        } else {
            $("#errorPassword").css('visibility', 'hidden');
        }

        if (passwordIsNull && userNameIsNull) {
            errMsg = "userName and Password is required";
        }

        if (!isSumbit) {
            $("#loginError").text(errMsg);
            $("#loginError").css('visibility', 'visible');
        } else {
            //("#loginForm").submit();
             loginsumbit();

        }
    });

    $("#remeber").click(
        function() {
            var UnSelect = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_RemeberMe_Unselect_12X12.png.png";
            var select = "<%=PropertiesUtil.getStaticUrl()%>/images/BTN_RemeberMe_Select_12x12.png";
            var src = $("#remeber").attr("src");
            if (src === Unselect) {
                $("#remeber").attr("src", select);
            } else {
                $("#remeber").attr("src", UnSelect);
            }
    });
});

