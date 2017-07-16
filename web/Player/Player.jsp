<%-- 
    Document   : Player
    Created on : 2017/5/28, 下午 02:26:48
    Author     : JimmyYang
--%>

<%@page import="model.LogTool"%>
<%@page import="Function.Game_Agents"%>
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

            //階層
            String agents = request.getParameter("agents");
            String account = request.getParameter("account");
            String status = request.getParameter("status");
            String search_account = request.getParameter("search_account");
            String sql_select = "SELECT * FROM game_agents";

            if (agents != null) {
                sql_select += " where member = " + agents;
            }

            if (status != null) {
                sql_select += " and status = " + status;
            }

            if (search_account != null && search_account.length() > 0) {
                sql_select += " and acount = '" + search_account + "'";
            }

            DataTable dt = DB.getDataTable(sql_select);
            Game_Agents ga = new Game_Agents();
            String table = ga.getTable(dt, agents, account);
            String title_name = ga.getAgents_Name(agents);

        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>會員</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th colspan="7" style="font-size: 20px;"><%=title_name%>管理</th>
                </tr>
                <tr>
            <form name="from" action="Player.jsp?agents=<%=agents%>" method="post">
                <td colspan="7">
                    <font>狀態：</font>
                    <select name="status">
                        <option value="1">啟用</option>
                        <option value="0">停用</option>
                    </select>
                    <font>會員帳號：</font>
                    <input type="text" style="width: 150px" name="search_account"></input>
                    <input type="submit" value="搜尋"></input>
                    <input type="button" value="新增"></input>
                </td>
            </form>
        </tr>
        <tr>
            <th style="width: 10%">帳號</th>
            <th style="width: 10%">名稱</th>
            <th style="width: 10%">下層人數</th>
            <th style="width: 10%">額度</th>
            <th style="width: 10%">狀態</th>
            <th style="width: 20%">功能</th>
            <th style="width: 40%">各項佔成(上層不吃丟公司)</th>
        </tr>
    </thead>
    <tbody>
        <%=table%>
    </tbody>
</table>

</body>
</html>
