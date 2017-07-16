<%-- 
    Document   : Left
    Created on : 2017/7/1, 下午 04:24:43
    Author     : JimmyYang
--%>

<%@page import="WebPage.GamePage_game2_Left"%>
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

            //資料端
            String game_id = "GS04";
            String gtype = "801";
            String type = "4";
            GamePage_game2_Left gp = new GamePage_game2_Left(game_id, gtype, type);
            String table = gp.getHTML_TABLE();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->

        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Main.js'></script>
        <script src='/Control/js/game1/Right1.js'></script>

    </head>
    <body>
        <font>單一號碼中獎風險限額：<input type="number" style="width: 50px" value="0"></input></font>
        <hr>
        <table border="1">
            <tr>
                <th colspan="10">台號</th>
            </tr>
        </tr>
        <tr>
            <td>名次</td>
            <td>號碼</td>
            <td style="width: 100px">金額</td>
            <td>本金</td>
            <td>賠率</td>
            <td>名次</td>
            <td>號碼</td>
            <td style="width: 100px">金額</td>
            <td>本金</td>
            <td>賠率</td>
        </tr>
        <%=table%>
    </table>
</body>
</html>
