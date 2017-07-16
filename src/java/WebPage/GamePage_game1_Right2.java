/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang
 */
public class GamePage_game1_Right2 {

    //產出資料
    public String getHTML_TABLE(String game_id, String gtype) {
        StringBuilder table = new StringBuilder();
        String sql_select = "select * from game_setting_num1 where "
                + "type = 3 and "
                + "type_sub = 2 and "
                + "gtype=" + gtype + " order by page asc";

        //資料源--start
        DataTable dt = DB.getDataTable(sql_select);
        ArrayList<Double> re_cost_array = new ArrayList(); //退水資料
        DecimalFormat df = new DecimalFormat("##.0");
        for (int i = 0; i < dt.getRow(); i++) {
            for (int j = 1; j <= 25; j++) {
                String re_cost = dt.getColume(i, "ball_" + j);
                if (!re_cost.equals("null")) {
                    double re_cost_double = Double.parseDouble(re_cost) / 100;
                    String r_c = df.format(100.0 - re_cost_double);
                    re_cost_double = Double.parseDouble(r_c);
                    re_cost_array.add(re_cost_double);
                }
            }
        }
        //資料源--end

        //產出表格
        int table_limit = getGame_limlit(gtype);
        for (int i = 0; i < table_limit; i++) {
            boolean last = false;
            int count = 5;
            if (i == (table_limit - 1)) {
                count = 4;
                last = true;
            }

            table.append("<tr>");
            for (int j = 0; j < count; j++) {
                int data_count = (i + 1) + (10 * j); //資料數
                String total_cash = getBet_gold(game_id, gtype, "3", data_count); //下注總金額
                double cost = re_cost_array.get(data_count - 1); //本金資料
                table.append("<td style=\"background-color: gray;color: white;\">").append(data_count).append("</td>"); //名次
                table.append("<td id=\"rank").append(data_count).append("\">").append(getNum(data_count)).append("</td>"); //特碼
                table.append("<td id=\"money").append(data_count).append("\">").append(total_cash).append("</td>"); //總金額
                table.append("<td><a href=\"javascript:change(1,'").append(data_count).append("');\">+</a>").append("<font id = cash").append(data_count).append(">").append(cost)
                        .append("</font>").append("<a href=\"javascript:change(0,'").append(data_count).append("');\">-</a></td>"); //本金資料
            }
            if (last) {
                table.append("<td colspan=\"2\">總和</td>"
                        + "<td colspan=\"5\"><input type=\"number\" value=\"" + getTotal_Sum(game_id, gtype) + "\" style=\"text-align: right\"></input></td>");
            }
            table.append("</tr>");
        }

        return table.toString();
    }

    //TABLE行數
    private int getGame_limlit(String gtype) {
        if (gtype.equals("801") || gtype.equals("802")) {
            return 10;
        } else {
            return 9;
        }
    }

    //補零
    private String getNum(int num) {
        String result;
        if (num < 10) {
            result = "0" + num;
        } else {
            result = String.valueOf(num);
        }
        return result;
    }

    //下注金額計算
    private String getBet_gold(String game_id, String gtype, String type, int num) {
        String sql_select = "select Sum(bet_gold) as total from game_warges_main where id in"
                + " (select flw_id from game_warges_sub where "
                + "gtype = " + gtype + " and "
                + "type=" + type + " and "
                + getNum_sql(num) + ") and game_id = '" + game_id + "'";
        String total = "0";
        String[][] data = DB.getDataArray(sql_select);
        if (!data[0][0].equals("null")) {
            total = data[0][0];
        }
        return total;
    }

    //回傳SQL球號條件式
    private String getNum_sql(int num) {
        String sql_select = null;
        String page = "page = 1";
        String ball = "ball_" + num + "=1";
        if (num > 25) {
            page = "page = 2";
            num = num - 25;
            ball = "ball_" + num + "=1";
        }
        sql_select = page + " and " + ball;
        return sql_select;
    }

    //回傳總金額
    private String getTotal_Sum(String game_id, String gtype) {
        String sql_select = "select Sum(bet_gold) as total from game_warges_main where id in (select flw_id from game_warges_sub where gtype = " + gtype + " and type=3) and game_id = '" + game_id + "'";
        String result = "0";
        String[][] data = DB.getDataArray(sql_select);
        if (data.length > 0) {
            result = data[0][0];
        }
        return result;
    }
}
