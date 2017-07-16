/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//SETID
function setID(gtype) {
    window.location.href = 'SetBall.jsp?gtype=' + gtype;
}

function showGameSet(game) {
    var game_type;
    switch (game) {
        case '801':
            game_type = "六合彩";
            break;
        case '802':
            game_type = "大樂透";
            break;
        case '803':
            game_type = "539";
            break;
    }
    layer.open({
        type: 2
        , title: '新增期數 ' + game_type
        , area: ['400px', '300px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: 'SetBall_Game.jsp?gtype=' + game
    });
}

function showGameUpdate(id) {
    layer.open({
        type: 2
        , title: '修改期數'
        , area: ['400px', '300px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: 'UpdateBall_Game.jsp?id=' + id
    });
}

function showGameStart(id) {
    layer.open({
        type: 2
        , title: '開球'
        , area: ['800px', '300px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: 'StartBall.jsp?id=' + id
    });
}
