/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import model.DB;
import model.DataTable;
import model.LogTool;

/**
 *
 * @author JimmyYang 網頁路徑GamePage/game1/Left1.jsp
 */
public class GamePage_game1_Left1 {

    //產生HTML表格
    public String createTable(String gtype) {
        StringBuilder table = new StringBuilder();
        //TABLE_START
        table.append("<table border=\"1\">");

        //TABLE_TITLE1
        table.append("<tr>");
        table.append("<th colspan=\"8\">正特碼雙面【有和局】</th>");
        table.append("</tr>");

        //TABLE_TITLE2
        table.append("<tr>");
        table.append("<td></td>");
        table.append("<td>單雙</td>");
        table.append("<td>下注金額</td>");
        table.append("<td>賠率</td>");
        table.append("<td>大小</td>");
        table.append("<td>球頭</td>");
        table.append("<td>下注金額</td>");
        table.append("<td>賠率</td>");
        table.append("</tr>");

        //TABLE_CONTENT
        int j = 0;
        String sql_str = "";
        int gold_big, gold_small, gold_even, gold_odd;
        float odds_big, odds_small, odds_even, odds_odd;
        int head, head_one, head_two, head_three;
        String head_mid;
        DataTable data_table;

        for (int i = 0; i < 7; i++) {
            //Getdata
            //球頭
            sql_str = "SELECT * "
                    + "FROM game_setting_num1 "
                    + "Where gtype=801 AND type=2 AND type_sub=4 ";
            //LogTool.showLog("sql------>" + sql_str);
            data_table = DB.getDataTable(sql_str);
            head = Integer.valueOf(data_table.getColume(0, "ball_1"));
            head_one = head / 1000;
            head = head % 1000;
            head_two = head / 100;
            if (head_two == 0) {
                head_mid = "+";
            } else {
                head_mid = "-";
            }
            head_three = head % 100;
            sql_str = "SELECT * "
                    + "FROM game_setting_num1 "
                    + "Where gtype=801 AND type=2 AND type_sub=1 ";
            data_table = DB.getDataTable(sql_str);
            //金額賠率
            if (i == 6) {
                j += 1;
                odds_odd = Float.parseFloat(data_table.getColume(0, "ball_" + j)) / 100;
                j = j - 24;
                odds_even = Float.parseFloat(data_table.getColume(0, "ball_1" + j)) / 100;
                j += 1;
                odds_big = Float.parseFloat(data_table.getColume(0, "ball_1" + j)) / 100;
                j += 1;
                odds_small = Float.parseFloat(data_table.getColume(0, "ball_1" + j)) / 100;
            } else {
                j += 1;
                odds_odd = Float.parseFloat(data_table.getColume(0, "ball_" + j)) / 100;
                j += 1;
                odds_even = Float.parseFloat(data_table.getColume(0, "ball_" + j)) / 100;
                j += 1;
                odds_big = Float.parseFloat(data_table.getColume(0, "ball_" + j)) / 100;
                j += 1;
                odds_small = Float.parseFloat(data_table.getColume(0, "ball_" + j)) / 100;
            }

            //OutputTable
            table.append("<tr>");
            switch (i) {
                case 0:
                    table.append("<td rowspan=\"2\">正碼一</td>");
                    break;
                case 1:
                    table.append("<td rowspan=\"2\">正碼二</td>");
                    break;
                case 2:
                    table.append("<td rowspan=\"2\">正碼三</td>");
                    break;
                case 3:
                    table.append("<td rowspan=\"2\">正碼四</td>");
                    break;
                case 4:
                    table.append("<td rowspan=\"2\">正碼五</td>");
                    break;
                case 5:
                    table.append("<td rowspan=\"2\">正碼六</td>");
                    break;
                case 6:
                    table.append("<td rowspan=\"2\">特碼</td>");
                    break;
            }
            table.append("<td>單</td>");
            table.append("<td>0</td>");
            table.append("<td><a href=\"javascript:showGameUpdate(").append(i*4+1).append(",").append(odds_odd).append(")\">").append(odds_odd).append("</a></td>");
            table.append("<td>大</td>");
            table.append("<td rowspan=\"2\">").append(head_one).append(head_mid).append(head_three).append("%</td>");
            table.append("<td>0</td>");
            table.append("<td><a href=\"#\" onclick=\"showGameUpdate(").append(i*4+2).append(",").append(odds_big).append(")\">").append(odds_big).append("</a></td>");
            table.append("</tr>");

            table.append("<tr>");
            table.append("<td>雙</td>");
            table.append("<td>0</td>");
            table.append("<td><a href=\"#\" onclick=\"showGameUpdate(").append(i*4+3).append(",").append(odds_even).append(")\">").append(odds_even).append("</a></td>");
            table.append("<td>小</td>");
            table.append("<td>0</td>");
            table.append("<td><a href=\"#\" onclick=\"showGameUpdate(").append(i*4+4).append(",").append(odds_small).append(")\">").append(odds_small).append("</a></td>");
            table.append("</tr>");
        }

        //TABLE_TOTAL
        table.append("<tr>");
        table.append("<th colspan=\"8\" style=\" text-align: right;\">總和：<input type=\"number\" value=\"0\" style=\"text-align: right\"></input></th>");
        table.append("</tr>");

        //TABLE_END
        table.append("</table>");
        return table.toString();
    }

    private int getGame_limlit(String gtype) {
        if (gtype.equals("801") || gtype.equals("802")) {
            return 10;
        } else {
            return 9;
        }
    }
}
