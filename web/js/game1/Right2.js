/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//遊戲類型
var gtype;
var interval = 0.1;//預設間隔
var type = "3";
var ball_limlit = 49;//球號總數

function setGtype(gtype_set) {
    gtype = gtype_set;
    document.getElementById("s6").style.backgroundColor = "#FFFF33";
}

//設定間隔
function setInterval(interval_s, id) {
    init_s_btn();
    document.getElementById(id).style.backgroundColor = "#FFFF33";
    interval = interval_s;
}

//設定區塊
function setNum(type) {
    init_b_btn();
    init_ball_color();
    var num;
    switch (type) {
        case 1: //單
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                if (num % 2 !== 0) {
                    document.getElementById("rank" + i).style.color = "white";
                    document.getElementById("rank" + i).style.backgroundColor = "red";
                }
            }
            break;
        case 2: //雙
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                if (num % 2 === 0) {
                    document.getElementById("rank" + i).style.color = "white";
                    document.getElementById("rank" + i).style.backgroundColor = "red";
                }
            }
            break;
        case 3: //大
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                if (num > 24) {
                    document.getElementById("rank" + i).style.color = "white";
                    document.getElementById("rank" + i).style.backgroundColor = "red";
                }
            }
            break;
        case 4: //小
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                if (num < 25) {
                    document.getElementById("rank" + i).style.color = "white";
                    document.getElementById("rank" + i).style.backgroundColor = "red";
                }
            }
            break;
        case 5: //合單
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                //console.log(num);
                if (i < 10) {
                    if (num % 2 !== 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 9 && i < 20) {
                    if (num % 2 === 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 19 && i < 30) {
                    if (num % 2 !== 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 29 && i < 40) {
                    if (num % 2 === 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 39 && i < 50) {
                    if (num % 2 !== 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                }
            }
            break;
        case 6: //合雙
            for (var i = 1; i <= ball_limlit; i++) {
                num = parseInt(document.getElementById("rank" + i).innerHTML);
                console.log(num);
                if (i < 10) {
                    if (num % 2 === 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 9 && i < 20) {
                    if (num % 2 !== 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 19 && i < 30) {
                    if (num % 2 === 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 29 && i < 40) {
                    if (num % 2 !== 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                } else if (i > 39 && i < 50) {
                    if (num % 2 === 0) {
                        document.getElementById("rank" + i).style.color = "white";
                        document.getElementById("rank" + i).style.backgroundColor = "red";
                    }
                }
            }
            break;
    }
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
    
     doSend(getJSON());
}

//重置間隔色彩
function init_s_btn() {
    for (var i = 1; i <= 6; i++) {
        document.getElementById("s" + i).style.backgroundColor = null;
    }
}

//重置區塊色彩
function init_b_btn() {
    for (var i = 1; i <= 6; i++) {
        document.getElementById("b" + i).style.backgroundColor = null;
    }
}

//球號色彩還原
function init_ball_color() {
    for (var i = 1; i <= ball_limlit; i++) {
        document.getElementById("rank" + i).style.color = null;
        document.getElementById("rank" + i).style.backgroundColor = null;
    }
}

//改變紅色藍位
function change_all(fun) {
    var interval_s;
    var result;
    if (fun === 0) {
        interval_s = -1 * interval;
    } else {
        interval_s = interval;
    }
    for (var i = 1; i <= ball_limlit; i++) {
        var rank = document.getElementById("rank" + i).style.backgroundColor;
        if (rank === 'red') {
            var cash = document.getElementById("cash" + i).innerHTML;//本金球號
            var int_cash = parseFloat(cash);
            result = int_cash + interval_s;
            result = Math.round(result * 100) / 100; //取小數點後2位
            document.getElementById("cash" + i).innerHTML = result;
        }
    }
}

//改變全體BY數字
function change_all_num(id) {
    var num = document.getElementById(id).value;
    for (var i = 1; i <= ball_limlit; i++) {
        var rank = document.getElementById("rank" + i).style.backgroundColor;
        if (rank === 'red') {
            document.getElementById("cash" + i).innerHTML = num;
        }
    }
}

//組合JSON
function getJSON() {
    var array = new Array();
    for (var i = 1; i <= ball_limlit; i++) {
        for (var j = 1; j <= ball_limlit; j++) {
            var ball_num = document.getElementById("rank" + j).innerHTML;
            ball_num = parseInt(ball_num);
            if (ball_num === i) {
                var ball_cash = document.getElementById("cash" + j).innerHTML;
                array.push(ball_cash);
            }
        }
    }
    result = new Object();
    result.function = "update";
    result.gtype = gtype;
    result.type = type;
    result.type_sub = "2";
    result.cash_temp = array;
    //結果
    var result_json = JSON.stringify(result);
    return result_json;
}

//取消
function cancel() {
    init_ball_color();
}

//發送
function check() {
    //配置一个透明的询问框
    layer.msg('確定要變更遊戲基本數值嗎', {
        time: 20000, //20s后自动关闭
        btn: ['確定更改', '取消更改']
        , yes: function () {
            layer.msg('已更改');
            //console.log(getJSON());
            doSend(getJSON());
        }
    });
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
        //layer.msg('資料已更新');
    }
}