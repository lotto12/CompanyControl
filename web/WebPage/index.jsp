<%-- 
    Document   : index
    Created on : 2017/5/18, 下午 05:56:50
    Author     : JimmyYang
--%>

<%@page import="Function.MyTable"%>
<%@page import="model.DB"%>
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

            String sql_select = "select build_date , msg from system_update_msg";
            MyTable st = new MyTable();
            String table = st.getTable_2(DB.getDataTable(sql_select));
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <title>更新日誌</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <th>未回覆的客戶留言</th>
                <th>中間商人數</th>
                <th>線上中間商</th>
                <th>會員人數</th>
                <th>線上會員</th>
                <th>佔成修改開始時間</th>
                <th>佔成修改結束時間</th>

            </tr>
            <tr>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <td>22:00:00</td>
                <td>23:59:00</td>
            </tr>
        </table>
        <hr>
        <%=table%>
    </body>
</html>
