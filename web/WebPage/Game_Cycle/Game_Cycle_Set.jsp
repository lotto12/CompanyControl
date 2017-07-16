<%-- 
    Document   : Game_Cycle_Set
    Created on : 2017/5/26, 下午 09:11:20
    Author     : JimmyYang
--%>

<%@page import="Function.MyTable"%>
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

            String gtype = request.getParameter("gtype");
            String sql_select = "SELECT * FROM game_cycle where gtype = " + gtype;
            DataTable dt = DB.getDataTable(sql_select);
            String[] check = {"mo", "tu", "we", "th", "fr", "sa", "su"};
            String[] data = new String[check.length];
            for (int i = 0; i < data.length; i++) {
                data[i] = dt.getColume(0, check[i]);
            }
            MyTable st = new MyTable();

            //需要顯示的資料
            String table = st.getTable_1(data);
            String code = dt.getColume(0, "code");
            String op_time = dt.getColume(0, "opentime");
            String cl_time = dt.getColume(0, "closetime");
            String game_name = null;
            switch (gtype) {
                case "801":
                    game_name = "六合彩";
                    break;
                case "802":
                    game_name = "大樂透";
                    break;
                case "803":
                    game_name = "539";
                    break;
            }


        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>盤口設定</title>
    </head>
    <body>
        <form name="from" target="Mid" action="/Control/Main" method="post">
            <input type=hidden name="function" value="set_game_cycle"/>  
            <input type=hidden name="gtype" value="<%=gtype%>"/>  
            <table border="1">
                <tr>
                    <th rowspan="2">名稱</th>
                    <td><%=game_name%></td>
                </tr>
                <tr>
                    <td>代號<input type="text" value="<%=code%>" name="code" required="required"></input></td>
                </tr>
                <tr>
                    <th>日期</th>
                    <td>
                        <%=table%>
                    </td>
                </tr>
                <tr>
                    <th>開盤時間</th>
                    <td>
                        <input type="time" value="<%=op_time%>" name="op_time" required="required"></input>
                    </td>
                </tr>
                <tr>
                    <th>關盤時間</th>
                    <td>
                        <input type="time" value="<%=cl_time%>" name="cl_time" required="required"></input>
                    </td>
                </tr>
                <tr>
                    <th colspan="2">
                        <input type="submit" class="button" value="儲存"></input>
                    </th>
                </tr>
            </table>
        </form>
    </body>
</html>
