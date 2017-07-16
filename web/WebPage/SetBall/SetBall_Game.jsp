<%-- 
    Document   : SetBall_Game
    Created on : 2017/5/25, 下午 10:39:58
    Author     : JimmyYang
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

            //接收資料
            String gtype = request.getParameter("gtype");
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <title>設定開盤</title>
    </head>
    <body>
        <form name="from" target="Mid" action="/Control/Main" method="post">
            <input type=hidden name="function" value="add_game"/>  
            <input type=hidden name="gtype" value="<%=gtype%>"/>  
            <table border="1">
                <tr>
                    <th>期數</th>
                    <td><input type="text" name="period"></input></td>
                </tr>
                <tr>
                    <th>日期</th>
                    <td><input type="date" name="acc_date"></input></td>
                </tr>
                <tr>
                    <th>開盤時間</th>
                    <td><input type="time" name="open_date"></input></td>
                </tr>
                <tr>
                    <th>關盤時間</th>
                    <td><input type="time" name="close_date"></input></td>
                </tr>
                <tr>
                    <th colspan="2"><input type="submit" class="button" value="確定"></input></th>
                </tr>
            </table>
        </form>
    </body>
</html>
