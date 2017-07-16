<%-- 
    Document   : Game_Cycle
    Created on : 2017/5/26, 下午 06:30:15
    Author     : JimmyYang
--%>

<%@page import="WebPage.Game_Cycle"%>
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

            //類別
            Game_Cycle gc = new Game_Cycle();
            String table = gc.showTable();

        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script type="text/javascript" src="/Control/js/Game_Cycle/Game_Cycle.js"></script>
        <script type="text/javascript" src="/Control/js/jquery-tablepage-1.0.js"></script>

        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <title>盤口週期</title>
    </head>
    <body>
        <%=table%>
    </body>
</html>
