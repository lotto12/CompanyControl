/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang
 */
public class MyTable {

    //更新週期用
    public String getTable_1(String[] data) {
        String[] week = {"一", "二", "三", "四", "五", "六", "日"};
        StringBuilder table = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            table.append("<input type=\"checkbox\" name=\"we").append(i).append("\"");
            if (data[i].equals("1")) {
                table.append("checked=\"true\"");
            }
            table.append("></input>").append(week[i]);
        }
        return table.toString();
    }

    //首頁用
    public String getTable_2(DataTable dt) {
        StringBuilder table = new StringBuilder();
        table.append("<table border=\"1\">");
        table.append("<tr>");
        table.append("<th style=\"width: 20%\">").append("日期");
        table.append("</th>");
        table.append("<th>").append("更新日誌");
        table.append("</th>");
        table.append("</tr>");

        for (int i = 0; i < dt.getRow(); i++) {
            String date = dt.getColume(i, "build_date");
            String msg = dt.getColume(i, "msg");
            table.append("<tr>");
            table.append("<td>").append(date);
            table.append("</td>");
            table.append("<td>").append(msg);
            table.append("</td>");
            table.append("</tr>");
        }

        table.append("</table>");
        return table.toString();
    }

    //更改密碼_顯示登入狀態用
    public String getTable_3() {
        StringBuilder table = new StringBuilder();
        String sql = "select * from company_login_log order by id desc limit 25";
        DataTable dt = DB.getDataTable(sql);
        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                table.append("<tr>");
                String date = dt.getColume(i, "time");
                String ip = dt.getColume(i, "ip");
                String status = dt.getColume(i, "status");
                if (status.equals("1")) {
                    status = "<font style=\"color: green\">成功</font>";
                } else {
                    status = "<font style=\"color: red\">失敗</font>";
                }
                table.append("<td>").append(date).append("</td>");
                table.append("<td>").append(ip).append("</td>");
                table.append("<td>").append(status).append("</td>");
                table.append("</tr>");
            }
        } else {
            table.append("<tr>");
            table.append("<td colspan=\"3\">無資料</td>");
            table.append("</tr>");
        }
        return table.toString();
    }

    //會員訊息用
    public String getTable_4(DataTable dt) {
        StringBuilder table = new StringBuilder();
        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                String account = dt.getColume(i, "acount");
                String name = dt.getColume(i, "name");
                String date = dt.getColume(i, "build_date");
                String status = dt.getColume(i, "status");
                String id = dt.getColume(i, "id");

                String chk = "<input type=\"checkbox\" name=\"id[]\" value=\"" + id + "\"></input>";
                String href = "<a href=\"javascript:showFrame('詳細內容','ReadMsg.jsp?id=" + id + "');\">詳細內容</a>";
                String _name = account + "(" + name + ")";

                table.append("<tr>");
                table.append("<td>").append(chk).append("</td>");
                table.append("<td>").append(_name).append("</td>");
                table.append("<td>").append(date).append("</td>");
                table.append("<td>").append(status).append("</td>");
                table.append("<td>").append(href).append("</td>");
                table.append("</tr>");
            }
        } else {
            table.append("<tr>");
            table.append("<td colspan=\"5\">無資料</td>");
            table.append("</tr>");
        }
        return table.toString();
    }
}
