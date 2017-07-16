<%-- 
    Document   : Wel_Msg
    Created on : 2017/5/28, 下午 01:57:22
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
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>登入公告</title>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <td colspan="4">
                        <input type="button" class="button" value="新增公告"></input>
                    </td>
                </tr>
                <tr>
                    <th colspan="4">修改公告</th>
                </tr>
                <tr>
                    <td>類別</td>
                    <td>標題</td>
                    <td>內容</td>
                    <td>功能</td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>重要通知</td>
                    <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                    <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                    <td><font style="color: green">(啟用中)</font><input type="button" value="停用"><input type="button" value="修改"></td>
                </tr>
                <tr>
                    <td>重要通知</td>
                    <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                    <td><input type="text" style="width: 90%" value="上線測試"></input></td>
                    <td><font style="color: red">(停用中)</font><input type="button" value="啟用"><input type="button" value="修改"></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
