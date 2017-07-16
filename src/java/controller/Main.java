/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Function.GameAdmin;
import Function.GameSetting;
import Function.Game_Agents;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JimmyYang 網頁跳板
 */
public class Main extends HttpServlet {

    //遊戲設定
    private GameSetting gs = new GameSetting();

    private GameAdmin gd = new GameAdmin();

    private Game_Agents game_agents = new Game_Agents();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        //選擇功能
        String id = request.getParameter("function");
        try {
            //選取功能
            HttpSession session;
            switch (id) {
                case "1":
                    //SQL_select_DEBUG
                    String sql_select = request.getParameter("sql_select");
                    session = request.getSession();
                    session.setAttribute("sql_select", sql_select);
                    response.sendRedirect("Debug/Search.jsp");
                    break;
                case "2":
                    //SQL_select_table_DEBUG
                    String table = request.getParameter("table");
                    session = request.getSession();
                    session.setAttribute("sql_select", "select * from " + table);
                    response.sendRedirect("Debug/Search.jsp");
                    break;
                case "login":
                    //首頁登入功能
                    String user_acount = request.getParameter("user_acount");
                    String user_pwd = request.getParameter("user_pwd");
                    String ip = request.getRemoteAddr();
                    System.out.println("[登入] ip->" + ip);
                    if (gd.isAdminOK(user_acount, user_pwd, ip)) {
                        session = request.getSession();
                        session.setAttribute("user_acount", user_acount);
                        response.sendRedirect("Control_index.jsp");
                    } else {
                        showAlert(response, "帳號密碼錯誤!", "index.html");
                    }
                    break;
                case "add_game":
                    //新增賽事
                    String gtype = request.getParameter("gtype");
                    String period = request.getParameter("period");
                    String acc_date = request.getParameter("acc_date");
                    String open_date = request.getParameter("open_date");
                    String close_date = request.getParameter("close_date");
                    if (gs.add_game(gtype, period, acc_date, open_date, close_date)) {
                        showAlert(response, "新增成功", "WebPage/SetBall/SetBall.jsp");
                    } else {
                        showAlert(response, "新增失敗，請檢查輸入格式是否正確。", "WebPage/SetBall/SetBall.jsp");
                    }
                    break;
                case "update_game":
                    //修改賽事
                    String id_u = request.getParameter("id");
                    String period_u = request.getParameter("period");
                    String acc_date_u = request.getParameter("acc_date");
                    String open_date_u = request.getParameter("open_date");
                    String close_date_u = request.getParameter("close_date");
                    if (gs.update_game(id_u, period_u, acc_date_u, open_date_u, close_date_u)) {
                        showAlert(response, "修改成功", "WebPage/SetBall/SetBall.jsp");
                    } else {
                        showAlert(response, "修改失敗，請檢查輸入格式是否正確。", "WebPage/SetBall/SetBall.jsp");
                    }
                    break;
                case "startGame":
                    //開球
                    String id_s = request.getParameter("id");
                    String[] balls = new String[6];
                    String spc = request.getParameter("num_spc");
                    for (int i = 0; i < balls.length; i++) {
                        balls[i] = request.getParameter("num" + (i + 1));
                        if (balls[i] == null) {
                            balls[i] = "0";
                        }
                    }
                    if (spc == null) {
                        spc = "0";
                    }
                    if (gs.start_game(id_s, balls, spc)) {
                        showAlert(response, "開球成功", "WebPage/SetBall/SetBall.jsp");
                    } else {
                        showAlert(response, "開球失敗，請檢查輸入格式是否正確。", "WebPage/SetBall/SetBall.jsp");
                    }
                    break;
                case "set_game_cycle":
                    //設定盤口
                    String gtype_c = request.getParameter("gtype");
                    String code = request.getParameter("code");
                    String op_time = request.getParameter("op_time");
                    String cl_time = request.getParameter("cl_time");
                    String[] wes = new String[7];
                    for (int i = 0; i < wes.length; i++) {
                        wes[i] = request.getParameter("we" + (i));
                        if (wes[i] == null) {
                            wes[i] = "0";
                        }

                        if (wes[i].equals("on")) {
                            wes[i] = "1";
                        }
                    }
                    if (gs.update_Cycle(gtype_c, code, op_time, cl_time, wes)) {
                        showAlert(response, "設定成功", "WebPage/Game_Cycle/Game_Cycle.jsp");
                    } else {
                        showAlert(response, "設定失敗，請檢查輸入格式是否正確。", "WebPage/Game_Cycle/Game_Cycle.jsp");
                    }
                    break;
                case "change_pwd":
                    //更改密碼
                    String old_pwd = request.getParameter("old_pwd");
                    String new_pwd = request.getParameter("mew_pwd");
                    String new_pwd_chk = request.getParameter("new_pwd_chk");
                    session = request.getSession();
                    String acount = (String) session.getAttribute("user_acount");
                    if (acount != null) {
                        String reuslt = gd.updatePwd(acount, old_pwd, new_pwd, new_pwd_chk);
                        showAlert(response, reuslt, "WebPage/ChangePwd.jsp");
                    }
                    break;
                case "reply_msg":
                    //訊息回覆
                    String id_r = request.getParameter("id");
                    String msg = request.getParameter("reply");
                    if (gs.reply_msg(id_r, msg)) {
                        showAlert(response, "回覆成功", "System/Message/Message.jsp");
                    } else {
                        showAlert(response, "回覆失敗，請檢查輸入格式是否正確。", "System/Message/Message.jsp");
                    }
                    break;
                case "del_msg":
                    String del_data = request.getParameter("data");
                    if (gs.del_msg(del_data)) {
                        showAlert(response, "刪除成功", "System/Message/Message.jsp");
                    } else {
                        showAlert(response, "刪除失敗，請檢查輸入格式是否正確。", "System/Message/Message.jsp");
                    }
                    break;
                case "add_player":
                    //新增會員
                    String back_button = "<br><button onclick=\"javascript:history.go(-1);\">返回</buttton>";
                    if (game_agents.setData("add", request)) {
                        show_html(response, "建立成功" + back_button);
                    } else {
                        show_html(response, "建立失敗，請檢查資料輸入正確" + back_button);
                    }
                    break;
                case "set_player":
                    //詳細設定
                    String back_button_set = "<br><button onclick=\"javascript:history.go(-1);\">返回</buttton>";
                    if (game_agents.setData("set", request)) {
                        show_html(response, "修改成功" + back_button_set);
                    } else {
                        show_html(response, "修改失敗，請檢查資料輸入正確" + back_button_set);
                    }
                    break;

            }
        } catch (Exception ex) {
            show_html(response, "功能編號:" + id + "<br>網頁錯誤<br>" + ex.toString());
        }
    }

    //顯示文字
    private void show_html(HttpServletResponse response, String msg) {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Main</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3>" + msg + "</h3>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception ex) {
            System.out.println("show_html exception =" + ex.toString());
        }
    }

    //顯示alert訊息
    private void showAlert(HttpServletResponse response, String msg, String back_url) {
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<script type=\"text/javascript\">\n"
                    + "alert('" + msg + "')\n"
                    + "location='" + back_url + "'\n"
                    + "</script>");
        } catch (Exception ex) {
            System.out.println("show_result exception =" + ex.toString());
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
