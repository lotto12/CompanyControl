<%-- 
    Document   : UpdateBall_Game
    Created on : 2017/5/25, 下午 10:39:58
    Author     : JimmyYang
--%>

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

            //接收資料
            String id = request.getParameter("id");
            String sql_selct = "SELECT id,gtype,period,acc_date,open_date,close_date,status FROM game_main where id=" + id;
            DataTable dt = DB.getDataTable(sql_selct);
            String period = dt.getColume(0, "period");
            String acc_date = dt.getColume(0, "acc_date");
            String open_date = dt.getColume(0, "open_date");
            String close_date = dt.getColume(0, "close_date");
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <title>修改開盤</title>
    </head>
    <body>
        <form name="from" target="Mid" action="/Control/Main" method="post">
            <input type=hidden name="function" value="update_game"/>  
            <input type=hidden name="id" value="<%=id%>"/> 
            <table border="1">
                <tr>
                    <th>期數</th>
                    <td><input type="text" name="period" value="<%=period%>"></input></td>
                </tr>
                <tr>
                    <th>日期</th>
                    <td><input type="date" name="acc_date" value="<%=acc_date%>"></input></td>
                </tr>
                <tr>
                    <th>開盤時間</th>
                    <td><input type="time" name="open_date" value="<%=open_date%>"></input></td>
                </tr>
                <tr>
                    <th>關盤時間</th>
                    <td><input type="time" name="close_date" value="<%=close_date%>"></input></td>
                </tr>
                <tr>
                    <th colspan="2"><input type="submit" class="button" value="確定"></input></th>
                </tr>
            </table>
        </form>
    </body>
</html>
