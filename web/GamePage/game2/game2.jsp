<%-- 
    Document   : game2
    Created on : 2017/7/1, 下午 04:18:27
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>台號 & 特尾三</title>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Control/index.html\");</script>");
            }
        %>
    </head>
    <frameset rows=7%,* frameborder="0">
        <frame src="Top.jsp"></frame>
        <frameset cols=50%,*> 
            <frame src="Left.jsp"></frame>
            <frame src="Right.jsp"></frame>
　　     </frameset> 
　　</frameset>
</html>
