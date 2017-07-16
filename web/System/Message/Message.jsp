<%-- 
    Document   : Message
    Created on : 2017/5/27, 下午 04:22:08
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

            //資料接收
            String status = request.getParameter("status");

            String sql_select = "select t1.acount,t2.name,t1.build_date,Case when t1.status='1' then '已經回覆' when t1.status='0' then '尚未回覆'end as 'status',t1.id from game_comment t1 , game_member t2 where t1.acount = t2.acount ";
            if (status != null) {
                if (!status.equals("-1")) {
                    sql_select += " and t1.status = " + status;
                }
            }
            sql_select += " order by id desc;";
            DataTable dt = DB.getDataTable(sql_select);
            MyTable st = new MyTable();
            String table = st.getTable_4(dt);
        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript" src="/Control/layer/layer.js"></script>
        <script src='/Control/js/Setting.js'></script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th colspan="7">留言管理</th>
                </tr>
                <tr>
                    <td>
                        <font>顯示內容：</font>
                    </td>
                    <td>
                        <input type="button" value="全部" class="button" onclick="setMsgStatus(-1);"></input>
                    </td>
                    <td>
                        <input type="button" value="未回覆" class="button" onclick="setMsgStatus(0);"></input>
                    </td>
                    <td>
                        <input type="button" value="已回覆" class="button" onclick="setMsgStatus(1);"></input>
                    </td>
                    <td>
                        <font>選取刪除：</font>
                    </td>
                    <td colspan="2">
                        <input type="button" value="選擇全部" style="background-color: #FFB7DD" onclick="chkall(true);"></input>
                        <input type="button" value="取消選擇" style="background-color: #CCFF99" onclick="chkall(false);"></input>
                        <input type="button" value="刪除留言" style="background-color: #CCDDFF" onclick="del();"></input>
                    </td>
                </tr>
            </thead>
        </table>
        <hr>
        <form name="del_form" action="/Control/Main" method="post">
            <input type=hidden name="function" value="del_msg"/>  
            <input type=hidden id="data" name="data" value=""/>  
            <table border="1">
                <thead>
                    <tr>
                        <th>刪除</th>
                        <th>名稱</th>
                        <th>留言時間</th>
                        <th>狀態</th>
                        <th>功能</th>
                    </tr>
                </thead>
                <tbody>
                    <%=table%>
                </tbody>
            </table>
        </form>
    </body>
</html>
