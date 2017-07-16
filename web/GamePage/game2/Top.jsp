<%-- 
    Document   : Top
    Created on : 2017/7/1, 下午 04:21:05
    Author     : JimmyYang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <%
            String gtype = request.getParameter("gtype");
            String[] select = new String[2];
            for (int i = 0; i < select.length; i++) {
                select[i] = "";
            }
            if (gtype == null) {
                gtype = "801";
            }
            switch (gtype) {
                case "801":
                    select[0] = "SELECTED";
                    break;
                case "802":
                    select[1] = "SELECTED";
                    break;
            }
        %>
        <title>game1_top</title>
    </head>
    <body>
        <form target="Mid" id="from" action="game2.jsp" method="get">
            <select name="gtype" onchange="document.getElementById('from').submit();">
　         <option value="801" <%=select[0]%>>六和</option>
　         <option value="802" <%=select[1]%>>大樂透</option>
　         </select>
            <select name="game_type" onchange="document.getElementById('from').submit();">
　         <option value="Taipei">實盤</option>
　         <option value="Taoyuan">虛盤</option>
　         <option value="Hsinchu">便宜盤</option>
　      </select>
            <input type="button" value="風險預估表"></input>
            <font>線上人數:0</font>
            <a href="#">會員列表</a>
        </form>
    </body>
</html>
