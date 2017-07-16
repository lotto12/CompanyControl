/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//顯示小視窗
function showFrame(name, url) {
    layer.open({
        type: 2
        , title: name
        , area: ['600px', '600px']
        , shade: 0.1
        , offset: [
            0, 0
        ]
        , maxmin: false
        , content: url
    });
}

//SETID_訊息用
function setMsgStatus(status) {
    window.location.href = 'Message.jsp?status=' + status;
}

//訊息用_全選
function chkall(chk) {
    var checkboxs = document.getElementsByName("id[]");
    for (var i = 0; i < checkboxs.length; i++) {
        checkboxs[i].checked = chk;
    }
}

//確認刪除
function del() {
    var array = [];
    var checkboxs = document.getElementsByName("id[]");
    for (var i = 0; i < checkboxs.length; i++) {
        if (checkboxs[i].checked === true) {
            var value = checkboxs[i].value;
            array.push(value);
        }
    }
    var result = new Object();
    result.id = array;

    var str = JSON.stringify(result);
    var theForm = document.forms['del_form'];
    document.getElementById('data').value = str;
    theForm.submit();
}

//新增玩家
function add_Player(id) {
    var index = layer.open({
        type: 2,
        title: '新增下層',
        content: 'Set_Player.jsp?gtype=801&function=add&account=' + id,
        maxmin: false
    });
    layer.full(index);
}

//顯示密碼
function show_Pwd(pwd, id) {
    layer.tips('密碼：' + pwd, '#p' + id, {
        tips: [3, '#0066FF']
    });
}

//玩家詳細設定
function set_Player(id) {
    var index = layer.open({
        type: 2,
        title: '詳細設定',
        content: 'Set_Player.jsp?gtype=801&function=set&account=' + id,
        maxmin: false
    });
    layer.full(index);
}

//確認是否要刪除使用者資料
function del_player_data(master_id) {
    layer.msg('確定要刪除嗎?', {
        time: 10000, //10s後關閉
        btn: ['確認', '取消'],
        yes: function () {
            window.location = 'del_Player.jsp?master_id=' + master_id;
        },
        btn2: function () {
            layer.msg('已取消');
        }
    });
}