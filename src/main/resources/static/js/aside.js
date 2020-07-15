//초대버튼 누르면 input박스 비워주고, focus주기
$('#InviteBtn').click(function() {
	$('#searchUser').val("");
	$('#searchUser').focus();
});

////////////////////////////////////////////////////////////// All Board 추가하는 모달 부분 
$('#addBaordBtn').click(function() {
	$('#commonBoard').prop('checked', true);
	$('#kanbanBoard').prop('checked', false);
	$('#boardName').val("");
	$('#boardName').focus();
});

$('#createBoardBtn').click(function() {
//	console.log($('input[name="boardType"]:checked').val());
//	console.log($('#boardName').val());
//	console.log(teamNo);
//	console.log(currUser);
	if($('#boardName').val() == "") {
		swal("Board Name을 입력하세요");
		return;
	}
	
	$.ajax({
		url: "insertAllBoard.do",
		data: {
			name: $('#boardName').val(),
			teamNo: teamNo,
			id: currUser,
			boardTypeNo: $('input[name="boardType"]:checked').val()
		},
		success: function(resData) {
			let html = "";
			html += '<li class="sidebar-item" th:each="board: ${allBoardList}">';
			html += 	'<a class="sidebar-link redirectBoard" href="javascript:void(0);" aria-expanded="false">'; 
			html += 		'<input type="hidden" value="'+ resData +'" class="hiddenAllBoardListNo">'
			if($('input[name="boardType"]:checked').val() == '1') {
				html +=			'<i class="fas fa-table"></i>';
			} else {
				html +=			'<i class="fab fa-trello"></i>';
			}
			html +=			'<span class="hide-menu"">'+ $('#boardName').val() +'</span>';
			html += 	'</a>';
			html += '</li>';
			$('#allBoardList').append(html);
		},
		error: function(e) {
			console.log(e);
		}
	});
});

//////////////////////////////////////////////////////////////////////////////////////// autocomplete
$("#searchUser").autocomplete({
    source : function(request, response) {
    	$.ajax({
	        type: 'get',
	        url: "searchUser.do",
	        data: {
	        	id: $("#searchUser").val()
	        },
	        dataType: "json",
	        success: function(data) {
	        	//서버에서 json 데이터 response 후 목록에 추가
	        	response(
        			$.map(data, function(item) {    //json[i] 번째 에 있는게 item 임.
        				if(currUser != item) {
        					return {
        						label: item,    //UI 에서 보여지는 글자, 실제 검색어랑 비교 대상
        						value: item,    //선택 시 input창에 표시되는 값
        					}
        				}
        			})
	        	);
	        }
    	});
    },    // source 는 자동 완성 대상
    select : function(event, ui) {    //아이템 선택시
//        console.log(ui);//사용자가 오토컴플릿이 만들어준 목록에서 선택을 하면 반환되는 객체
//        console.log(ui.item.label);
//        console.log(ui.item.value);
    	$('.hiddenMemberId').each(function(index, ele) {
    		if($(this).val() == ui.item.value) {
    			swal("이미 초대된 회원입니다.");
    		}
    	});
    },
    focus : function(event, ui) {    //포커스 가면
        return false; //한글 에러 잡기용도로 사용됨
    },
    minLength: 1, // 최소 글자수
    autoFocus: true, //첫번째 항목 자동 포커스 기본값 false
    classes: {    //잘 모르겠음
        "ui-autocomplete": "highlight"
    },
    delay: 500,    //검색창에 글자 써지고 나서 autocomplete 창 뜰 때 까지 딜레이 시간(ms)
//    disabled: true, //자동완성 기능 끄기
    position: { my : "right top", at: "right bottom" },    //잘 모르겠음
    close : function(event){    //자동완성창 닫아질때 호출
//        console.log(event);
    }
})
//.autocomplete("instance")._renderItem = function(ul, item) {    //요 부분이 UI를 마음대로 변경하는 부분
//	let html = "";
//	$('.hiddenMemberId').each(function(index, ele) {
//		if($(this).val() == item.value) {
//			console.log($(this).val());
//			html += item.value;
//		} 
//		if($(this).val() != item.value) {
//			html += "<a href='javascript:void(0)'>" + item.value +  "</a>";
//		}
//	});
//    return $( "<li>" )    //기본 tag가 li로 되어 있음 
//        .append( html )    //여기에다가 원하는 모양의 HTML을 만들면 UI가 원하는 모양으로 변함.
//        .appendTo(ul);
//};

//autocomplete을 모달위로 띄어주는 방법
$("#InviteMember").on("shown.bs.modal", function() { 
	$("#searchUser").autocomplete("option", "appendTo", "#InviteMember") 
});

$('#sendInvitationBtn').click(function() {
	if($("#searchUser").val() == "") {
		swal('초대할 Email을 입력하세요.');
		$("#searchUser").focus();
		return;
	}
	$.ajax({
		url: "inviteMember.do",
		data: {
			teamNo: teamNo,
			id: $("#searchUser").val()
		},
		success: function(resData) {
			swal($("#searchUser").val() + '님이 초대되었습니다.');
			let html = "";
			html += '<div class="rounded-circle popover-item" style="float: left; background-color: white; overflow: hidden; height: 50px; width: 50px;">';
			html += 	'<div style="top: 0; left: 0; right: 0; bottom: 0; transform: translate(50%, 50%);">';
			html += 		'<img src="assets/images/userImage/'+ resData.image +'" alt="user"';
			html +=				'style="width: auto; height: 70px; transform: translate(-50%, -50%);"';
			html += 			'data-toggle="tooltip" data-placement="top" title="'+ resData.id +'">';
			html += 		'<input type="hidden" class="hiddenMemberId" value="'+ resData.id +'">';
			html +=		'</div>';
			html +=	'</div>';
			$('#profile-group').append(html);
		},
		error: function(e) {
			console.log(e);
		}
	});
});

//////////////////////////////////////////////////////////////////////////////////////// 페이지 이동
$('#allBoardList').on('click', '.redirectBoard', function() {
//	console.log($(this).find('.hiddenAllBoardListNo').val());
	console.log($(this).find('i').attr('class'));
	if($(this).find('i').attr('class') == 'fas fa-table') {
		location.href = 'boardList.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	} else {
		location.href = 'kanban.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	}
});