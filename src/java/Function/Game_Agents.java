/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import javax.servlet.http.HttpServletRequest;
import model.DB;
import model.DataTable;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author JimmyYang 顯示階層使用
 */
public class Game_Agents {

    //管理階層名單
    private DataTable agents;

    //會員
    private DataTable members;

    public Game_Agents() {
        String sql = "SELECT * FROM game_agents";
        agents = DB.getDataTable(sql);

        sql = "SELECT * FROM game_member";
        members = DB.getDataTable(sql);
    }

    //顯示階層Table
    public String getTable(DataTable dt, String agents, String up_account) {
        int count = 0;
        StringBuilder table = new StringBuilder();
        int member = Integer.parseInt(agents) + 1;
        if (member > 5) {
            member = 5;
        }
        agents = String.valueOf(member);
        if (dt.getRow() > 0) {
            for (int i = 0; i < dt.getRow(); i++) {
                String id = dt.getColume(i, "id"); //系統流水號
                String account = dt.getColume(i, "acount"); //帳號
                String name = dt.getColume(i, "name"); //名稱
                String pwd = dt.getColume(i, "pwd"); //密碼
                String top_num = getDown_People(id); //人數
                String cash = "<font style=\"color: blue\">" + dt.getColume(i, "money")
                        + "</font>/<font style=\"color: red\">" + dt.getColume(i, "credit") + "</font>"; //額度
                String status = dt.getColume(i, "status"); //狀態

                if (!isDownAccount(dt.getColume(i, "top"), up_account)) {
                    break;
                }
                table.append("<tr>");

                //第一欄帳號超連結
                String d1 = "<td><a href=\"Player.jsp?agents=" + agents + "&account=" + id + "\">" + account + "</a></td>";
                table.append(d1);

                //第二欄名稱
                String d2 = "<td>" + name + "</td>";
                table.append(d2);

                //第三欄人數
                String d3 = "<td style=\"font-size: 15px\">" + top_num + "</td>";
                table.append(d3);

                //第四欄額度
                String d4 = "<td>" + cash + "</td>";
                table.append(d4);

                //第五欄狀態
                String d5;
                if (status.equals("1")) {
                    d5 = "<td>啟用/收單</td>";
                } else {
                    d5 = "<td>停用</td>";
                }
                table.append(d5);

                //第六欄功能
                String d6 = "<td>"
                        + "                        <table>"
                        + "                            <tr>"
                        + "                                <td><a href=\"javascript:set_Player('" + id + "');\">詳細設定</a></td>"
                        + "                                <td><a href=\"#\">停用</a></td>"
                        + "                                <td><a href=\"#\">停押</a></td>"
                        + "                            </tr>"
                        + "                            <tr>"
                        + "                                <td><a href=\"javascript:del_player_data('" + id + "');\">刪除</a></td>"
                        + "                                <td><a id=\"p" + count + "\" href=\"javascript:show_Pwd('" + pwd + "','" + count + "');\">查詢密碼</a></td>"
                        + "                                <td><a href=\"javascript:add_Player('" + id + "');\">新增下層</a></td>"
                        + "                            </tr>"
                        + "                        </table>"
                        + "                    </td>";
                table.append(d6);

                //第七欄各項佔成
                String d7 = "<td>" + getAgent_conf(id) + "</td>";
                table.append(d7);

                count++;
            }
        }

        if (count == 0) {
            table.append("<tr>");
            table.append("<td colspan=\"7\">無資料</td>");
            table.append("</tr>");
        }
        return table.toString();
    }

