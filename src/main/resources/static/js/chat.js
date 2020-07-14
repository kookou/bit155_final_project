$("#fixedBtn").popover({
/*  title: "Notifiche",*/
	html: true,
	sanitize: false,
	placement: "bottom",
	content: function() {
	  return $("#alert_list").html();
	}
});

///////////////////////////////////////////////////////////웹소켓

var ws = null;
var loginId = null;

$(function () {
    ws = new WebSocket('ws://localhost:8090/chatting');
	ws.onopen = function() {
   	    console.log('웹소켓 서버 접속 성공');
    };
    // 메세지 받기
    ws.onmessage = function(evt) {
    	makeChatBox(evt.data);
    };
    ws.onerror = function(evt) {
    	swal('웹소켓 에러 발생 : ' + evt.data);
    };
    ws.onclose = function() {
    	swal("웹소켓 연결이 종료됨");
    };
});

$(document).on('click', '#sendBtn', function() {
	var msg = $("#message");
//	console.log(msg);
	// 웹소켓 서버에 데이터 전송하기
	ws.send(currUserNickname + ":" + msg.val());
	msg.val("");
});

function makeChatBox(data) {
	var nickAndMsg = data.split(":");
	var nick = nickAndMsg[0];
	var msg = nickAndMsg[1];
	let html = "";
	html += '<li class="chat-item list-style-none mt-3">';
	html += 	'<div class="chat-img d-inline-block">';
	html += 		'<img src="assets/images/userImage/'+ currUserImage +'" alt="user" class="rounded-circle" width="45">';
	html += 	'</div>';
	html += 	'<div class="chat-content d-inline-block">';
	html += 		'<h6 class="font-weight-medium">'+ nick +'</h6>';
	html += 		'<div class="msg p-2 d-inline-block mb-1">'+ msg +'</div>';
	html += 	'</div>';
	html += 	'<div class="chat-time d-block font-10 mt-1 mr-0 mb-3">';
	html += 		'10:56 am';
	html += 	'</div>';
	html += '</li>';
	$("#msgUl").append(html);
}

//$('#logoutBtn').click(function() { 
//	$.ajax({
//		url: "<c:url value='/websocket/logout.do' />"
//	})
//	.done(function (result) {
//		ws.send("logout:" + loginId);
//		$("#loginBox, #msgBox, #logoutBox").toggle();
//		loginId = null;
//		$("#result").html("");
//	});
//});