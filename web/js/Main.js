/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var wsocket;
var isOnline = false;
var check_key = "error";
var data_json = "error";

//連線
function connect() {
    //建立連線
    wsocket = new WebSocket("ws://jimmyyang.ddns.net:8084/Control/Setting");
    wsocket.onmessage = onMessage;
}

//接收訊息
function onMessage(evt) {
    var system_msg = evt.data;
    var json_data = JSON.parse(system_msg);
    var function_s = json_data.function;
    if (function_s === 'update') {
        data_json = json_data;
        update_data();
    } 
}

//傳送訊息
function doSend(message) {
    //document.getElementById('text').value = "";
    wsocket.send(message);
}

//設定密碼
function setCheck_Key(Key) {
    check_key = Key;
}

window.addEventListener("load", connect, false);


