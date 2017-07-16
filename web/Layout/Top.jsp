<%-- 
    Document   : Top
    Created on : 2017/5/18, 下午 05:56:55
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
        <title>上層</title>
        <!--        CSS載入start-->
        <link href="../css/Top.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Top/Top.js'></script>
    </head>
    <body>
        <table border="1">
　<tr>
　<td rowspan="2"><a target="Mid" href="../WebPage/index.jsp"><img src="../images/login.png" height="50"></img></a></td>
　<td><input class="button" type="button" value="控盤" onclick="change(1);"></input></td>
                <td><input class="button" type="button" value="組織管理" onclick="change(2);"></input></td>
                <td><input class="button" type="button" value="遊戲設定" onclick="change(3);"></input></td>
                <td><input class="button" type="button" value="各類報表" onclick="change(4);"></input></td>
                <td><input class="button" type="button" value="訊息相關" onclick="change(5);"></input></td>
                <td><input class="button" type="button" value="修改密碼" onclick="changePwd();"></input></td>
                <td><input class="button" type="button" value="其他" onclick="change(6);"></input></td>
                <td><input class="logout" type="button" value="登出" onclick="logout();"></input></td>
　</tr>
　<tr>
　<td colspan="8"> 
                    <!--        控盤-->
                    <div id="f1">
                        <a class="href" href="../GamePage/game1/game1.jsp" target="Mid">正特碼 & 雙面</a>
                        <a class="href" href="../GamePage/game2/game2.jsp" target="Mid">台號 & 特尾三</a>
                        <a class="href" href="../GamePage/game3/game3.jsp" target="Mid">二、三、四星</a>
                        <a class="href" href="../GamePage/game4/game4.jsp" target="Mid">天碰二碼</a>
                        <a class="href" href="../GamePage/game5/game5.jsp" target="Mid">天碰三碼</a>
                        <a class="href" href="../GamePage/game6/game6.jsp" target="Mid">星碰轉單列表</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">即時單</a>
                    </div>

                    <!--        組織管理-->
                    <div style="display:none" id="f2">
                        <a class="href" href="../Player/Admin.jsp" target="Mid">控端子帳號</a>
                        <a class="href" href="../Player/Player.jsp?agents=0" target="Mid">大總監</a>
                        <a class="href" href="../Player/Player.jsp?agents=1" target="Mid">總監</a>
                        <a class="href" href="../Player/Player.jsp?agents=2" target="Mid">大股東</a>
                        <a class="href" href="../Player/Player.jsp?agents=3" target="Mid">股東</a>
                        <a class="href" href="../Player/Player.jsp?agents=4" target="Mid">總代理</a>
                        <a class="href" href="../Player/Player.jsp?agents=5" target="Mid">代理</a>
                        <a class="href" href="../Player/Player.jsp" target="Mid">會員</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">組織移桶</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">全站修改</a>
                    </div>

                    <!--        遊戲設定-->
                    <div style="display:none" id="f3">
                        <a class="href" href="../WebPage/Game_Cycle/Game_Cycle.jsp" target="Mid">盤口週期</a>
                        <a class="href" href="../WebPage/SetBall/SetBall.jsp" target="Mid">期數設定</a>
                        <a class="href" href="../GamePage/game3/game3.jsp" target="Mid">期數開獎</a>
                        <a class="href" href="../GamePage/game4/game4.jsp" target="Mid">組數設定</a>
                        <a class="href" href="../GamePage/game5/game5.jsp" target="Mid">號碼封牌</a>
                        <a class="href" href="../GamePage/game6/game6.jsp" target="Mid">押累跳</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">固定陪</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">特殊牌型</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">特殊包牌</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">特殊規則</a>
                    </div>

                    <!--        各類報表-->
                    <div style="display:none" id="f4">
                        <a class="href" href="../GamePage/game1/game1.jsp" target="Mid">開球結果</a>
                        <a class="href" href="../GamePage/game2/game2.jsp" target="Mid">總累計表</a>
                        <a class="href" href="../GamePage/game3/game3.jsp" target="Mid">大盤表</a>
                        <a class="href" href="../GamePage/game4/game4.jsp" target="Mid">明細表</a>
                        <a class="href" href="../GamePage/game5/game5.jsp" target="Mid">總報表</a>
                        <a class="href" href="../GamePage/game6/game6.jsp" target="Mid">月報表</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">總和總報表</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">星碰分析表</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">撤單列表</a>
                        <a class="href" href="../GamePage/game7/game7.jsp" target="Mid">分盤表</a>
                    </div>

                    <!--        訊息相關-->
                    <div style="display:none" id="f5">
                        <a class="href" href="../System/Sys_Marq/Marq.jsp" target="Mid">跑馬燈</a>
                        <a class="href" href="../System/Message/Message.jsp" target="Mid">客戶留言</a>
                        <a class="href" href="../WebPage/index.jsp" target="Mid">更新日誌</a>
                        <a class="href" href="javascript:window.open('http://bet.hkjc.com/marksix/Fixtures.aspx', '攪珠日期', config='height=800px,width=800px');">攪珠日期</a>
                        <a class="href" href="../System/Wel_Msg.jsp" target="Mid">登入公告</a>
                    </div>

                    <!--        其他-->
                    <div style="display:none" id="f6">
                        <a class="href" href="../GamePage/game1/game1.jsp" target="Mid">解鎖</a>
                    </div>
                </td>
　</tr>
        </table>
    </body>
</html>
