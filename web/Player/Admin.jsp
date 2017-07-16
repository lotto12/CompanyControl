<%-- 
    Document   : Admin
    Created on : 2017/5/28, 下午 02:12:24
    Author     : JimmyYang
--%>

<%@page import="Function.GameAdmin"%>
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
            
            //資料源
            GameAdmin game_admin = new GameAdmin();
            String table = game_admin.getHTML_table();
        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>控端子帳號</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th colspan="4">控端子帳號管理</th>
                </tr>
                <tr>
                    <td colspan="4">
                        <font>狀態：</font>
                        <select name="status">
                            <option>啟用</option>
                            <option>停用</option>
                        </select>
                        <font>子帳號：</font>
                        <input type="text" style="width: 150px"></input>
                        <input type="button" value="搜尋"></input>
                        <input type="button" value="新增"></input>
                    </td>
                </tr>
                <tr>
                    <th style="width: 30%">帳號</th>
                    <th style="width: 30%">名稱</th>
                    <th style="width: 10%">狀態</th>
                    <th style="width: 30%">功能</th>
                </tr>
            </thead>
            <tbody>
                <%=table%>
            </tbody>
        </table>

    </body>
</html>
