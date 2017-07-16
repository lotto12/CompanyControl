<%-- 
    Document   : Add_Player
    Created on : 2017/6/2, 下午 05:46:38
    Author     : JimmyYang
--%>

<%@page import="Function.Game_Agents_conf"%>
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

            //來源
            String function = request.getParameter("function");
            String account = request.getParameter("account");
            String gtype = request.getParameter("gtype");
            Game_Agents_conf conf = new Game_Agents_conf();

            //顯示內容
            String title = null;

            //資料
            String[][] data = conf.getData_Percent(function, account, gtype);
            String[] member_data = conf.getMemberData(function, account);
            String color = conf.getColor(gtype);

            switch (function) {
                case "add":
                    //新增下層
                    title = "新增下層";
                    break;
                case "set":
                    //顯示資料
                    title = "詳細設定";
                    break;
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>新增下層</title>
        <!--        CSS載入start-->
        <link href="/Control/css/Main.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
    </head>
    <body>
        <!--                edit by jimmy-->  
        <form name="from" action="../Main" method="post">
            <input type=hidden name="function" value="<%=function%>_player"/> 
            <input type=hidden name="id" value="<%=account%>"/> 
            <input type=hidden name="gtype" value="<%=gtype%>"/> 
            <input type=hidden name="member" value="<%=member_data[5]%>"/> 
            <input type=hidden name="top" value='<%=member_data[6]%>'/> 
            <table border="1">
                <tr>
                    <th colspan="2" style="font-size: 15px">
                        <%=title%>管理
                    </th>
                </tr>
                <tr>
                    <td>帳號</td>
                    <td style="text-align:left;"><input type="text" name="user_account" value="<%=member_data[0]%>"></td>
                </tr>
                <tr>
                    <td>密碼</td>
                    <td style="text-align:left;" ><input name="user_password" type="password"  value="<%=member_data[1]%>"> 密碼必須至少4個字元長，最多12個字元長，並只能有數字(0-9)及英文大小寫字母</td>
                </tr>
                <tr>
                    <td>確認密碼</td>
                    <td style="text-align:left;"><input type="password" name="chk_password"  value="<%=member_data[2]%>"></td>
                </tr>
                <tr>
                    <td>名稱</td>
                    <td style="text-align:left;"><input type="text" name="name"  value="<%=member_data[3]%>"></td>
                </tr>
                <tr>
                    <td>總額度</td>
                    <td style="text-align:left;"><input type="number" name="cash"  value="<%=member_data[4]%>"> 擁有額度：【0】 剩餘額度：【2000000000】 額度上限：【2000000000】</td>
                </tr>
                <tr>
                    <th colspan="2">
                        <input type="radio" name="set" value="0">新增設定</input>
                        <input type="radio" name="set" value="1">複製</input>
                        <select name="account" style="width: 150px">
                            <option value="0">aa1</option>
                            <option value="1">aa2</option>
                        </select>
                        <input type="text" placeholder="直接輸入要複製的帳號">
                        的設定
                    </th>
                </tr>
                <tr>
                    <td style="background-color: <%=color%>" colspan="2">
                        <input type="button" value="六和" onclick="window.location.href = 'Set_Player.jsp?function=<%=function%>&account=<%=account%>&gtype=801'">
                        <input type="button" value="大樂" onclick="window.location.href = 'Set_Player.jsp?function=<%=function%>&account=<%=account%>&gtype=802'">
                        <input type="button" value="539" onclick="window.location.href = 'Set_Player.jsp?function=<%=function%>&account=<%=account%>&gtype=803'">
                    </td>
                </tr>
            </table>

            <table border="1">
                <tr>
                    <th colspan="12">
                        <font>佔成《<a href="#" style="color: bisque">如何設定佔成</a>》</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>設定值</td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][0]%>" name="d1"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][1]%>" name="d2"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][2]%>" name="d3"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][3]%>" name="d4"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][4]%>" name="d5"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][5]%>" name="d6"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][6]%>" name="d7"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][7]%>" name="d8"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][8]%>" name="d9"></td>
                    <td><input type="text" style="width: 50px" value="<%=data[0][9]%>" name="d10"></td>
                    <td><input type="text" style="width: 50px" name="d11"></td>
                </tr>
                <tr>
                    <td>原始值</td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <font>退水《<a href="#" style="color: bisque">如何設定退水</a>》</font>
                    </th>
                </tr>
                <tr>
                    <th colspan="12">
                        <font>以下同項目內之"退水"及"本金"欄為互相連動，請選擇兩者中較熟悉的一項輸入即可。</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>退水</td>
                    <td><input type="text" style="width: 50px" name="w1" value="<%=data[1][0]%>"></td>
                    <td><input type="text" style="width: 50px" name="w2" value="<%=data[1][1]%>"></td>
                    <td><input type="text" style="width: 50px" name="w3" value="<%=data[1][2]%>"></td>
                    <td><input type="text" style="width: 50px" name="w4" value="<%=data[1][3]%>"></td>
                    <td><input type="text" style="width: 50px" name="w5" value="<%=data[1][4]%>"></td>
                    <td><input type="text" style="width: 50px" name="w6" value="<%=data[1][5]%>"></td>
                    <td><input type="text" style="width: 50px" name="w7" value="<%=data[1][6]%>"></td>
                    <td><input type="text" style="width: 50px" name="w8" value="<%=data[1][7]%>"></td>
                    <td><input type="text" style="width: 50px" name="w9" value="<%=data[1][8]%>"></td>
                    <td><input type="text" style="width: 50px" name="w10" value="<%=data[1][9]%>"></td>
                    <td><input type="text" style="width: 50px" name="w11"></td>
                </tr>
                <tr>
                    <td>本金</td>
                    <td><input type="text" style="width: 50px" name="c1" value="<%=data[2][0]%>"></td>
                    <td><input type="text" style="width: 50px" name="c2" value="<%=data[2][1]%>"></td>
                    <td><input type="text" style="width: 50px" name="c3" value="<%=data[2][2]%>"></td>
                    <td><input type="text" style="width: 50px" name="c4" value="<%=data[2][3]%>"></td>
                    <td><input type="text" style="width: 50px" name="c5" value="<%=data[2][4]%>"></td>
                    <td><input type="text" style="width: 50px" name="c6" value="<%=data[2][5]%>"></td>
                    <td><input type="text" style="width: 50px" name="c7" value="<%=data[2][6]%>"></td>
                    <td><input type="text" style="width: 50px" name="c8" value="<%=data[2][7]%>"></td>
                    <td><input type="text" style="width: 50px" name="c9" value="<%=data[2][8]%>"></td>
                    <td><input type="text" style="width: 50px" name="c10" value="<%=data[2][9]%>"></td>
                    <td><input type="text" style="width: 50px" name="c11"></td>
                </tr>
                <tr>
                    <td>原退水</td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                    <td><font style="color: blue">90%</font></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <font>單筆上限 (二三四星以及天碰為「單碰上限」)</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>設定值</td>
                    <td><input type="text" style="width: 50px" name="p1" value="<%=data[3][0]%>"></td>
                    <td><input type="text" style="width: 50px" name="p2" value="<%=data[3][1]%>"></td>
                    <td><input type="text" style="width: 50px" name="p3" value="<%=data[3][2]%>"></td>
                    <td><input type="text" style="width: 50px" name="p4" value="<%=data[3][3]%>"></td>
                    <td><input type="text" style="width: 50px" name="p5" value="<%=data[3][4]%>"></td>
                    <td><input type="text" style="width: 50px" name="p6" value="<%=data[3][5]%>"></td>
                    <td><input type="text" style="width: 50px" name="p7" value="<%=data[3][6]%>"></td>
                    <td><input type="text" style="width: 50px" name="p8" value="<%=data[3][7]%>"></td>
                    <td><input type="text" style="width: 50px" name="p9" value="<%=data[3][8]%>"></td>
                    <td><input type="text" style="width: 50px" name="p10" value="<%=data[3][9]%>"></td>
                    <td><input type="text" style="width: 50px" name="p11"></td>
                </tr>
                <tr>
                    <td>原始值</td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <font>單號上限 ( 正特碼雙面為「單場上限」；二三四星為「單組額度」。 )</font>
                    </th>
                </tr>
                <tr>
                    <th colspan="12">
                        <font>單號上限是指給會員的單號下注限額，並非該組織線的單號可收上限，！注意：台號、特尾三 設0為不限制！</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>設定值</td>
                    <td><input type="text" style="width: 50px" name="l1" value="<%=data[4][0]%>"></td>
                    <td><input type="text" style="width: 50px" name="l2" value="<%=data[4][1]%>"></td>
                    <td><input type="text" style="width: 50px" name="l3" value="<%=data[4][2]%>"></td>
                    <td><input type="text" style="width: 50px" name="l4" value="<%=data[4][3]%>"></td>
                    <td><input type="text" style="width: 50px" name="l5" value="<%=data[4][4]%>"></td>
                    <td><input type="text" style="width: 50px" name="l6" value="<%=data[4][5]%>"></td>
                    <td><input type="text" style="width: 50px" name="l7" value="<%=data[4][6]%>"></td>
                    <td><input type="text" style="width: 50px" name="l8" value="<%=data[4][7]%>"></td>
                    <td><input type="text" style="width: 50px" name="l9" value="<%=data[4][8]%>"></td>
                    <td><input type="text" style="width: 50px" name="l10" value="<%=data[4][9]%>"></td>
                    <td><input type="text" style="width: 50px" name="l11"></td>
                </tr>
                <tr>
                    <td>原始值</td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                    <td><font style="color: blue">20000</font></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <font>《單邊上限說明》單邊上限(<input type="checkbox">二星、三星、四星  散單是否限制單邊上限 ＊六合、大樂、539通用設定)</font>
                    </th>
                </tr>
                <tr>
                    <th colspan="12">
                        <font>單邊上限是指可設定該組織線的單號可收上限</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>設定值</td>
                    <td><input type="text" style="width: 50px" name="b1"></td>
                    <td><input type="text" style="width: 50px" name="b2"></td>
                    <td><input type="text" style="width: 50px" name="b3"></td>
                    <td><input type="text" style="width: 50px" name="b4"></td>
                    <td><input type="text" style="width: 50px" name="b5"></td>
                    <td><input type="text" style="width: 50px" name="b6"></td>
                    <td><input type="text" style="width: 50px" name="b7"></td>
                    <td><input type="text" style="width: 50px" name="b8"></td>
                    <td><input type="text" style="width: 50px" name="b9"></td>
                    <td><input type="text" style="width: 50px" name="b10"></td>
                    <td><input type="text" style="width: 50px" name="b11"></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <font>總額度</font>
                    </th>
                </tr>
                <tr>
                    <td>玩法</td>
                    <td>全車</td>
                    <td>特碼</td>
                    <td>正特碼雙面</td>
                    <td>台號</td>
                    <td>特尾三</td>
                    <td>二星</td>
                    <td>三星</td>
                    <td>四星</td>
                    <td>天碰二</td>
                    <td>天碰三</td>
                    <td>快速輸入</td>
                </tr>
                <tr>
                    <td>設定值</td>
                    <td><input type="text" style="width: 50px" name="t1"></td>
                    <td><input type="text" style="width: 50px" name="t2"></td>
                    <td><input type="text" style="width: 50px" name="t3"></td>
                    <td><input type="text" style="width: 50px" name="t4"></td>
                    <td><input type="text" style="width: 50px" name="t5"></td>
                    <td><input type="text" style="width: 50px" name="t6"></td>
                    <td><input type="text" style="width: 50px" name="t7"></td>
                    <td><input type="text" style="width: 50px" name="t8"></td>
                    <td><input type="text" style="width: 50px" name="t9"></td>
                    <td><input type="text" style="width: 50px" name="t10"></td>
                    <td><input type="text" style="width: 50px" name="t11"></td>
                </tr>
                <tr>
                    <td>原始值</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>剩餘值</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>

                <tr>
                    <th colspan="12">
                        <input type="button" value="複製設定至大樂">
                        <input type="submit" value="確定">
                        <input type="reset" value="取消">
                        <input type="button" value="複製">
                        <font style="color: wheat;">※藍色數值為原始設定值</font>
                        <input type="button" value="貼上">
                    </th>
                </tr>
            </table>
        </form>
    </body>
</html>
