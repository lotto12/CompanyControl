/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebPage;

import model.DataTable;

/**
 *
 * @author JimmyYang
 */
public class SetBall {

    //產生表格
    public String getTable(DataTable dt) {
        StringBuilder result = new StringBuilder();
        //TABEL START
        result.append("<table border=\"1\" id=\"tbl\">");

        //TABLE_TITLE
        String[] titles = {"盤口", "期數", "日期", "開盤時間", "關盤時間", "狀態", "功能", "備份"};
        result.append("<thead>");
        result.append("<tr>");
        for (int i = 0; i < titles.length; i++) {
            result.append("<th>");
            result.append(titles[i]);
            result.append("</th>");
        }
        result.append("</tr>");
        result.append("</thead>");

        //TABLE_CONTENT
        result.append("<tbody>");
        if (dt.getRow() > 0) {
            String[] dt_title = {"period", "acc_date", "open_date", "close_date"};
            for (int i = 0; i < dt.getRow(); i++) {
                result.append("<tr>");

                //GTYPE
                result.append("<td>");
                switch (dt.getColume(i, "gtype")) {
                    case "801":
                        result.append("六和");
                        break;
                    case "802":
                        result.append("大樂");
                        break;
                    case "803":
                        result.append("539");
                        break;
                }
                result.append("</td>");

                //CONTENT
                for (int j = 0; j < dt_title.length; j++) {
                    result.append("<td>");
                    result.append(dt.getColume(i, dt_title[j]));
                    result.append("</td>");
                }

                //STATUS
                switch (dt.getColume(i, "status")) {
                    case "0":
                        result.append("<td>");
                        result.append("未開盤");
                        result.append("</td>");
                        result.append("<td>");
                        result.append("<a href=\"javascript:showGameUpdate('").append(dt.getColume(i, "id")).append("');\">修改</a>");
                        result.append("</td>");
                        break;
                    case "1":
                        result.append("<td>");
                        result.append("<font style=\"color: red\">未開獎</font>");
                        result.append("</td>");
                        result.append("<td>");
                        result.append("<a href=\"javascript:showGameUpdate('").append(dt.getColume(i, "id")).append("');\">修改</a>");
                        result.append(" | ");
                        result.append("<a href=\"javascript:showGameStart('").append(dt.getColume(i, "id")).append("');\">開球</a>");
                        result.append("</td>");
                        break;
                    case "2":
                        result.append("<td>");
                        result.append("<font style=\"color: blue\">已開球</font>");
                        result.append("</td>");
                        result.append("<td>");
                        result.append("<a href=\"javascript:showGameUpdate('").append(dt.getColume(i, "id")).append("');\">修改</a>");
                        result.append("</td>");
                        break;
                    case "3":
                        result.append("<td>");
                        result.append("<font style=\"color: orange\">已結算</font>");
                        result.append("</td>");
                        result.append("<td>");
                        result.append("<a href=\"javascript:showGameUpdate('").append(dt.getColume(i, "id")).append("');\">修改</a>");
                        result.append("</td>");
                        break;
                }

                result.append("<td>");
                result.append("<a href=\"\">下載</a>");
                result.append("</td>");

                result.append("</tr>");
            }
        } else {
            result.append("<tr>");
            result.append("<td colspan=\"8\">");
            result.append("尚無新增資料");
            result.append("</td>");
            result.append("</tr>");
        }
        result.append("</tbody>");

        //TABEL END
        result.append("</table>");
        return result.toString();
    }

    //開球
    public String getBall_TABLE(String gtype, String perid) {
        int count = 6; //球數
        int spc = 1; //特碼
        if (gtype.equals("803")) {
            count = 5;
            spc = 0;
        }
        int total_count = count + spc + 1;

        //GTYPE
        String name = null;
        switch (gtype) {
            case "801":
                name = "六合彩";
                break;
            case "802":
                name = "大樂透";
                break;
            case "803":
                name = "539";
                break;
        }

        StringBuilder result = new StringBuilder();
        //TABEL START
        result.append("<table border=\"1\">");

        //TABLE_TILTE
        result.append("<tr>");
        result.append("<th colspan=\"").append(total_count).append("\">");
        result.append("開獎管理");
        result.append("</th>");
        result.append("</tr>");

        result.append("<tr>");
        result.append("<td>");
        result.append("期數");
        result.append("</td>");
        for (int i = 1; i <= count; i++) {
            result.append("<td>");
            result.append(i).append(" 號球");
            result.append("</td>");
        }
        if (spc == 1) {
            result.append("<td>");
            result.append("特別號");
            result.append("</td>");
        }
        result.append("</tr>");

        //TABLE_CONTENT
        result.append("<tr>");
        result.append("<td style=\"width: 15%\">");
        result.append(name).append("\n").append(perid);
        result.append("</td>");
        for (int i = 1; i <= count; i++) {
            result.append("<td>");
            result.append("<input required=\"required\" style=\"width: 60%\" type=\"number\" name=\"num").append(i).append("\"></input>");
            result.append("</td>");
        }
        if (spc == 1) {
            result.append("<td>");
            result.append("<input required=\"required\" style=\"width: 60%\" type=\"number\" name=\"num").append("_spc").append("\"></input>");
            result.append("</td>");
        }
        result.append("</tr>");

        //TABLE_SET
        result.append("<tr>");
        result.append("<th colspan=\"").append(total_count).append("\">");
        result.append("<input type=\"submit\" class=\"button\"value=\"全部設定\"></input>");
        result.append("</th>");
        result.append("</tr>");
        //TABEL END
        result.append("</table>");
        return result.toString();
    }
}
