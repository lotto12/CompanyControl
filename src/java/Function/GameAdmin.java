/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Function;

import java.text.SimpleDateFormat;
import java.util.Date;
import model.DB;
import model.DataTable;

/**
 *
 * @author JimmyYang
 */
public class GameAdmin {

    //判斷管理會員帳號密碼是否正確_紀錄上線時間
    public boolean isAdminOK(String user_account, String user_pwd, String ip) {
        boolean result = false;
        String sql_selct_str = "select password from company_operate where account = '" + user_account + "'";
        String[][] sql_selct = DB.getDataArray(sql_selct_str);
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String strDate = sdFormat.format(date);
        if (sql_selct.length > 0) {
            if (sql_selct[0][0].equals(user_pwd)) {
                //紀錄上線時間
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String sql_update = "update game_member set on_time ='" + strDate + "' where acount = '" + user_account + "'";
                        DB.query(sql_update);

                        //登入紀錄
                        String log = "insert into company_login_log (account , ip , status , time) values ('" + user_account + "','" + ip + "','1','" + strDate + "')";
                        DB.query(log);
                    }
                }).start();
                result = true;
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //登入紀錄
                        String log = "insert into company_login_log (account , ip , status , time) values ('" + user_account + "','" + ip + "','0','" + strDate + "')";
                        DB.query(log);
                    }
                }).start();
            }
        }
        return result;
    }

    //密碼驗證更新
    public String updatePwd(String acount, String old_pwd, String new_pwd, String new_pwd_chk) {
        String result = "密碼更新失敗";
        if (new_pwd.equals(new_pwd_chk)) {
            String sql_selct_str = "select password from company_operate where account = '" + acount + "'";
            String[][] sql_selct = DB.getDataArray(sql_selct_str);
            if (sql_selct[0][0].equals(old_pwd)) {
                String sql_update = "update company_operate set password ='" + new_pwd + "' where account = '" + acount + "'";
                DB.query(sql_update);
                result = "密碼更新成功";
            } else {
                result = "舊密碼錯誤";
            }
        } else {
            result = "請確認新密碼輸入正確";
        }
        return result;
    }

    //產出管理者html表格
    public String getHTML_table() {
        StringBuilder table = new StringBuilder();
        String sql_select = "SELECT account,name,password,case(status) when 1 then '啟用'  when 0 then '停用' end as status FROM company_operate";
        DataTable dt = DB.getDataTable(sql_select);
        for (int i = 0; i < dt.getRow(); i++) {
            String account = dt.getColume(i, "account");
            String name = dt.getColume(i, "name");
            String status = dt.getColume(i, "status");
            String function = ""
                    + "                        <input type=\"button\" value=\"修改密碼\"></input>\n"
                    + "                        <input type=\"button\" value=\"停用\"></input>\n"
                    + "                        <input type=\"button\" value=\"刪除\"></input>\n"
                    + "                        <input type=\"button\" value=\"查詢密碼\"></input>\n"
                    + "";

            table.append("<tr>");
            table.append("<td>").append(account).append("</td>");
            table.append("<td>").append(name).append("</td>");
            table.append("<td>").append(status).append("</td>");
            table.append("<td>").append(function).append("</td>");
            table.append("</tr>");
        }
        return table.toString();
    }
}
