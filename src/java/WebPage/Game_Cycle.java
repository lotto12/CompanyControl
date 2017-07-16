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
 * @author JimmyYang
 */
public class Game_Cycle {

    //產出表格
    public String showTable() {
        String sql_select = "SELECT * FROM game_cycle";
        DataTable dt = DB.getDataTable(sql_select);

        StringBuilder result = new StringBuilder();
        //TABEL START
        result.append("<table border=\"1\">");

        //TABLE_TILTE
        result.append("<tr>");
        result.append("<th colspan=\"12\">");
        result.append("盤口管理");
        result.append("</th>");
        result.append("</tr>");

        result.append("<tr>");
        String[] titles = {"盤口", "代號", "一", "二", "三", "四", "五", "六", "日", "開盤時間", "關盤時間", "功能"};
        for (String title : titles) {
            result.append("<td>");
            result.append(title);
            result.append("</td>");
        }
        result.append("</tr>");

        //TABLE_CONTENT
        String title = null;
        for (int i = 0; i < dt.getRow(); i++) {
            result.append("<tr>");
            switch (dt.getColume(i, "gtype")) {
                case "801":
                    title = "六合彩";
                    break;
                case "802":
                    title = "大樂透";
                    break;
                case "803":
                    title = "539";
                    break;
            }
            result.append("<td>");
            result.append(title);
            result.append("</td>");

            result.append("<td>");
            result.append(dt.getColume(i, "code"));
            result.append("</td>");

            String[] week = {"mo", "tu", "we", "th", "fr", "sa", "su"};
            for (String we : week) {
                String Y = "<font style=\"color: green\">Y</font>";
                String N = "<font style=\"color: red\">N</font>";
                if (dt.getColume(i, we).equals("1")) {
                    result.append("<td>");
                    result.append(Y);
                    result.append("</td>");
                } else {
                    result.append("<td>");
                    result.append(N);
                    result.append("</td>");
                }
            }

            result.append("<td>");
            result.append(dt.getColume(i, "opentime"));
            result.append("</td>");

            result.append("<td>");
            result.append(dt.getColume(i, "closetime"));
            result.append("</td>");

            result.append("<td>");
            result.append("<a href=\"javascript:showGameSet('").append(dt.getColume(i, "gtype")).append("');\">修改盤口</a>");
            result.append(" / ");
            result.append("<a href=\"javascript:\");\">賠率歸零</a>");
            result.append("</td>");

            result.append("</tr>");
        }

        //TABEL END
        result.append("</table>");
        return result.toString();
    }
}
