<%-- 
    Document   : Update_type2_odds
    Created on : 2017/7/2, 下午 06:26:16
    Author     : wayne
--%>

<%@page import="javafx.scene.control.TitledPane"%>
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
            String odds = request.getParameter("odds");
            int num = (Integer.parseInt(id) - 1) / 4;
            int style = (Integer.parseInt(id) - 1) % 4;
            String title_1 = "", title_2 = "";
            if (num == 0) {
                title_1 = "正碼一";
            } else if (num == 1) {
                title_1 = "正碼二";
            } else if (num == 2) {
                title_1 = "正碼三";
            } else if (num == 3) {
                title_1 = "正碼四";
            } else if (num == 4) {
                title_1 = "正碼五";
            } else if (num == 5) {
                title_1 = "正碼六";
            } else if (num == 6) {
                title_1 = "特碼";
            } else {
                title_1 = "正碼一";
            }
            if (style == 0) {

                title_2 = "單";
            } else if (style == 1) {
                title_2 = "大";
            } else if (style == 2) {
                title_2 = "雙";
            } else if (style == 3) {
                title_2 = "小";
            } else {
                title_2 = "單";
            }

        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script src='/Control/js/game1/Update_type2_odds.js'></script>
        <script src='/Control/js/Main.js'></script>
        <title>修改賠率</title>
    </head>
    <body>
        <form name="from" action="/Control/Main" method="post">
            <table border="1">
                <tr>
                    <th>調整項目</th>
                    <th>賠率</th>                
                </tr>
                <tr>
                    <td><%=title_1%><%=title_2%></td>
                    <td><input type="text" name="odds" onchange="check_odds(this.value)" value="<%=odds%>"></td>
                </tr>
                <tr>
                    <td><input type="submit" class="button" name="sure" value="確定"></td>
                    <td><input type="submit" class="button" name="re" value="取消"></td>
                </tr>
            </table>   
            <input type=hidden name="id" value="<%=id%>"/>
        </form>
    </body>
</html>
