/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JimmyYang
 */
import Function.GameOdd;
import Function.GameSetting;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.LogTool;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JimmyYang 更改資料庫使用
 */
@ServerEndpoint("/Setting")
public class Setting {
    //queue holds the list of connected clients

    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();
    private GameOdd game_odd = new GameOdd(); //遊戲機本設定
    private static String game_id = "GS04"; //遊戲ID

    //收到訊息
    @OnMessage
    public void onMessage(Session session, String msg) {
        //收到更新基本數值
        try {
            JSONObject obj = new JSONObject(msg);
            game_odd.update_game_setting(obj);

            //通知所有畫面更新
            String gtype = String.valueOf(obj.get("gtype"));
            String type = String.valueOf(obj.get("type"));
            JSONObject update = new JSONObject();
            update.put("function", "update");
            update.put("gtype", gtype);
            update.put("type", type);
            update.put("data", game_odd.getOdd_Data(gtype, type));
            sendAll(update.toString());
            LogTool.showLog("update_json:" + update.toString());
        } catch (Exception e) {
            LogTool.showLog("Setting:" + e.toString());
        }
    }

    //使用者進入
    @OnOpen
    public void open(Session session) {
        queue.add(session);
    }

    //連線錯誤
    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        System.err.println("Error on session " + session.getId());
    }

    //使用者離開
    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        System.out.println("session closed: " + session.getId());
    }

    //公告所有使用者消息
    private static void sendAll(String msg) {
        try {
            /* Send the new rate to all open WebSocket sessions */
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    System.err.println("Closed session: " + session.getId());
                    closedSessions.add(session);
                } else {
                    session.getBasicRemote().sendText(msg);
                }
            }
            queue.removeAll(closedSessions);
            System.out.println("Sending to " + queue.size() + " clients");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //發送指定對象By ID
    private static void sendMsgByID(String user, String msg) {
        try {
            /* Send the new rate to one open WebSocket sessions */
            ArrayList<Session> closedSessions = new ArrayList<>();
            for (Session session : queue) {
                if (!session.isOpen()) {
                    System.err.println("Closed session: " + session.getId());
                    closedSessions.add(session);
                } else if (session.getId().equals(user)) {
                    session.getBasicRemote().sendText(msg);
                }
            }
            queue.removeAll(closedSessions);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
}
