<%-- 
    Document   : StartBall
    Created on : 2017/5/26, 下午 02:47:17
    Author     : JimmyYang
--%>

<%@page import="model.DB"%>
<%@page import="model.DataTable"%>
<%@page import="WebPage.SetBall"%>
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
            String sql_select = "SELECT gtype,period FROM game_main where id = " + id;
            DataTable dt = DB.getDataTable(sql_select);

            SetBall set_ball = new SetBall();
            String table = set_ball.getBall_TABLE(dt.getColume(0, "gtype"), dt.getColume(0, "period"));
        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>開球</title>
    </head>
    <body>
        <form name="from" target="Mid" action="/Control/Main" method="post">
            <input type=hidden name="id" value="<%=id%>"/>  
            <input type=hidden name="function" value="startGame"/>  
            <%=table%>
        </form>
    </body>
</html>
