<%-- 
    Document   : ChangePwd
    Created on : 2017/5/27, 下午 12:38:28
    Author     : JimmyYang
--%>

<%@page import="Function.MyTable"%>
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

            //顯示
            MyTable st = new MyTable();
            String table = st.getTable_3();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/js/jquery-tablepage-1.0.js"></script>
        <title>更改密碼</title>
    </head>
    <body>
        <form name="from" action="../Main" method="post">
            <input type=hidden name="function" value="change_pwd"/>  
            <table border="1">
                <thead>
                    <tr>
                        <th colspan="2">更改密碼</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>舊密碼</td>
                        <td><input type="password" name="old_pwd"></input></td>
                    </tr>
                    <tr>
                        <td>新密碼</td>
                        <td><input type="password" name="mew_pwd"></input></td>
                    </tr>
                    <tr>
                        <td>確認密碼</td>
                        <td><input type="password" name="new_pwd_chk"></input></td>
                    </tr>
                    <tr>
                        <th colspan="2"><input type="submit" class="button" value="修改"></input></t>
                    </tr>
                </tbody>
            </table>
        </form>
        <hr>
        <table border="1" id="tbl">
            <thead>
                <tr>
                    <th colspan="3">登入紀錄</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>登入時間</td>
                    <td>登入IP</td>
                    <td>登入狀態</td>
                </tr>
                <%=table%>
            </tbody>
        </table>
        <span id='table_page'></span>
        <script>
            $("#tbl").tablepage($("#table_page"), 10);
        </script>
    </body>
</html>
