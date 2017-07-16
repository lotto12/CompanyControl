/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang 台號、特尾三
 */
public class GamePage_game2_Left {

    private String game_id; //遊戲期數
    private String gtype; //遊戲種類
    private String type; //玩法

    private DataTable dt_water, dt_bet; //退水、賠率 資料

    //建構
    public GamePage_game2_Left(String game_id, String gtype, String type) {
        this.game_id = game_id;
        this.gtype = gtype;
        this.type = type;

        //退水資料
        String sql_back_water = "SELECT * FROM game_setting_num1 where gtype="
                + gtype + " and type_sub = 1 and type = 4 order by page";
        dt_water = DB.getDataTable(sql_back_water);

        //賠率資料
        String sql_bet = "SELECT * FROM game_setting_num1 where gtype="
                + gtype + " and type_sub = 1 and type = 4 order by page";
        dt_bet = DB.getDataTable(sql_bet);
    }

    //產出表格
    public String getHTML_TABLE() {
        //號碼上限
        int num_limit = 100;

        //表格用
        StringBuilder table = new StringBuilder();
        for (int i = 1; i <= (num_limit / 2); i++) {
            table.append("<tr>"); //行-start

            table.append("<th>").append(i).append("</th>"); //名次
            table.append("<td>").append(getNum(i)).append("</td>"); //號碼
            table.append("<td>").append(getBet_gold(i)).append("</td>"); //金額
            table.append("<td>").append(getOdds_data(0, i)).append("</td>"); //本金
            table.append("<td>").append(getOdds_data(1, i)).append("</td>"); //賠率

            table.append("<th>").append(i + 50).append("</th>"); //名次
            table.append("<td>").append(getNum(i + 50)).append("</td>"); //號碼
            table.append("<td>").append(getBet_gold(i + 50)).append("</td>"); //金額
            table.append("<td>").append(getOdds_data(0, i + 50)).append("</td>"); //本金
            table.append("<td>").append(getOdds_data(1, i + 50)).append("</td>"); //賠率

            table.append("</tr>"); //行-end
        }

        return table.toString();
    }

    //取得本金: 0 取得賠率:1
    private String getOdds_data(int function, int num) {
        DataTable dt;
        int page_num = getNum_Page(num);
        int ball_num = num;
        if (num > 25) {
            ball_num = num - (25 * (page_num - 1));
        }

        double res = 0.0;
        switch (function) {
            case 0: //本金
                dt = dt_water;
                String water = dt.getColume(page_num - 1, "ball_" + ball_num);
                res = (10000 - Integer.parseInt(water)) / 100.0;
                break;
            case 1: //賠率
                dt = dt_bet;
                String odd = dt.getColume(page_num - 1, "ball_" + ball_num);
                res = Integer.parseInt(odd) / 100.0;
                break;
        }
        return String.valueOf(res);
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
    private String getBet_gold(int num) {
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

    //回傳資料所在頁數
    private int getNum_Page(int num) {
        double ball_page = num / 25.0;
        if (ball_page < 1) {
            ball_page = 1;
        } else if (ball_page <= 2) {
            ball_page = 2;
        } else if (ball_page <= 3) {
            ball_page = 3;
        } else {
            ball_page = 4;
        }
        return (int) ball_page;
    }

    //回傳SQL球號條件式
    private String getNum_sql(int num) {
        String sql_select, page, ball;
        int num_page = getNum_Page(num);

        int ball_num = num;
        if (num > 25) {
            ball_num = num - (25 * (num_page - 1));
        }

        page = "page =" + num_page;
        ball = "ball_" + ball_num + "=1";
        sql_select = page + " and " + ball;
        return sql_select;
    }
}
