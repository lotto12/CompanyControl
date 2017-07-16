/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import model.DB;
import model.DataTable;
import model.LogTool;

/**
 *
 * @author Jimmy
 */
public class Game_Agents_conf {

    //產出佔成
    public String[][] getData_Percent(String function, String master_id, String gtype) {
        int data_length = 11;
        String[][] result;

        if (function.equals("add")) {
            //回傳空陣列
            result = new String[5][];
            for (int i = 0; i < result.length; i++) {
                result[i] = getEmptyArray(data_length);
            }
        } else {
            String sql_select = "SELECT * FROM game_agents_conf WHERE "
                    + "master_id = " + master_id + " and "
                    + "gtype=" + gtype + " "
                    + "order by type ASC";
            DataTable dt = DB.getDataTable(sql_select);
            result = new String[5][dt.getRow()];
            for (int i = 0; i < result.length; i++) {
                switch (i) {
                    case 0:
                        //佔成
                        for (int j = 0; j < result[i].length; j++) {
                            result[i][j] = dt.getColume(j, "percent");
                        }
                        break;
                    case 1:
                        //退水
                        for (int j = 0; j < result[i].length; j++) {
                            double re_cost = Double.parseDouble(dt.getColume(j, "re_cost")) / 100;
                            result[i][j] = String.valueOf(re_cost);
                        }
                        break;
                    case 2:
                        //本金
                        for (int j = 0; j < result[i].length; j++) {
                            double cost = Double.parseDouble(dt.getColume(j, "cost")) / 100;
                            result[i][j] = String.valueOf(cost);
                        }
                        break;
                    case 3:
                        //單筆上限
                        for (int j = 0; j < result[i].length; j++) {
                            result[i][j] = dt.getColume(j, "one_limit");
                        }
                        break;
                    case 4:
                        //單號上限
                        for (int j = 0; j < result[i].length; j++) {
                            result[i][j] = dt.getColume(j, "num_limit");
                        }
                        break;
                }

            }
        }
        return result;
    }

    //產生空的字串陣列
    private String[] getEmptyArray(int count) {
        String[] result = new String[count];
        for (int i = 0; i < result.length; i++) {
            result[i] = "";
        }
        return result;
    }

    //回傳色碼
    public String getColor(String gtype) {
        String result = "";
        String[] colors = {"#77FFCC", "#FFA488", "#E38EFF"};
        switch (gtype) {
            case "801":
                result = colors[0];
                break;
            case "802":
                result = colors[1];
                break;
            case "803":
                result = colors[2];
                break;
        }
        return result;
    }

    //回傳個人基本資料
    public String[] getMemberData(String function, String account) {
        String[] result = new String[7];
        String sql_select = "SELECT acount,pwd,name,money,member,top FROM game_agents where id=" + account;
        DataTable dt = DB.getDataTable(sql_select);
        if (function.equals("add")) {
            for (int i = 0; i < result.length; i++) {
                switch (i) {
                    case 5:
                        result[5] = dt.getColume(0, "member");
                        break;
                    case 6:
                        result[6] = dt.getColume(0, "top");
                        break;
                    default:
                        result[i] = "";
                        break;
                }
            }
        } else {
            result[0] = dt.getColume(0, "acount");
            result[1] = dt.getColume(0, "pwd");
            result[2] = dt.getColume(0, "pwd");
            result[3] = dt.getColume(0, "name");
            result[4] = dt.getColume(0, "money");
            result[5] = dt.getColume(0, "member");
            result[6] = dt.getColume(0, "top");
        }

        return result;
    }
}
