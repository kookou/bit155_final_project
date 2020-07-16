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
   	    ws.send('connent∥'+teamNo+"∥"+currUserNickname+"∥"+"님 접속");
    };
    // 메세지 받기
    ws.onmessage = function(evt) {
    	makeChatBox(evt.data);
    };
    ws.onerror = function(evt) {
    	console.log('웹소켓 에러 발생 : ' + evt.data);
    };
    ws.onclose = function() {
    	console.log("웹소켓 연결이 종료됨");
    };
});

$('#fixedBtn').on({
    "shown.bs.popover": function(){
        var input = $(".popover input#message");
        input.focus();
        $(document).on('keydown', input, function(key) {
        	if (key.keyCode == 13) {
        		if(input.val() != "") {
        			// 웹소켓 서버에 데이터 전송하기
        			ws.send('send∥'+teamNo+"∥"+currUserNickname+"∥"+input.val());
        			input.val("");
        		}
        	}
    	});
		$(document).on('click', '#sendBtn', function() {
			if(input.val() != "") {
				// 웹소켓 서버에 데이터 전송하기
				ws.send('send∥'+teamNo+"∥"+currUserNickname+"∥"+input.val());
				input.val("");
			}
		});
    }
});


function makeChatBox(data) {
	var nickAndMsg = data.split("∥");
	var notice = nickAndMsg[0];
	var nick = nickAndMsg[2];
	var msg = nickAndMsg[3];
	var time = nickAndMsg[4];
	console.log("에코닉네임:"+nick);
	console.log("에코메세지:"+msg);
	console.log("에코타임:"+time);
	let html = "";
	if(notice.trim() == "notice") {
		html += '<li class="chat-item odd list-style-none mt-3">';
		html += 	'<div class="chat-content text-center d-inline-block">';
		html += 		'<div class="box msg p-2 d-inline-block mb-1 box" style="background-color:#ffffcf;">'+ nick+msg +'</div>';
		html += 		'<br>';
		html += 	'</div>';
		html += '</li>';
	} else {
		if(nick.trim() == currUserNickname) {
			html += '<li class="chat-item odd list-style-none mt-3">';
			html += 	'<div class="chat-content text-right d-inline-block">';
			html += 		'<div class="box msg p-2 d-inline-block mb-1 box">'+ msg +'</div>';
			html += 		'<br>';
			html += 	'</div>';
			html += 	'<div class="chat-time text-right d-block font-10 mt-1 mr-0 mb-3 time">'+ time +'</div>';
			html += '</li>';
		} else {
			html += '<li class="chat-item list-style-none mt-3">';
			html += 	'<div class="chat-img d-inline-block">';
			html += 		'<img src="assets/images/userImage/'+ currUserImage +'" alt="user" class="rounded-circle" width="45">';
			html += 	'</div>';
			html += 	'<div class="chat-content d-inline-block">';
			html += 		'<h6 class="font-weight-medium">'+ nick +'</h6>';
			html += 		'<div class="msg p-2 d-inline-block mb-1">'+ msg +'</div>';
			html += 	'</div>';
			html += 	'<div class="chat-time d-block font-10 mt-1 mr-0 mb-3">'+ time +'</div>';
			html += '</li>';
		}
	}
	$(".popover #msgUl").append(html);
	$(".popover .chat-box").scrollTop($(".popover #msgUl")[0].scrollHeight);
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