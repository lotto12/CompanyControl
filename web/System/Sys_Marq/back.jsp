<%-- 
    Document   : back
    Created on : 2017/5/28, 下午 01:18:49
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
        <title>後端</title>
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
    </head>
    <body>
        <table border="1">
            <tr>
                <th colspan="8">
                    後端跑馬燈
                </th>
            </tr>
            <tr>
                <td colspan="8">
                    目前後端跑馬燈內容
                </td>
            </tr>
            <tr>
                <td colspan="6"><input type="text" style="width: 90%"></input></td>
                <td><input type="button" class="button" value="修改"></td>
                <td><input type="button" class="button" value="新增"></td>
            </tr>
        </table>
        <hr>
        <table border="1">
            <tr>
                <th>日期</th>           
                <th>內容</th>     
                <th>功能</th>           
            </tr>
            <tr>
                <td>2016-09-18 18:52:36</td>
                <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                <td><input type="button" value="修改"><input type="button" value="刪除"></td>
            </tr>
            <tr>
                <td>2016-09-18 18:52:36</td>
                <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                <td><input type="button" value="修改"><input type="button" value="刪除"></td>
            </tr>
            <tr>
                <td>2016-09-18 18:52:36</td>
                <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                <td><input type="button" value="修改"><input type="button" value="刪除"></td>
            </tr>
        </table>
    </body>
</html>
