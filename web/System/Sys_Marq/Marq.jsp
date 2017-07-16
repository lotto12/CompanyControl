<%-- 
    Document   : Marq
    Created on : 2017/5/28, 下午 01:16:38
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
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>跑馬燈</title>
    </head>
    <frameset cols=50%,* border="0"> 
　　　　      <frame src="front.jsp"></frame>
　　　　      <frame src="back.jsp"></frame>
　　</frameset> 
</html>
