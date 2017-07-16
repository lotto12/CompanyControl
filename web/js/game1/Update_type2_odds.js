/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//正特碼雙面賠率修改
var check_final = 1;
var odds;
var gtype=801;
var type=2;

function check_odds(odds_set) {
    odds = odds_set;
    if (odds > 0) {

    } else {
        alert("1");
    }
}

doSend(getJSON());

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
    result.odd_temp = 0.89;
    //結果
    var result_json = JSON.stringify(result);
    return result_json;
}

