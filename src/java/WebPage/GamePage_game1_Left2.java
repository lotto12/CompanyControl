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
 * @author JimmyYang 網頁路徑GamePage/game1/Left2.jsp
 */
public class GamePage_game1_Left2 {

    //產生HTML表格
    public String createTable(String gtype) {
        StringBuilder table = new StringBuilder();
        //TABLE_START
        table.append("<table border=\"1\">");
        
        //TABLE_TITLE1
        table.append("<tr>");
        table.append("<th colspan=\"3\">全車球數包牌</th>");
        table.append("<th colspan=\"3\">全車配比包牌</th>");
        table.append("</tr>");
        table.append("<tr>");
        table.append("<td>球數</td>");
        table.append("<td>金額</td>");
        table.append("<td>本金</td>");
        table.append("<td>球數</td>");
        table.append("<td>金額</td>");
        table.append("<td>本金</td>");
        table.append("</tr>");
        table.append("<tr>");
        table.append("<td>7</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("<td>6</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("</tr>");
        table.append("<tr>");
        table.append("<td>6</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("<td>6</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("</tr>");
        table.append("<tr>");
        table.append("<td>6</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("<td>6</td>");
        table.append("<td>0</td>");
        table.append("<td>72.00</td>");
        table.append("</tr>");
        table.append("<tr>");
        table.append("<th colspan=\"3\" style=\"text-align: right\">總和<input type=\"number\" value=\"0\" style=\"text-align: right\"></input></th>");
        table.append("<th colspan=\"3\" style=\"text-align: right\">總和<input type=\"number\" value=\"0\" style=\"text-align: right\"></input></th>");
        table.append("</tr>");
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
