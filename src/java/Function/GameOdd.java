/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.DataTable;
import model.LogTool;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 設定、取得遊戲基本資料
 */
public class GameOdd {

//    public static void main(String[] args) {
//        System.out.println(new GameOdd().getOdd_Data("801", "1"));
//    }
    //更新遊戲機本設定
    public void update_game_setting(JSONObject result) {
        try {
            String gtype = result.getString("gtype");
            String type = result.getString("type");
            String type_sub;
            //System.out.println("type:" + type);
            //switch
            type_sub = result.getString("type_sub");
            JSONArray data_array = result.getJSONArray("cash_temp");

            int page = (int) Math.ceil(data_array.length() / 25.0); //頁數
            int page_count = data_array.length() % 25; //最後一頁的餘數

            int count = 0;
            for (int i = 1; i <= page; i++) {
                String sql_update = "update game_setting_num1 set ";
                int p_count = 25;
                if (i == page) {
                    p_count = page_count;
                }
                for (int j = 1; j <= p_count; j++) {
                    String num = "ball_" + j;
                    String data = String.valueOf(data_array.get(count));
                    data = String.valueOf((int) (10000 - (Double.parseDouble(data) * 100)));
                    if (j == p_count) {
                        sql_update += num + "=" + data + " ";
                    } else {
                        sql_update += num + "=" + data + ",";
                    }
                    count++;
                }
                sql_update += " where "
                        + "gtype = " + gtype + " "
                        + "and type =" + type + " "
                        + "and page =" + i + " "
                        + "and type_sub=" + type_sub + "";
                DB.query(sql_update);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
            LogTool.showLog("GameOdd(update_game_setting) Exception:" + e.toString());
        }
    }

    //801_1
    public JSONArray getOdd_Data(String gtype, String type) {
        JSONArray json_array = new JSONArray();
        JSONObject json_obj;
        //金額
        //退水
        String type_sub = "2";
        String sql_select = "select * from game_setting_num1 where "
                + " gtype ='" + gtype + "' and"
                + " type ='" + type + "' and"
                + " type_sub ='" + type_sub + "' order by page ASC";
        DataTable dt = DB.getDataTable(sql_select);
        int ball_count = 1;
        try {
            if (dt.getRow() > 0) {
                for (int i = 0; i < dt.getRow(); i++) {
                    for (int j = 1; j <= 25; j++) {
                        String ball = "ball_" + j;
                        String data = dt.getColume(i, ball);
                        if (!data.equals("null")) {
                            Double cash = Double.parseDouble(data);
                            //本金
                            cash = 100.0 - (cash /= 100);

                            json_obj = new JSONObject();
                            json_obj.put("rank", ball_count);//名次
                            json_obj.put("ball_num", ball_count); //球號
                            json_obj.put("cash", cash); //本金
                            json_obj.put("total_money", "0"); //總金額
                            ball_count++;
                            json_array.put(json_obj);
                        }
                    }
                }
            }

        } catch (Exception e) {
            LogTool.showLog("GameOdd:" + e.toString());
        }
        return json_array;
    }
}
