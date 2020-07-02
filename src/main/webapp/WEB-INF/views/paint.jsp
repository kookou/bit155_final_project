<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>그림판</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/game.css">
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
</head>
<body>
	<section class="content_section" style="width: 1200px">
		<div class="content_row_1">
			<div id="outer">
				<div id="canvasDiv">
					<div id="pencil">
						<img
							src="${pageContext.request.contextPath}/resources/images/game/pencil.png"
							width="200px" height="50px" id="pencilImg">
					</div>
					<div id="canvas">
						<canvas id="myCanvas" width="520" height="430"></canvas>
						<div id="rcmndDiv"></div>
					</div>
					<div id="colorArea">
				<a href='#t' class='color' data-color='#fa5a5a' id='red'><img src="${pageContext.request.contextPath}/resources/images/game/red.png"></a>
                <a href='#t' class='color' data-color='#f0d264' id='yellow'><img src="${pageContext.request.contextPath}/resources/images/game/yellow.png"></a>
                <a href='#t' class='color' data-color='#82c8a0' id='green'><img src="${pageContext.request.contextPath}/resources/images/game/green.png"></a>
                <a href='#t' class='color' data-color='#7fccde' id='blue'><img src="${pageContext.request.contextPath}/resources/images/game/blue.png"></a>
                <a href='#t' class='color' data-color='#cb99c5' id='purple'><img src="${pageContext.request.contextPath}/resources/images/game/purple.png"></a>
                <a href='#t' class='color' data-color='black' id='black'><img src="${pageContext.request.contextPath}/resources/images/game/black.png"></a>
                <a href='javascript:fill();' id='bucket'><img src="${pageContext.request.contextPath}/resources/images/game/bucket.png" width="50px" height="50px"></a>
                <a href="#t" id="clearBtn" class="btn cyan small">All Clear</a>
					</div>
				</div>
				<div id="connectedUser">
					<div id="online"></div>
				</div>
			</div>
		</div>
	</section>
	<script>
		//새로고침 막기!
		function LockF5(){
			if(event.keyCode == 116){
				event.keyCode = 0;
				swal({
					icon: "error",
					title: '이 페이지는 새로고침을 할 수 없습니다.',
					button: "닫기"
				}).then((val)=>{
					location.href="${pageContext.request.contextPath}/";
					});
				}
			};
			document.onkeydown = LockF5;




				//웹소켓을 이용해 채팅하기.
				var ws= null;
				
				$(function(){
				ws = new WebSocket("ws://localhost:8090/paint");			
				ws.onopen = function(){
					console.log("웹소켓 서버접속 성공");
					console.log("${id}");
					};
				ws.onmessage = function (evt){
					onMessage(evt);
				};
				ws.onclose = function (){
					console.log("웹소켓 서버연결 종료");
				};
				ws.onerror = function (evt){
					console.log("evt");
					dir(evt);
				};
							});

			//그림그리기
			var paintWs = null;
			var isEditable = false;

			var canvas = document.querySelector("#myCanvas");
			var paintCtx = canvas.getContext("2d");

			var isPress = false;
			var prevX = 0;
			var prevY = 0;
			var now = [];

			paintCtx.lineWidth = 3;
			paintCtx.lineCap = "round";

			$(document).ready(function(){
				paintWs = new WebSocket("ws://localhost:8090/paint");

				$("canvas").on({
					mousedown: function(e){
						if(!isEditable){return;}
						e.preventDefault();
						isPress = true;
						paintCtx.beginPath();
						prevX=e.offsetX;
						prevY=e.offsetY;
						now.push({"prevX":prevX,"prevY":prevY,"color":color});
					},
					mousemove: function(e){
						if(!isEditable){return;}
						var x= e.offsetX;
						var y= e.offsetY;
						if (isPress){
							now.push({x,y});
							paintCtx.moveTo(prevX,prevY);
							paintCtx.lineTo(x,y);
							paintCtx.stroke();
							prevX=e.offsetX;
							prevY=e.offsetY;
							if(x<=10||y<=10||x>=canvas.width-10||y>=canvas.height-10){
								isPress=false;
							}
						}
					},
					mouseup: function (e){
						if (!isEditable) {return;}
						isPress=false;
						paintCtx.closePath();
						paintWs.send(JSON.stringfy(now));
						now=[];
					}
				});
				paintWs.onmessage = function(evt){
					console.log("왜안되냐고");
					if(evt.data=="OK" || evt.data=="NO"){
						if(evt.data=="OK"){
							isEditable= true;
						}
						else{
							isEditable=false;
						}
						return;
					}
					var c = document.querySelector("#myCanvas");
					var otherCtx = c.getContext("2d");
					var drawData;
					var fillData;
					if(evt.data.startsWith('{"')){
						fillData = JSON.parse(evt.data);
						if(fillData.mode != undefined&&fillData.mode=="fill"){
							otherCtx.fillStyle = fillData.color;
							otherCtx.fillRect(0,0,canvas.width,canvas.height);
							otherCtx.closePath();
							if(fillData.color=='#f4f5ed'){
								$("#time").css("color","black");	
							}
							else {
								$("#time").css("color","white");
							}
							return;
						}
					}
					if(evt.data.startsWith('[{"')){
						drawData = JSON.parse(evt.data);
						otherCtx.strokeStyle = drawData[0].color;
						otherCtx.beginPath();
						otherCtx.moveTo(drawData[0].prevX, drawData[0].prevY);
						for(let i = 1;i<drawData.length;i++){
							otherCtx.lineTo(drawData[i].x,drawData[i].y);
						}
						otherCtx.stroke();
					}
				}
			
			});

			//캔버스 그리기 색상 바꾸기 및 지우기
			var color ="black";
			$(".color").click(function(){
				var strokeStyle = $(this).data("color");
				paintCtx.storkeStyle=strokeStyle;
				color=strokeStyle;
			});

			function fill(){
				if (!isEditable){return;}
				fillColor(color,'white');
			};
			$("canvas").contextmenu(function (e){
				e.preventDefault();
				if(!isEditable){return;}
				fillColor('#f4f5ed','black');
			});
			$("#clearBtn").click(function(){
				if(!isEditable){return;}
				fillColor('#f4f5ed','black');
			});
			//채우기색상
			function fillColor(fillColor, timeColor){
				paintCtx.fillStyle = fillColor;
				paintCtx.fillRect(0,0,canvas.width,canvas.height);
				paintCtx.closePath();
				$("#time").css("color",timeColor);
				var fill = {};
				fill.mode = "fill";
				fill.color = fillColor;
				paintWs.send(JSON.stringify(fill));
												}
		
			function restart(){
				paintWs.send("restart");
				ws.send("end");
							}	

			//canvas 영역 캡처를 위한 img태그 생성
			var photo = $('<img id="photo"/>');
			var canvasInfo = '';
			//스트림객체를 통해 캔버스의 이미저 정보를 img 태그에 삽입
			function snapshot(){
				canvasInfo = canvas.toDataURL('image/jpeg');
				$("#photo").attr("src",canvasInfo);
				console.log("canvasinfo: "+canvasInfo);
			}
			
	</script>
</body>
</html>






























