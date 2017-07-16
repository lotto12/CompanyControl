<%-- 
    Document   : Left1
    Created on : 2017/5/18, 下午 07:20:31
    Author     : JimmyYang
--%>

<%@page import="WebPage.GamePage_game1_Left1"%>
<%@page import="Function.EncryptionCode"%>
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

            //程式位置WebPage/GamePage_game1_Left1
            GamePage_game1_Left1 page_set = new GamePage_game1_Left1();
            EncryptionCode pwd = new EncryptionCode();
            String gtype = request.getParameter("gtype");
            String client_pwd = pwd.getCheckPwd_Client();

            //TABLE
            String table = page_set.createTable(gtype);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Main.js'></script>
        <script src='/Control/js/game1/Left1.js'></script>
    </head>
    <body onload="setCheck_Key('<%=client_pwd%>');setGtype('801');">
        <!--        表格顯示-->
        <%=table%>
    </body>
</html>
