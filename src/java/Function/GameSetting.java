/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.LogTool;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang
 */
public class GameSetting {

    //增加賽事
    public boolean add_game(String gtype, String period, String acc_date, String open_date, String close_date) {
        boolean result = false;
        String[] checks = {gtype, period, acc_date, open_date, close_date};
        for (String s : checks) {
            if (s.equals("") || s.length() == 0 || s == null) {
                return false;
            }
        }
        String sql_insert = "INSERT INTO game_main (gtype,period,acc_date,open_date,close_date,status) VALUES "
                + "('" + gtype + "','" + period + "','" + acc_date + "','" + open_date + "','" + close_date + "',0)";
        result = DB.query(sql_insert);
        return result;
    }

    //修改賽事
    public boolean update_game(String id, String period, String acc_date, String open_date, String close_date) {
        boolean result = false;
        String[] checks = {id, period, acc_date, open_date, close_date};
        for (String s : checks) {
            if (s.equals("") || s.length() == 0 || s == null) {
                return false;
            }
        }
        String sql_update = "update game_main set "
                + " period = '" + period + "',"
                + " acc_date = '" + acc_date + "',"
                + " open_date = '" + open_date + "',"
                + " close_date = '" + close_date + "'"
                + " where id = " + id;
        result = DB.query(sql_update);
        return result;
    }

    //開球
    public boolean start_game(String id, String[] ball, String ball_spc) {
        boolean result;
        String sql_update = "update game_main set "
                + " status = '" + 2 + "',"
                + " result_A = '" + ball[0] + "',"
                + " result_B = '" + ball[1] + "',"
                + " result_C = '" + ball[2] + "',"
                + " result_D = '" + ball[3] + "',"
                + " result_E = '" + ball[4] + "',"
                + " result_F = '" + ball[5] + "',"
                + " result_G = '" + ball_spc + "'"
                + " where id = " + id;
        result = DB.query(sql_update);
        return result;
    }

    //更新週期
    public boolean update_Cycle(String gtype, String code, String op_time, String cl_time, String[] we) {
        boolean result;
        String sql_update = "update game_cycle set "
                + " code = '" + code + "',"
                + " opentime = '" + op_time + "',"
                + " closetime = '" + cl_time + "',"
                + " mo = '" + we[0] + "',"
                + " tu = '" + we[1] + "',"
                + " we = '" + we[2] + "',"
                + " th = '" + we[3] + "',"
                + " fr = '" + we[4] + "',"
                + " sa = '" + we[5] + "',"
                + " su = '" + we[6] + "'"
                + " where gtype = '" + gtype + "'";
        result = DB.query(sql_update);
        return result;
    }

    //回覆留言
    public boolean reply_msg(String id, String msg) {
        boolean result;
        String sql_update = "update game_comment set "
                + " reply = '" + msg + "',"
                + " status = '1'"
                + " where id = '" + id + "'";
        result = DB.query(sql_update);
        return result;
    }

    //刪除留言
    public boolean del_msg(String data) {
        boolean result = false;
        try {
            JSONObject obj = new JSONObject(data);
            JSONArray obj_arry = obj.getJSONArray("id");
            if (obj_arry.length() > 0) {
                String del = "(";
                for (int i = 0; i < obj_arry.length(); i++) {
                    if (i == obj_arry.length() - 1) {
                        del += String.valueOf(obj_arry.get(i));
                    } else {
                        del += String.valueOf(obj_arry.get(i)) + ",";
                    }
                }
                del += ")";
                String sql = "DELETE FROM game_comment WHERE id IN " + del;
                result = DB.query(sql);
            }
        } catch (Exception e) {
            LogTool.showLog("DEL_MSG:" + e.toString());
        }
        return result;
    }
}
