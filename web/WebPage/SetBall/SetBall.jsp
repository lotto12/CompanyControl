<%-- 
    Document   : SetBall
    Created on : 2017/5/25, 下午 01:02:27
    Author     : JimmyYang
--%>

<%@page import="WebPage.SetBall"%>
<%@page import="model.DB"%>
<%@page import="model.DataTable"%>
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

            //REQUEST
            String gtype = request.getParameter("gtype");

            //取得資料
            String sql_selct = "SELECT id,gtype,period,acc_date,open_date,close_date,status FROM game_main";
            if (gtype != null && !gtype.equals("0")) {
                sql_selct += " where gtype='" + gtype + "'";
            }
            sql_selct += " order by id desc";
            DataTable dt = DB.getDataTable(sql_selct);

            //程式
            SetBall set_ball = new SetBall();
            String table = set_ball.getTable(dt);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>設定期數</title>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script type="text/javascript" src="/Control/js/SetBall/SetBall.js"></script>
        <script type="text/javascript" src="/Control/js/jquery-tablepage-1.0.js"></script>

        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->

    </head>
    <body>
        <table border="1">
            <tr>
                <th colspan="8">期數管理</th>
            </tr>
            <tr>
                <td>
                    <font>盤口：</font>
                </td>
                <td>
                    <input type="button" value="全部" class="button" onclick="setID('0');"></input>
                </td>
                <td>
                    <input type="button" value="六和" class="button" onclick="setID('801');"></input>
                </td>
                <td>
                    <input type="button" value="大樂" class="button" onclick="setID('802');"></input>
                </td>
                <td>
                    <input type="button" value="539" class="button" onclick="setID('803');"></input>
                </td>
                <td colspan="5">
                    <font>新增期數：</font>
                    <input style="background-color: #FFCC22;" type="button" value="新增六合彩" onclick="showGameSet('801');"></input>
                    <input style="background-color: #CCFF33;" type="button" value="新增大樂透" onclick="showGameSet('802');"></input>
                    <input style="background-color: #77DDFF;" type="button" value="新增539" onclick="showGameSet('803');"></input>
                </td>
            </tr>
        </table>
        <hr>
        <%=table%>
        <hr>
        <span id='table_page'></span>
        <script>
            $("#tbl").tablepage($("#table_page"), 20);
        </script>
    </body>
</html>
