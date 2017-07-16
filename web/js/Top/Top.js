/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//切換功能
function change(id) {
    for (var i = 1; i <= 6; i++) {
        $('#f' + i).css('display', 'none');
    }
    $('#f' + id).fadeIn('fast');
}

//連結
function changePwd() {
    window.open('../WebPage/ChangePwd.jsp', 'Mid');
}

//登出
function logout() {
    top.window.location.replace("../System/logout.jsp");
}


