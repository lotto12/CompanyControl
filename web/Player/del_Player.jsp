<%-- 
    Document   : del_Player
    Created on : 2017/6/19, 下午 01:43:48
    Author     : Jimmy
--%>

<%@page import="model.DB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>刪除</title>
    </head>
    <body>
        <%
            String id = request.getParameter("master_id");
            if (id != null && !id.equals("")) {
                String sql_del = "delete from game_agents where id = " + id;
                String sql_del_conf = "delete from game_agents_conf where master_id = '" + id + "'";
                boolean del1 = DB.query(sql_del);
                boolean del2 = DB.query(sql_del_conf);
                String back_button = "<br><button onclick=\"javascript:history.go(-1);\">返回</buttton>";
                if (del1 && del2) {
                    out.print("<h3>刪除成功</h3>" + back_button);
                } else {
                    out.print("<h3>刪除失敗</h3>" + back_button);
                }
            }
        %>
    </body>
</html>
