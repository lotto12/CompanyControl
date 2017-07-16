<%-- 
    Document   : game7
    Created on : 2017/7/11, 下午 10:23:01
    Author     : wayne
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Control/index.html\");</script>");
            }

        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>即時注單</title>
    </head>
    <body>
        <h1>即時注單 我在 ../WebPage/GamePage/game7/</h1>
        執行注單分析</br>
        <form name="from" action="/Control/Wagers_check" method="post">
            <input type="submit" value="send">
        </form>
    </body>
</html>
