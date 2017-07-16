<%-- 
    Document   : Control_index
    Created on : 2017/5/18, 下午 05:51:20
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
                out.print("<script>parent.location.href='/Control/index.html';</script>");
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>控端</title>
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js'></script>
        <script type="text/javascript">
            //調整中間顯示範圍
            var obj1_h;
            var bd_h;
            function resizeIframe(obj) {
                obj.style.height = 0;
                var hight = obj.contentWindow.document.body.scrollHeight;
                obj1_h = hight;
                bd_h = $(window).height() - 50;
                document.getElementById('Mid').style.height = bd_h - obj1_h + 'px';
                obj.style.height = hight + 'px';
            }
            //重新調整視窗
            $(document).ready(function () {
                $(window).resize(function () {
                    hight = $(window).height() - 50;
                    bd_h = hight;
                    document.getElementById('Mid').style.height = bd_h - obj1_h + 'px';
                });
            });
        </script>
        <style>
            #container{
                height:98vh;
                /* Permalink - use to edit and share this gradient: http://colorzilla.com/gradient-editor/#ffffff+0,e5e5e5+100;White+3D */
                background: rgb(255,255,255); /* Old browsers */
                background: -moz-linear-gradient(top, rgba(255,255,255,1) 0%, rgba(229,229,229,1) 100%); /* FF3.6-15 */
                background: -webkit-linear-gradient(top, rgba(255,255,255,1) 0%,rgba(229,229,229,1) 100%); /* Chrome10-25,Safari5.1-6 */
                background: linear-gradient(to bottom, rgba(255,255,255,1) 0%,rgba(229,229,229,1) 100%); /* W3C, IE10+, FF16+, Chrome26+, Opera12+, Safari7+ */
                filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#ffffff', endColorstr='#e5e5e5',GradientType=0 ); /* IE6-9 */
            }
        </style>
    </head>
    <body>
        <div id="container">
            <iframe src="Layout/Top.jsp" name="Top" width="100%" marginwidth="0" marginheight="0" scrolling="No" frameborder="0" id="Top"  onload="resizeIframe(this)"></iframe> 
            <iframe src="WebPage/index.jsp" name="Mid" width="100%" marginwidth="0" marginheight="0" scrolling="Yes" frameborder="0" id="Mid"></iframe> 
        </div>
    </body>
</html>