    //取得各項佔成
    private String getAgent_conf(String master_id) {
        String sql_select = "SELECT * FROM lottodb.game_agents_conf "
                + "where master_id = " + master_id
                + " order by gtype ,type";
        StringBuilder table = new StringBuilder();
        DataTable dt = DB.getDataTable(sql_select);
        table.append("<table border=\"1\">");

        //TITLE
        String title = "<tr>"
                + "                                <th>玩法</th>"
                + "                                <th>全車</th>"
                + "                                <th>特碼</th>"
                + "                                <th>雙面</th>"
                + "                                <th>台號</th>"
                + "                                <th>特尾三</th>"
                + "                                <th>二星</th>"
                + "                                <th>三星</th>"
                + "                                <th>四星</th>"
                + "                                <th>天二</th>"
                + "                                <th>天三</th>"
                + "                            </tr>";
        table.append(title);

        //CONTENT
        int count = 0;
        String[] game = {"六和", "大樂", "539"};
        String[][] game_type = {
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
            {"0", "0", "3", "4", "0", "6", "7", "8", "0", "0"}};
        for (int i = 0; i < 3; i++) {
            table.append("<tr>");
            table.append("<td>").append(game[i]).append("</td>");
            for (int j = 0; j < 10; j++) {
                if ((j + 1) == Integer.parseInt(game_type[i][j])) {
                    String percent = null;
                    String percent_up = null;
                    try {
                        percent = dt.getColume(count, "percent");
                        percent_up = dt.getColume(count, "percent_up");
                    } catch (Exception e) {
                        percent = "X";
                        percent_up = "X";
                    }
                    String data = percent + "(" + percent_up + ")";
                    table.append("<td>").append(data).append("</td>");
                    count++;
                } else {
                    String data = "無";
                    table.append("<td>").append(data).append("</td>");
                }

            }
            table.append("</tr>");
        }

        table.append("</table>");
        return table.toString();
    }

