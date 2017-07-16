/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DB;
import model.DataTable;
import model.LogTool;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang
 */
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
        String gtype, type, stype, game_id, wagers_id, level_id;
        String mem_id, ag_id, sa_id, co_id, sc_id, mm_id, bm_id;
        String num1, num2, num3, num4;
        DataTable data_table, temp;
        int mem_percent, com_percent;
        int mem_percen_up;
        int mem_ewater, group_num;
        long ag_earn, sa_earn, co_earn, sc_earn, mm_earn, bm_earn, com_earn;
        long ag_lose, sa_lose, co_lose, sc_lose, mm_lose, bm_lose, com_lose;
        long wagers_gold, wagers_odds, wagers_bet;
        long gold_basic, gold_lose;
        int i, j;
        String[] agents_id = new String[6];
        int[] agents_percent = new int[6];
        int[] agents_percent_up = new int[6];
        int[] agents_earn_cost = new int[6];

        JSONObject obj;
        JSONArray obj_array;

        //測試用 先將所有注單CHECK歸零
        sq_up = "UPDATE game_warges_main SET check_wagers = '0' ";
        DB.query(sq_up);

        //搜尋未分析注單
        sq_sel = "SELECT * FROM game_warges_main WHERE check_wagers ='0' ";
        data_table = DB.getDataTable(sq_sel);
        //LogTool.showLog("sq_sel:" + sq_sel);
        for (i = 0; i < data_table.getRow(); i++) {
            gtype = data_table.getColume(i, "gtype");
            type = data_table.getColume(i, "type");
            game_id = data_table.getColume(i, "game_id");
            wagers_id = data_table.getColume(i, "id");
            mem_id = data_table.getColume(i, "account");
            wagers_gold = Integer.valueOf(data_table.getColume(i, "gold"));
            wagers_odds = Integer.valueOf(data_table.getColume(i, "odds"));
            wagers_bet = Integer.valueOf(data_table.getColume(i, "bet_gold"));
            group_num = Integer.valueOf(data_table.getColume(i, "group_num"));
            com_percent = 10; //公司基本占成

            //取得會員各階層ID
            sq_sel = "SELECT * FROM game_member WHERE id=" + mem_id + " ";
            //LogTool.showLog("sq_sel:" + sq_sel);
            temp = DB.getDataTable(sq_sel);
            level_id = temp.getColume(0, "top");
            obj = new JSONObject(level_id);
            obj_array = obj.getJSONArray("result");
            agents_id[5] = obj_array.getString(5);
            agents_id[4] = obj_array.getString(4);
            agents_id[3] = obj_array.getString(3);
            agents_id[2] = obj_array.getString(2);
            agents_id[1] = obj_array.getString(1);
            agents_id[0] = obj_array.getString(0);

            switch (type) {
                case "1":
                    //抓取號碼
                    num1 = data_table.getColume(i, "num");
                    //LogTool.showLog(num1);             

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    //LogTool.showLog("sq_sel:" + sq_sel);
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percent_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    for (j = 0; j < 6; j++) {
                        sq_sel = "SELECT * FROM game_agents_conf WHERE master_id=" + agents_id[j] + " AND gtype=" + gtype + " AND type=" + type + " ";
                        temp = DB.getDataTable(sq_sel);
                        //LogTool.showLog("sq_sel:" + sq_sel);
                        agents_percent[j] = Integer.valueOf(temp.getColume(0, "percent"));
                        agents_percent_up[j] = Integer.valueOf(temp.getColume(0, "percent_up"));
                        agents_earn_cost[j] = Integer.valueOf(temp.getColume(0, "earn_cost"));
                    }

                    //計算各階層占成                    
                    agents_percent[0] = agents_percent[0] - (agents_percent[1] + agents_percent_up[1]);
                    agents_percent[1] = agents_percent[1] - (agents_percent[2] + agents_percent_up[2]);
                    agents_percent[2] = agents_percent[2] - (agents_percent[3] + agents_percent_up[3]);
                    agents_percent[3] = agents_percent[3] - (agents_percent[4] + agents_percent_up[4]);
                    agents_percent[4] = agents_percent[4] - (agents_percent[5] + agents_percent_up[5]);
                    //agents_percent[5] = agents_percent[5] - (mem_percent + mem_percen_up);
                    agents_percent[5] = mem_percent;
                    com_percent = com_percent + agents_percent_up[1] + agents_percent_up[2] + agents_percent_up[3] + agents_percent_up[4] + agents_percent_up[5] + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - agents_earn_cost[5] - agents_earn_cost[4] - agents_earn_cost[3] - agents_earn_cost[2] - agents_earn_cost[1] - agents_earn_cost[0];
                    com_earn = gold_basic * com_percent / 100 + agents_earn_cost[0];
                    bm_earn = gold_basic * agents_percent[0] / 100 + agents_earn_cost[1];
                    mm_earn = gold_basic * agents_percent[1] / 100 + agents_earn_cost[2];
                    sc_earn = gold_basic * agents_percent[2] / 100 + agents_earn_cost[3];
                    co_earn = gold_basic * agents_percent[3] / 100 + agents_earn_cost[4];
                    sa_earn = gold_basic * agents_percent[4] / 100 + agents_earn_cost[5];
                    ag_earn = gold_basic * agents_percent[5] / 100 + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet * wagers_odds;
                    com_lose = gold_lose * com_percent / 100;
                    bm_lose = gold_lose * agents_percent[0] / 100;
                    mm_lose = gold_lose * agents_percent[1] / 100;
                    sc_lose = gold_lose * agents_percent[2] / 100;
                    co_lose = gold_lose * agents_percent[3] / 100;
                    sa_lose = gold_lose * agents_percent[4] / 100;
                    ag_lose = gold_lose * agents_percent[5] / 100;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + ""
                            + "," + mem_id + "," + agents_id[5] + "," + agents_id[4] + "," + agents_id[3] + "," + agents_id[2] + "," + agents_id[1] + "," + agents_id[0] + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "2":
                    //抓取號碼
                    num1 = data_table.getColume(i, "num");

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    //LogTool.showLog("sq_sel:" + sq_sel);
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percent_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    for (j = 0; j < 6; j++) {
                        sq_sel = "SELECT * FROM game_agents_conf WHERE master_id=" + agents_id[j] + " AND gtype=" + gtype + " AND type=" + type + " ";
                        temp = DB.getDataTable(sq_sel);
                        //LogTool.showLog("sq_sel:" + sq_sel);
                        agents_percent[j] = Integer.valueOf(temp.getColume(0, "percent"));
                        agents_percent_up[j] = Integer.valueOf(temp.getColume(0, "percent_up"));
                        agents_earn_cost[j] = Integer.valueOf(temp.getColume(0, "earn_cost"));
                    }

                    //計算各階層占成                    
                    agents_percent[0] = agents_percent[0] - (agents_percent[1] + agents_percent_up[1]);
                    agents_percent[1] = agents_percent[1] - (agents_percent[2] + agents_percent_up[2]);
                    agents_percent[2] = agents_percent[2] - (agents_percent[3] + agents_percent_up[3]);
                    agents_percent[3] = agents_percent[3] - (agents_percent[4] + agents_percent_up[4]);
                    agents_percent[4] = agents_percent[4] - (agents_percent[5] + agents_percent_up[5]);
                    //agents_percent[5] = agents_percent[5] - (mem_percent + mem_percen_up);
                    agents_percent[5] = mem_percent;
                    com_percent = com_percent + agents_percent_up[1] + agents_percent_up[2] + agents_percent_up[3] + agents_percent_up[4] + agents_percent_up[5] + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - agents_earn_cost[5] - agents_earn_cost[4] - agents_earn_cost[3] - agents_earn_cost[2] - agents_earn_cost[1] - agents_earn_cost[0];
                    com_earn = gold_basic * com_percent / 100 + agents_earn_cost[0];
                    bm_earn = gold_basic * agents_percent[0] / 100 + agents_earn_cost[1];
                    mm_earn = gold_basic * agents_percent[1] / 100 + agents_earn_cost[2];
                    sc_earn = gold_basic * agents_percent[2] / 100 + agents_earn_cost[3];
                    co_earn = gold_basic * agents_percent[3] / 100 + agents_earn_cost[4];
                    sa_earn = gold_basic * agents_percent[4] / 100 + agents_earn_cost[5];
                    ag_earn = gold_basic * agents_percent[5] / 100 + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet * wagers_odds;
                    com_lose = gold_lose * com_percent / 100;
                    bm_lose = gold_lose * agents_percent[0] / 100;
                    mm_lose = gold_lose * agents_percent[1] / 100;
                    sc_lose = gold_lose * agents_percent[2] / 100;
                    co_lose = gold_lose * agents_percent[3] / 100;
                    sa_lose = gold_lose * agents_percent[4] / 100;
                    ag_lose = gold_lose * agents_percent[5] / 100;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + ""
                            + "," + mem_id + "," + agents_id[5] + "," + agents_id[4] + "," + agents_id[3] + "," + agents_id[2] + "," + agents_id[1] + "," + agents_id[0] + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "3":
                    //賽事不同組數
                    if (gtype == "803") {
                        group_num = 38;
                    } else {
                        group_num = 48;
                    }
                    //抓取號碼
                    num1 = data_table.getColume(i, "num");

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    //LogTool.showLog("sq_sel:" + sq_sel);
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percent_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    for (j = 0; j < 6; j++) {
                        sq_sel = "SELECT * FROM game_agents_conf WHERE master_id=" + agents_id[j] + " AND gtype=" + gtype + " AND type=" + type + " ";
                        temp = DB.getDataTable(sq_sel);
                        //LogTool.showLog("sq_sel:" + sq_sel);
                        agents_percent[j] = Integer.valueOf(temp.getColume(0, "percent"));
                        agents_percent_up[j] = Integer.valueOf(temp.getColume(0, "percent_up"));
                        agents_earn_cost[j] = Integer.valueOf(temp.getColume(0, "earn_cost"));
                    }

                    //計算各階層占成                    
                    agents_percent[0] = agents_percent[0] - (agents_percent[1] + agents_percent_up[1]);
                    agents_percent[1] = agents_percent[1] - (agents_percent[2] + agents_percent_up[2]);
                    agents_percent[2] = agents_percent[2] - (agents_percent[3] + agents_percent_up[3]);
                    agents_percent[3] = agents_percent[3] - (agents_percent[4] + agents_percent_up[4]);
                    agents_percent[4] = agents_percent[4] - (agents_percent[5] + agents_percent_up[5]);
                    //agents_percent[5] = agents_percent[5] - (mem_percent + mem_percen_up);
                    agents_percent[5] = mem_percent;
                    com_percent = com_percent + agents_percent_up[1] + agents_percent_up[2] + agents_percent_up[3] + agents_percent_up[4] + agents_percent_up[5] + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - agents_earn_cost[5] - agents_earn_cost[4] - agents_earn_cost[3] - agents_earn_cost[2] - agents_earn_cost[1] - agents_earn_cost[0];
                    com_earn = gold_basic * com_percent / 100 + agents_earn_cost[0];
                    bm_earn = gold_basic * agents_percent[0] / 100 + agents_earn_cost[1];
                    mm_earn = gold_basic * agents_percent[1] / 100 + agents_earn_cost[2];
                    sc_earn = gold_basic * agents_percent[2] / 100 + agents_earn_cost[3];
                    co_earn = gold_basic * agents_percent[3] / 100 + agents_earn_cost[4];
                    sa_earn = gold_basic * agents_percent[4] / 100 + agents_earn_cost[5];
                    ag_earn = gold_basic * agents_percent[5] / 100 + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet / group_num * wagers_odds;
                    com_lose = gold_lose * com_percent / 100;
                    bm_lose = gold_lose * agents_percent[0] / 100;
                    mm_lose = gold_lose * agents_percent[1] / 100;
                    sc_lose = gold_lose * agents_percent[2] / 100;
                    co_lose = gold_lose * agents_percent[3] / 100;
                    sa_lose = gold_lose * agents_percent[4] / 100;
                    ag_lose = gold_lose * agents_percent[5] / 100;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + ""
                            + "," + mem_id + "," + agents_id[5] + "," + agents_id[4] + "," + agents_id[3] + "," + agents_id[2] + "," + agents_id[1] + "," + agents_id[0] + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "4":
                    //抓取號碼
                    num1 = data_table.getColume(i, "num");
                    //LogTool.showLog(num1);             

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    //LogTool.showLog("sq_sel:" + sq_sel);
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percent_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    for (j = 0; j < 6; j++) {
                        sq_sel = "SELECT * FROM game_agents_conf WHERE master_id=" + agents_id[j] + " AND gtype=" + gtype + " AND type=" + type + " ";
                        temp = DB.getDataTable(sq_sel);
                        //LogTool.showLog("sq_sel:" + sq_sel);
                        agents_percent[j] = Integer.valueOf(temp.getColume(0, "percent"));
                        agents_percent_up[j] = Integer.valueOf(temp.getColume(0, "percent_up"));
                        agents_earn_cost[j] = Integer.valueOf(temp.getColume(0, "earn_cost"));
                    }

                    //計算各階層占成                    
                    agents_percent[0] = agents_percent[0] - (agents_percent[1] + agents_percent_up[1]);
                    agents_percent[1] = agents_percent[1] - (agents_percent[2] + agents_percent_up[2]);
                    agents_percent[2] = agents_percent[2] - (agents_percent[3] + agents_percent_up[3]);
                    agents_percent[3] = agents_percent[3] - (agents_percent[4] + agents_percent_up[4]);
                    agents_percent[4] = agents_percent[4] - (agents_percent[5] + agents_percent_up[5]);
                    //agents_percent[5] = agents_percent[5] - (mem_percent + mem_percen_up);
                    agents_percent[5] = mem_percent;
                    com_percent = com_percent + agents_percent_up[1] + agents_percent_up[2] + agents_percent_up[3] + agents_percent_up[4] + agents_percent_up[5] + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - agents_earn_cost[5] - agents_earn_cost[4] - agents_earn_cost[3] - agents_earn_cost[2] - agents_earn_cost[1] - agents_earn_cost[0];
                    com_earn = gold_basic * com_percent / 100 + agents_earn_cost[0];
                    bm_earn = gold_basic * agents_percent[0] / 100 + agents_earn_cost[1];
                    mm_earn = gold_basic * agents_percent[1] / 100 + agents_earn_cost[2];
                    sc_earn = gold_basic * agents_percent[2] / 100 + agents_earn_cost[3];
                    co_earn = gold_basic * agents_percent[3] / 100 + agents_earn_cost[4];
                    sa_earn = gold_basic * agents_percent[4] / 100 + agents_earn_cost[5];
                    ag_earn = gold_basic * agents_percent[5] / 100 + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet * wagers_odds;
                    com_lose = gold_lose * com_percent / 100;
                    bm_lose = gold_lose * agents_percent[0] / 100;
                    mm_lose = gold_lose * agents_percent[1] / 100;
                    sc_lose = gold_lose * agents_percent[2] / 100;
                    co_lose = gold_lose * agents_percent[3] / 100;
                    sa_lose = gold_lose * agents_percent[4] / 100;
                    ag_lose = gold_lose * agents_percent[5] / 100;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + ""
                            + "," + mem_id + "," + agents_id[5] + "," + agents_id[4] + "," + agents_id[3] + "," + agents_id[2] + "," + agents_id[1] + "," + agents_id[0] + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "5":
                    //抓取號碼
                    num1 = data_table.getColume(i, "num");
                    //LogTool.showLog(num1);             

                    //讀取會員玩法設定
                    sq_sel = "SELECT * FROM game_member_conf WHERE master_id=" + mem_id + " AND gtype=" + gtype + " AND type=" + type + " ";
                    //LogTool.showLog("sq_sel:" + sq_sel);
                    temp = DB.getDataTable(sq_sel);
                    mem_percent = Integer.valueOf(temp.getColume(0, "percent"));
                    mem_percen_up = Integer.valueOf(temp.getColume(0, "percent_up"));
                    mem_ewater = Integer.valueOf(temp.getColume(0, "earn_cost"));

                    //讀取所有階層設定
                    for (j = 0; j < 6; j++) {
                        sq_sel = "SELECT * FROM game_agents_conf WHERE master_id=" + agents_id[j] + " AND gtype=" + gtype + " AND type=" + type + " ";
                        temp = DB.getDataTable(sq_sel);
                        //LogTool.showLog("sq_sel:" + sq_sel);
                        agents_percent[j] = Integer.valueOf(temp.getColume(0, "percent"));
                        agents_percent_up[j] = Integer.valueOf(temp.getColume(0, "percent_up"));
                        agents_earn_cost[j] = Integer.valueOf(temp.getColume(0, "earn_cost"));
                    }

                    //計算各階層占成                    
                    agents_percent[0] = agents_percent[0] - (agents_percent[1] + agents_percent_up[1]);
                    agents_percent[1] = agents_percent[1] - (agents_percent[2] + agents_percent_up[2]);
                    agents_percent[2] = agents_percent[2] - (agents_percent[3] + agents_percent_up[3]);
                    agents_percent[3] = agents_percent[3] - (agents_percent[4] + agents_percent_up[4]);
                    agents_percent[4] = agents_percent[4] - (agents_percent[5] + agents_percent_up[5]);
                    //agents_percent[5] = agents_percent[5] - (mem_percent + mem_percen_up);
                    agents_percent[5] = mem_percent;
                    com_percent = com_percent + agents_percent_up[1] + agents_percent_up[2] + agents_percent_up[3] + agents_percent_up[4] + agents_percent_up[5] + mem_percen_up;

                    //計算各階層賺
                    gold_basic = wagers_gold - mem_ewater - agents_earn_cost[5] - agents_earn_cost[4] - agents_earn_cost[3] - agents_earn_cost[2] - agents_earn_cost[1] - agents_earn_cost[0];
                    com_earn = gold_basic * com_percent / 100 + agents_earn_cost[0];
                    bm_earn = gold_basic * agents_percent[0] / 100 + agents_earn_cost[1];
                    mm_earn = gold_basic * agents_percent[1] / 100 + agents_earn_cost[2];
                    sc_earn = gold_basic * agents_percent[2] / 100 + agents_earn_cost[3];
                    co_earn = gold_basic * agents_percent[3] / 100 + agents_earn_cost[4];
                    sa_earn = gold_basic * agents_percent[4] / 100 + agents_earn_cost[5];
                    ag_earn = gold_basic * agents_percent[5] / 100 + mem_ewater;

                    //計算各階層賠
                    gold_lose = wagers_bet * wagers_odds;
                    com_lose = gold_lose * com_percent / 100;
                    bm_lose = gold_lose * agents_percent[0] / 100;
                    mm_lose = gold_lose * agents_percent[1] / 100;
                    sc_lose = gold_lose * agents_percent[2] / 100;
                    co_lose = gold_lose * agents_percent[3] / 100;
                    sa_lose = gold_lose * agents_percent[4] / 100;
                    ag_lose = gold_lose * agents_percent[5] / 100;

                    //將注單check修改1 表示分析過
                    sq_up = "UPDATE game_warges_main SET check_wagers = '1' WHERE id=" + wagers_id + " ";
                    DB.query(sq_up);
                    //寫入資料
                    sq_in = "INSERT INTO game_cal_" + gtype + "_" + type + " (game_id,wagers_id,gtype,status,num,mem_id,ag_id,sa_id,co_id,sc_id,mm_id,bm_id,"
                            + "mem_money,ag_money,sa_money,co_money,sc_money,mm_money,bm_money,com_money,mem_lose,ag_lose,sa_lose,co_lose,sc_lose,mm_lose,bm_lose,com_lose) "
                            + "VALUES (" + game_id + "," + wagers_id + "," + gtype + ",1," + num1 + ""
                            + "," + mem_id + "," + agents_id[5] + "," + agents_id[4] + "," + agents_id[3] + "," + agents_id[2] + "," + agents_id[1] + "," + agents_id[0] + ""
                            + "," + gold_basic + "," + ag_earn + "," + sa_earn + "," + co_earn + "," + sc_earn + "," + mm_earn + "," + bm_earn + "," + com_earn + ""
                            + "," + gold_lose + "," + ag_lose + "," + sa_lose + "," + co_lose + "," + sc_lose + "," + mm_lose + "," + bm_lose + "," + com_lose + ") ";
                    DB.query(sq_in);
                    break;
                case "6":
                    stype = data_table.getColume(i, "stype");

                    //根據下注方式細分
                    switch (stype) {
                        case "1":
                            //抓取號碼
                            num1 = data_table.getColume(i, "num");
                            obj = new JSONObject(num1);
                            obj_array = obj.getJSONArray("result");
                            obj_array = obj_array.getJSONArray(0);
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        default:
                            break;
                    }

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Wagers_check</title>");
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Wagers_check at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
            try {
                wagers_check();
                out.println("完成");
            } catch (Exception e) {
                String exception = getPrintStackTrace(e);
                out.println(exception);
            }

            //response.sendRedirect("WebPage/GamePage/game7/game7.jsp");
        }
    }

    public String getPrintStackTrace(Exception ex) {
        Writer result = new StringWriter();
        PrintWriter printWriter = new PrintWriter(result);
        ex.printStackTrace(printWriter);

        return result.toString();
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
