/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global layer */

//設定資料_m801_1(六合彩_正特碼雙面)
var datas; //所有資料
var interval = 0.1;//預設間隔
var ball_limlit = 0;//球號總數
var result = new Object();//JSON物件

//遊戲類型
var gtype;
var type = "2";

function setGtype(gtype_set) {
    gtype = gtype_set;
}

//更新資料
function update_data() {
    var gtype_u = data_json.gtype;
    var type_u = data_json.type;

    if (gtype_u === gtype && type_u === type) {
        //更新
        datas = data_json.data;
        ball_limlit = datas.length;
        for (var i in datas) {
            var obj = datas[i];
            var rank = obj.rank;
            var ball_num = obj.ball_num;
            var cash = obj.cash;
            var total_money = obj.total_money;
            document.getElementById("rank" + rank).innerHTML = ball_num;//名次球號
            document.getElementById("cash" + rank).innerHTML = cash;//本金球號
            document.getElementById("money" + rank).innerHTML = total_money;//累計獎金
        }
        document.getElementById("s6").style.backgroundColor = "#FFFF33";
        layer.msg('資料已更新');
    }
}

//初始資料
function setData() {
    datas = data_json.m801_1;
    ball_limlit = datas.length;
    for (var i in datas) {
        var obj = datas[i];
        var rank = obj.rank;
        var ball_num = obj.ball_num;
        var cash = obj.cash;
        var total_money = obj.total_money;
        document.getElementById("rank" + rank).innerHTML = ball_num;//名次球號
        document.getElementById("cash" + rank).innerHTML = cash;//本金球號
        document.getElementById("money" + rank).innerHTML = total_money;//累計獎金
    }
    document.getElementById("s6").style.backgroundColor = "#FFFF33";
    layer.msg('載入完成');
}

//設定間隔
function setInterval(interval_s, id) {
    init_s_btn();
    document.getElementById(id).style.backgroundColor = "#FFFF33";
    interval = interval_s;
}

//設定資料
function change(fun, id) {
    var cash = document.getElementById("cash" + id).innerHTML;//本金球號
    var int_cash = parseFloat(cash);
    var result;
    if (fun === 0) {
        //減少
        result = int_cash - interval;
    } else {
        //增加
        result = int_cash + interval;
    }
    result = Math.round(result * 100) / 100; //取小數點後2位
    document.getElementById("cash" + id).innerHTML = result;
}

//修改資料
function showGameUpdate(id, odds) {
    layer.open({
        type: 2
        , title: '修改賠率'
        , area: ['300px', '200px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: 'Update_type2_odds.jsp?id=' + id + '&odds=' + odds
    });
}