    //是否為這組帳號的下層
    private boolean isDownAccount(String top, String account) {
        JSONObject obj;
        JSONArray array;
        if (account == null) {
            return true;
        }
        try {
            obj = new JSONObject(top);
            array = obj.getJSONArray("result");
            for (int j = 0; j < array.length(); j++) {
                String top_data = array.getString(j);
                if (top_data.equals(account)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Game_Agents:" + e.toString());
        }
        return false;
    }

    //搜尋下層人數
    private String getDown_People(String id) {
        int count = 0; //人數
        JSONObject obj;
        JSONArray array;
        String top;
        try {
            //管理階層
            if (agents.getRow() > 0) {
                for (int i = 0; i < agents.getRow(); i++) {
                    top = agents.getColume(i, "top");
                    if (!top.equals("0")) {
                        obj = new JSONObject(top);
                        array = obj.getJSONArray("result");
                        for (int j = 0; j < array.length(); j++) {
                            String top_data = array.getString(j);
                            if (top_data.equals(id)) {
                                count++;
                            }
                        }
                    }
                }
            }
            //會員
            if (members.getRow() > 0) {
                for (int i = 0; i < members.getRow(); i++) {
                    top = members.getColume(i, "top");
                    if (!top.equals("0")) {
                        obj = new JSONObject(top);
                        array = obj.getJSONArray("result");
                        for (int j = 0; j < array.length(); j++) {
                            String top_data = array.getString(j);
                            if (top_data.equals(id)) {
                                count++;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Game_Agents json:" + e.toString());
        }
        return String.valueOf(count);
    }

    //回傳管理階層名稱
    public String getAgents_Name(String agent) {
        String[] agents = {"大總監", "總監", "大股東", "股東", "總代理", "代理"};
        return agents[Integer.valueOf(agent)];
    }

    //進資料庫
    public boolean setData(String function, HttpServletRequest request) {
        try {
            //個人帳號
            String id = request.getParameter("id"); //id
            String account = request.getParameter("user_account"); //帳號
            String password = request.getParameter("user_password"); //密碼
            String chk_password = request.getParameter("chk_password"); //密碼確認
            String name = request.getParameter("name"); //名稱
            String cash = request.getParameter("cash"); //額度
            String member = request.getParameter("member"); //階層
            String top = request.getParameter("top"); //上層
            String gtype = request.getParameter("gtype"); //遊戲種類

            //System.out.println(top);
            //各項資料取得
            int count = 10; //資料筆數
            //佔成
            String[] array_d = new String[count]; //佔成
            for (int i = 1; i <= count; i++) {
                array_d[i - 1] = request.getParameter("d" + i);

                //System.out.println("d" + (i - 1) + ":" + array_d[i - 1]);
            }
            //退水
            String[] array_w = new String[count]; //退水
            for (int i = 1; i <= count; i++) {
                String water = String.valueOf(Double.parseDouble(request.getParameter("w" + i)) * 100);
                array_w[i - 1] = water;

                //System.out.println("w" + (i - 1) + ":" + array_w[i - 1]);
            }
            //本金
            String[] array_c = new String[count]; //本金
            for (int i = 1; i <= count; i++) {
                String d_cash = String.valueOf(Double.parseDouble(request.getParameter("c" + i)) * 100);
                array_c[i - 1] = d_cash;

                //System.out.println("c" + (i - 1) + ":" + array_c[i - 1]);
            }
            //單筆上限
            String[] array_p = new String[count]; //單筆上限
            for (int i = 1; i <= count; i++) {
                array_p[i - 1] = request.getParameter("p" + i);

                //System.out.println("p" + (i - 1) + ":" + array_p[i - 1]);
            }
            //單號上限
            String[] array_l = new String[count]; //單號上限
            for (int i = 1; i <= count; i++) {
                array_l[i - 1] = request.getParameter("l" + i);

                //System.out.println("l" + (i - 1) + ":" + array_l[i - 1]);
            }
            if (password.equals(chk_password)) {
                if (function.equals("add")) {
                    //新增下層
                    String json_top = getAdd_JSON(top, id);
                    member = String.valueOf(Integer.parseInt(member) + 1);

                    String sql_insert = "insert into game_agents(top,acount,pwd,member,name,status,money,level)"
                            + "values('" + json_top + "','" + account + "','" + password + "','" + member + "','" + name + "','1','" + cash + "','0')";
                    if (DB.query(sql_insert)) {
                        //取得master_id
                        String sql_select = "select id from game_agents where acount = '" + account + "'";
                        //System.out.println(sql_select);
                        String master_id = DB.getDataArray(sql_select)[0][0];
                        //System.out.println("master_id:" + master_id);
                        //新增佔成
                        for (int type = 1; type <= 10; type++) {
                            int data_count = type - 1;
                            String one_limit = array_p[data_count];
                            String num_limit = array_l[data_count];
                            String re_cost = array_w[data_count];
                            String cost = array_c[data_count];
                            String percent = array_d[data_count];
                            String percent_up = String.valueOf(90 - Integer.parseInt(array_d[type - 1]));
                            String sql_insert_conf = "insert into game_agents_conf(master_id,gtype,type,type_sub,percent,percent_up,cost,re_cost,one_limit,num_limit,earn_cost)"
                                    + "values('" + master_id + "'," + gtype + "," + type + ",1," + percent + "," + percent_up + ","
                                    + "'" + cost + "','" + re_cost + "'," + one_limit + "," + num_limit + ",0)";
                            //System.out.println("type:" + sql_insert_conf);
                            if (!DB.query(sql_insert_conf)) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else {
                    //更改資料
                    String sql_update = "update game_agents set "
                            + "acount = '" + account + "',"
                            + "pwd = '" + password + "',"
                            + "name = '" + name + "',"
                            + "money = '" + cash + "'"
                            + "where id = " + id;
                    //System.out.println("update account:" + sql_update);
                    if (DB.query(sql_update)) {
                        //新增佔成
                        for (int type = 1; type <= 10; type++) {
                            int data_count = type - 1;
                            String one_limit = array_p[data_count];
                            String num_limit = array_l[data_count];
                            String re_cost = array_w[data_count];
                            String cost = array_c[data_count];
                            String percent = array_d[data_count];
                            String percent_up = String.valueOf(90 - Integer.parseInt(array_d[type - 1]));

                            String sql_update_conf = "update game_agents_conf set "
                                    + "percent ='" + percent + "',"
                                    + "percent_up ='" + percent_up + "',"
                                    + "cost = '" + cost + "',"
                                    + "re_cost = '" + re_cost + "',"
                                    + "one_limit = '" + one_limit + "',"
                                    + "num_limit = '" + num_limit + "' "
                                    + "where gtype = '" + gtype + "' and type = '" + type + "' and master_id = '" + id + "'";
                            System.out.println("type_update:" + sql_update_conf);
                            if (!DB.query(sql_update_conf)) {
                                return false;
                            }
                        }
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("0616:" + e.toString());
        }
        return false;
        //個人帳號END
    }

    //建立新的上層JSON
    private String getAdd_JSON(String top, String id) {
        String result = null;
        try {
            System.out.println(top);
            System.out.println(id);
            JSONObject obj = new JSONObject(top);
            JSONArray array = obj.getJSONArray("result");
            array.put(Integer.parseInt(id));
            //----------------建立新的JSON
            obj = new JSONObject();
            obj.put("result", array);
            result = obj.toString();
        } catch (Exception e) {
            System.out.println("產出新增JSON錯誤" + e.toString());
        }
        return result;
    }
}
