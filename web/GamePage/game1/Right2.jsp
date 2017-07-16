<%-- 
    Document   : Right2
    Created on : 2017/5/18, 下午 07:31:33
    Author     : JimmyYang
--%>

<%@page import="Function.EncryptionCode"%>
<%@page import="WebPage.GamePage_game1_Right2"%>
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

            //遊戲種類
            String gtype = request.getParameter("gtype");
            String game_id = "GS04"; //期數
            if (gtype == null || gtype.equals("null")) {
                gtype = "801";
            }

            //程式位置WebPage/GamePage_game1_Right1
            GamePage_game1_Right2 page_set = new GamePage_game1_Right2();
            EncryptionCode pwd = new EncryptionCode();
            String client_pwd = pwd.getCheckPwd_Client();

            //TABLE
            String table = page_set.getHTML_TABLE(game_id, gtype);
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->

        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Main.js'></script>
        <script src='/Control/js/game1/Right2.js'></script>
    </head>
    <body onload="setCheck_Key('<%=client_pwd%>');setGtype('801');">
        <input type="checkbox">以車數為單位顯示
        <table border="1">
            <tr>
                <th>名次</th>
                <th>特碼</th>
                <th>金額</th>
                <th>本金</th>
                <th>名次</th>
                <th>特碼</th>
                <th>金額</th>
                <th>本金</th>
                <th>名次</th>
                <th>特碼</th>
                <th>金額</th>
                <th>本金</th>
                <th>名次</th>
                <th>特碼</th>
                <th>金額</th>
                <th>本金</th>
                <th>名次</th>
                <th>特碼</th>
                <th>金額</th>
                <th>本金</th>
            </tr>
            <%=table%>
            <tr>
                <th colspan="5">調整數值間隔：</th>
                <th colspan="15">設置：</th>
            </tr>
            <tr>
                <td colspan="5">
                    <input type="button" id = "s1" value="5.0" onclick="setInterval(5.0, this.id)"></input>
                    <input type="button" id = "s2" value="2.0" onclick="setInterval(2.0, this.id)"></input>
                    <input type="button" id = "s3" value="1.0" onclick="setInterval(1.0, this.id)"></input>
                    <input type="button" id = "s4" value="0.5" onclick="setInterval(0.5, this.id)"></input>
                    <input type="button" id = "s5" value="0.2" onclick="setInterval(0.2, this.id)"></input>
                    <input type="button" id = "s6" value="0.1" onclick="setInterval(0.1, this.id)"></input>
                </td>
                <td colspan="15">
                    <input type="button" id="b1" value="單" onclick="setNum(1)"></input>
                    <input type="button" id="b2" value="雙" onclick="setNum(2)"></input>
                    <input type="button" id="b3" value="大" onclick="setNum(3)"></input>
                    <input type="button" id="b4" value="小" onclick="setNum(4)"></input>
                    <input type="button" id="b5" value="合單" onclick="setNum(5)"></input>
                    <input type="button" id="b6" value="合雙" onclick="setNum(6)"></input>
                    <input type="button" id="b7" value="+" onclick="change_all(1)"></input>
                    <input type="number" id="b8" maxlength="2" value="" style="width: 40px" onchange="change_all_num(this.id)"></input>
                    <input type="button" id="b9" value="-" onclick="change_all(0)"></input>
                    <input type="button" id="b_ok" value="確定" onclick="check();"></input>
                    <input type="button" id="b_cancel" value="取消" onclick="cancel();"></input>
                </td>
            </tr>
        </table>
    </body>
</html>
