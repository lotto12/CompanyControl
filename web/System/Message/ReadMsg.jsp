<%-- 
    Document   : ReadMsg
    Created on : 2017/5/27, 下午 05:32:14
    Author     : JimmyYang
--%>

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

            //接收資料
            String id = request.getParameter("id");
            String sql_select = "select t1.build_date,t1.id,t1.acount,t2.name,t1.phone,t1.email,t1.msg,t1.reply from game_comment t1 , game_member t2 where t1.acount = t2.acount and t1.id = " + id;
            DataTable dt = DB.getDataTable(sql_select);
            String date = dt.getColume(0, "build_date");
            String name = dt.getColume(0, "acount") + "(" + dt.getColume(0, "name") + ")";
            String phone = dt.getColume(0, "phone");
            String email = dt.getColume(0, "email");
            String msg = dt.getColume(0, "msg");
            String reply_msg = dt.getColume(0, "reply");
            String disable = "disabled=\"false\"";
            String str = "已回覆";
            if (reply_msg.equals("null")) {
                reply_msg = "";
                disable = "";
                str = "回覆";
            }
        %>
        <!--        CSS載入start-->
        <link href="/Control/css/Main_Big.css" rel="stylesheet" type="text/css">
        <!--        CSS載入end-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>讀取訊息</title>
    </head>
    <body>
        <table border="1">
            <tr>
                <th colspan="2">
                    留言管理
                </th>
            </tr>
            <tr>
                <td>
                    留言時間：
                </td>
                <td>
                    <%=date%>
                </td>
            </tr>
            <tr>
                <td>
                    客戶名稱：
                </td>
                <td>
                    <%=name%>
                </td>
            </tr>
            <tr>
                <td>
                    聯絡電話：
                </td>
                <td>
                    <%=phone%>
                </td>
            </tr>
            <tr>
                <td>
                    E-Mail：
                </td>
                <td>
                    <%=email%>
                </td>
            </tr>
            <tr>
                <td>
                    聯絡事項：
                </td>
                <td style="height: 100px; vertical-align: text-top; text-align: left;background-color: white">
                    <%=msg%>
                </td>
            </tr>
        </table>
        <hr>
        <form name="from" action="/Control/Main" method="post" target="Mid">
            <input type=hidden name="function" value="reply_msg"/>  
            <input type=hidden name="id" value="<%=id%>"/>  
            <table border="1">
                <tr>
                    <th colspan="2">
                        回覆留言
                    </th>
                </tr>
                <tr>
                    <td>
                        回覆內容
                    </td>
                    <td>
                        <textarea cols="50" rows="5" name="reply" required="required" <%=disable%>><%=reply_msg%></textarea>
                    </td>
                </tr>
                <tr>
                    <th colspan="2">
                        <input type="submit" class="button" value="<%=str%>" <%=disable%>></input>
                    </th>
                </tr>
            </table>
        </form>
    </body>
</html>
