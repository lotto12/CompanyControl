<%-- 
    Document   : game1
    Created on : 2017/5/18, 下午 06:29:27
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>正特碼雙面</title>
        <%
            //檢查帳號有無登入，未登入則返回登入畫面
            String user_acount = (String) session.getAttribute("user_acount");
            if (user_acount == null) {
                out.print("<script>top.window.location.replace(\"/Control/index.html\");</script>");
            }

            String gtype = request.getParameter("gtype");
        %>
    </head>

    <frameset rows=7%,* frameborder="0">
        <frame src="Top.jsp?gtype=<%=gtype%>"></frame>
　　     <frameset cols=40%,*> 
            <frameset rows=*,25%,20%> 
　　　　      <frame src="Left1.jsp?gtype=<%=gtype%>"></frame>
　　　　      <frame src="Left2.jsp?gtype=<%=gtype%>"></frame>
                <frame src="Left3.jsp?gtype=<%=gtype%>"></frame>
　　     </frameset> 
            <frameset rows=45%,*> 
　　　　    <frame src="Right1.jsp?gtype=<%=gtype%>"></frame>
                <frame src="Right2.jsp?gtype=<%=gtype%>"></frame>
　　     </frameset>
　　</frameset>
    </frameset>
</html>
