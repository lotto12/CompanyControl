/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DB;
import model.DataTable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author wayne
 */
@WebServlet(name = "wagers_check", urlPatterns = {"/wagers_check"})
public class Wagers_check extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void wagers_check() throws JSONException {
        String sq_up, sq_sel, sq_in;
        String gtype, type, game_id, wagers_id, level_id;
        String mem_id, ag_id, sa_id, co_id, sc_id, mm_id, bm_id;
        String num1, num2, num3, num4;
        DataTable data_table, temp;
        int ag_percent, sa_percent, co_percent, sc_percent, mm_percent, bm_percent, mem_percent, com_percent;
        int ag_percen_up, sa_percen_up, co_percen_up, sc_percen_up, mm_percen_up, mem_percen_up;
        int ag_ewater, sa_ewater, co_ewater, sc_ewater, mm_ewater, bm_ewater, mem_ewater;
        int ag_earn, sa_earn, co_earn, sc_earn, mm_earn, bm_earn, com_earn;
        int ag_lose, sa_lose, co_lose, sc_lose, mm_lose, bm_lose, com_lose;
        int wagers_gold, wagers_odds, wagers_bet;
        int gold_basic, gold_lose;
        int i, j;

        JSONObject obj;
        JSONArray obj_array;

        //測試用 先將所有注單CHECK歸零
        sq_up = "UPDATE game_warges_main SET check_wagers = '0' ";
        DB.query(sq_up);

        //搜尋未分析注單
        sq_sel = "SELECT * FROM game_warges_main WHERE check_wagers ='0' ";
        data_table = DB.getDataTable(sq_sel);
        for (i = 0; i < data_table.getRow(); i++) {
            gtype = data_table.getColume(i, "gtype");
            type = data_table.getColume(i, "type");
            game_id = data_table.getColume(i, "game_id");
            wagers_id = data_table.getColume(i, "id");
            mem_id = data_table.getColume(i, "account");
            wagers_gold = Integer.valueOf(data_table.getColume(i, "cost"));
            wagers_odds = Integer.valueOf(data_table.getColume(i, "odds"));
            wagers_bet = Integer.valueOf(data_table.getColume(i, "bet_gold"));
            com_percent = 10; //公司基本占成

            //取得會員各階層ID
            sq_sel = "SELECT * FROM game_member WHERE master_id=" + mem_id + " ";
            temp = DB.getDataTable(sq_sel);
            level_id = temp.getColume(0, "top");
            obj = new JSONObject(level_id);
            obj_array = obj.getJSONArray("result");
            ag_id = obj_array.getString(5);
            sa_id = obj_array.getString(4);
            co_id = obj_array.getString(3);
            sc_id = obj_array.getString(2);
            mm_id = obj_array.getString(1);
            bm_id = obj_array.getString(0);

            switch (type) {
                case "1":
                    num1 = data_table.getColume(i, "num");
                    obj = new JSONObject(num1);
                    obj_array = obj.getJSONArray("result");
                    num1 = obj_array.getString(0);

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percen_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE id IN (" + ag_id + "," + sa_id + "," + co_id + "," + sc_id + "," + mm_id + "," + bm_id + ") ORDER BY member ";
                    temp = DB.getDataTable(sq_sel);
                    ag_percent = Integer.valueOf(temp.getColume(5, "percent"));
                    sa_percent = Integer.valueOf(temp.getColume(4, "percent"));
                    co_percent = Integer.valueOf(temp.getColume(3, "percent"));
                    sc_percent = Integer.valueOf(temp.getColume(2, "percent"));
                    mm_percent = Integer.valueOf(temp.getColume(1, "percent"));
                    bm_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    ag_percen_up = Integer.valueOf(temp.getColume(5, "percen_up"));
                    sa_percen_up = Integer.valueOf(temp.getColume(4, "percen_up"));
                    co_percen_up = Integer.valueOf(temp.getColume(3, "percen_up"));
                    sc_percen_up = Integer.valueOf(temp.getColume(2, "percen_up"));
                    mm_percen_up = Integer.valueOf(temp.getColume(1, "percen_up"));
                    ag_ewater = Integer.valueOf(temp.getColume(5, "earn_cost"));
                    sa_ewater = Integer.valueOf(temp.getColume(4, "earn_cost"));
                    co_ewater = Integer.valueOf(temp.getColume(3, "earn_cost"));
                    sc_ewater = Integer.valueOf(temp.getColume(2, "earn_cost"));
                    mm_ewater = Integer.valueOf(temp.getColume(1, "earn_cost"));
                    bm_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //計算各階層占成
                    bm_percent = bm_percent - (mm_percent + mm_percen_up);
                    mm_percent = mm_percent - (sc_percent + sc_percen_up);
                    sc_percent = sc_percent - (co_percent + co_percen_up);
                    co_percent = co_percent - (sa_percent + sa_percen_up);
                    sa_percent = sa_percent - (ag_percent + ag_percen_up);
                    ag_percent = ag_percent - (mem_percent + mem_percen_up);
                    com_percent = com_percent + mm_percen_up + sc_percen_up + co_percen_up + sa_percen_up + ag_percen_up + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - ag_ewater - sa_ewater - co_ewater - sc_ewater - mm_ewater - bm_ewater;
                    com_earn = gold_basic * com_percent + bm_ewater;
                    bm_earn = gold_basic * bm_percent + mm_ewater;
                    mm_earn = gold_basic * mm_percent + sc_ewater;
                    sc_earn = gold_basic * sc_percent + co_ewater;
                    co_earn = gold_basic * co_percent + sa_ewater;
                    sa_earn = gold_basic * sa_percent + ag_ewater;
                    ag_earn = gold_basic * ag_percent + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet * wagers_odds;
                    com_lose = gold_lose * com_percent;
                    bm_lose = gold_lose * bm_percent;
                    mm_lose = gold_lose * mm_percent;
                    sc_lose = gold_lose * sc_percent;
                    co_lose = gold_lose * co_percent;
                    sa_lose = gold_lose * sa_percent;
                    ag_lose = gold_lose * ag_percent;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + "," + mem_id + "," + ag_id + "," + sa_id + "," + co_id + "," + sc_id + "," + mm_id + "," + bm_id + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                case "6":
                    break;
                case "7":
                    break;
                case "8":
                    break;
                case "9":
                    break;
                case "10":
                    break;
                default:
                    break;
            }

        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, JSONException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Wagers_check</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet test at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
            wagers_check();
            response.sendRedirect("WebPage/GamePage/game7/game7.jsp");
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Wagers_check.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (JSONException ex) {
            Logger.getLogger(Wagers_check.class.getName()).log(Level.SEVERE, null, ex);
        }
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
