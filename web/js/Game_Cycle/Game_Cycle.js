/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
        , title: '修改盤口 ' + game_type
        , area: ['600px', '350px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: 'Game_Cycle_Set.jsp?gtype=' + game
    });
}
