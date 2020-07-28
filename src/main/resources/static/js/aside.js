/**
	파일명: aside.js
	설명: 사이드바 메뉴
	작성일: 2020-07-28
	작성자: 김혜린
**/

if(bgColor == '5f76e8') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-primary');
}
if(bgColor == '22ca80') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-success');
}
if(bgColor == 'fdc16a') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-warning');
}
if(bgColor == 'ff4f70') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-danger');
}
if(bgColor == 'e8eaec' || bgColor == 'ffffff') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-light');
}
if(bgColor == '6c757d') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-secondary');
}
if(bgColor == '1c2d41') {
	$('#InviteBtn').attr("class", 'btn waves-effect waves-light btn-rounded btn-dark');
}

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 팀이름 수정하는 함수
**/
var teamTitleTag = "";
$('#editTeamName').click(function() {
	/*console.log($(this).parents('#teaminfo-nav').find('.team-title').text());*/
	teamTitleTag = $(this).parents('#teaminfo-nav').find('.team-title');
	let teamTitleText = $(this).parents('#teaminfo-nav').find('.team-title').text();
	$('#inputTeamName').val(teamTitleText);
	$('#inputTeamName').focus();
});

$('#editOkBtn').click(function() {
	$.ajax({
		url: "editTeamName.do",
		data: {
			teamName: $('#inputTeamName').val(),
			teamNo: teamNo,
			id: currUser
		},
		success: function() {
			teamTitleTag.text($('#inputTeamName').val());
		},
		error: function(e) {
			console.log(e);
		}
	});
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : board 이름 수정하는 함수
**/
var aTag = "";
var icon = "";
var boardTag = "";
var allBoardListNo = "";
$('#allBoardList').on('click', '.editBoardName', function() {
	//다른 열려있는 input 닫아주기
	$('.addedIcon').each(function(index, ele) {
		$(this).parent().find('.board-name').show();
		$(this).parent().find('.oriIcon').show();
		$(this).parent().find('.newBoardName').remove();
		$(this).remove();
	});
	var parentTag = $(this).parents('.sidebar-item');
	aTag = $(this).parents('.sidebar-item').find('.sidebar-link');
	boardTag = $(this).parents('.sidebar-item').find('.board-name');
	var oriBoardName = $(this).parents('.sidebar-item').find('.board-name').text();
	icon = $(this).parent().parent();
	
	let html = '';
	html += '<input type="text" value="'+oriBoardName+'" class="form-control newBoardName" style="width:120px; display: inline-block;">';
	let html2 = "";
	html2 += '<div style="display: inline-block;" class="addedIcon">';
	html2 += 	'<a href="javascript:void(0);"><i class="fas fa-check iconStyle editBoardNameOk"></i></a>&nbsp;&nbsp;';
	html2 += 	'<a href="javascript:void(0);"><i class="fas fa-times iconStyle cancelEditBoardName"></i></a>';
	html2 += '</div>';
	aTag.append(html);
	aTag.after(html2);
	$('input').focus();
	boardTag.hide();
	icon.hide();
	aTag.attr('class', 'sidebar-link');
	
	allBoardListNo = $(this).parents('.sidebar-item').find('.hiddenAllBoardListNo').val();
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : Board 이름 수정하는 함수
**/
$('#allBoardList').on('click', '.editBoardNameOk', function() {
	if($('.newBoardName').val() == "") {
		Swal.fire('', '변경할 Board Name을 입력하세요', 'error');
		return;
	}
	var addedIcon = $(this).parent().parent();
	$.ajax({
		url: "editBoardName.do",
		data: {
			name: $('.newBoardName').val(),
			allBoardListNo: allBoardListNo,
			teamNo: teamNo,
			id: currUser
		},
		success: function() {
			aTag.attr('class', 'sidebar-link redirectBoard');
			icon.show();
			boardTag.text($('.newBoardName').val()).show();
			addedIcon.remove();
			$('.newBoardName').remove();
		},
		error: function(e) {
			console.log(e);
		}
	});
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 보드이름 바꾸기 취소
**/
$('#allBoardList').on('click', '.cancelEditBoardName', function() {
	aTag.attr('class', 'sidebar-link redirectBoard');
	icon.show();
	boardTag.show();
	$(this).parent().parent().remove();
	$('.newBoardName').remove();
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 보드 삭제하기
**/
$('#allBoardList').on('click', '.delBoard', function() {
	var boardName = $(this).parents('.sidebar-item').find('.board-name').text();
	allBoardListNo = $(this).parents('.sidebar-item').find('.hiddenAllBoardListNo').val();
	var deleteBoard = $(this).parents('.sidebar-item');
	
	Swal.fire({
		title: '"' + boardName + '" Board를<br>정말 삭제하시겠습니까?',
		text: "삭제되면 데이터를 복구할 수 없습니다!",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it'
	}).then((result) => {
		if (result.value) {
			$.ajax({
				url: "delBoard.do",
				data: {
					allBoardListNo: allBoardListNo,
					teamNo: teamNo,
					id: currUser
				},
				success: function() {
					deleteBoard.remove();
					Swal.fire({
						title: 'Deleted!',
						text: '"' + boardName + '" Board가 삭제되었습니다.',
						icon: 'success',
						confirmButtonText: 'OK',
					}).then((result) => {
						location.href = 'timeLine.do?teamNo=' + teamNo;
					});
				},
				error: function(e) {
					console.log(e);
				}
			});
		}
	});
	
});

//초대버튼 누르면 input박스 비워주고, focus주기
$('#InviteBtn').click(function() {
	$('#searchUser').val("");
	$('#searchUser').focus();
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : Board 추가하는 함수
**/
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
		Swal.fire('', 'Board Name을 입력하세요', 'error');
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
			html += '<li class="sidebar-item">';
			html += 	'<a class="sidebar-link redirectBoard" href="javascript:void(0);" aria-expanded="false" style="display: inline-block;">'; 
			html += 		'<input type="hidden" value="'+ resData +'" class="hiddenAllBoardListNo">'
			if($('input[name="boardType"]:checked').val() == '1') {
				html +=			'<i class="fas fa-table"></i>';
			} else {
				html +=			'<i class="fab fa-trello"></i>';
			}
			html +=			'<span class="hide-menu board-name">'+ $('#boardName').val() +'</span>';
			html += 	'</a>';
			$.each(teamMember, function(index, obj) {
				html += '<div style="display: inline-block; vertical-align: middle; height: 100%;">';
				if(obj.nickname == currUserNickname) {
					if(obj.leader == 'Y') {
						html += 	'<div>';
						html += 		'<div class="oriIcon">';
						html += 			'<a href="javascript:void(0);"><i class="fas fa-pencil-alt iconStyle editBoardName"></i></a>&nbsp;';
						html += 			'<a href="javascript:void(0);"><i class="fas fa-trash-alt iconStyle delBoard"></i></a>';
						html += 		'</div>';
						html += 	'</div>';
					}
				}
				html += '</div>';
			});
			html += '</li>';
			$('#allBoardList').append(html);
		},
		error: function(e) {
			console.log(e);
		}
	});
});

//////////////////////////////////////////////////////////////////////////////////////// autocomplete
/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : autocomplete 적용
**/
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
    			Swal.fire('', '이미 초대된 회원입니다.', 'warning');
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

//동적 생성된 태그에 툴팁 적용하기
$("body").tooltip({
    selector: '[data-toggle="tooltip"]'
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 팀원 초대하기
**/
$('#sendInvitationBtn').click(function() {
	if($("#searchUser").val() == "") {
		Swal.fire('초대할 Email을 입력하세요.');
		Swal.fire('', '초대할 Email을 입력하세요.', 'warning');
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
			Swal.fire('', $("#searchUser").val() + '님이 초대되었습니다.', 'success');
			let html = "";
			html += '<div class="rounded-circle popover-item" style="float: left; background-color: white; overflow: hidden; height: 50px; width: 50px;">';
			html += 	'<div style="top: 0; left: 0; right: 0; bottom: 0; transform: translate(50%, 50%);">';
			html += 		'<img src="assets/images/userImage/'+ resData.image +'" alt="user"';
			html +=				'style="width: auto; height: 50px; transform: translate(-50%, -50%);"';
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

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 팀원이 팀 탈퇴하기
**/
$('#teamOut').click(function() {
	Swal.fire({
		title: '"' + teamName + '" Team에서<br>정말 탈퇴하시겠습니까?',
		text: "초대를 통해서 다시 들어올 수 있습니다.",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it'
	}).then((result) => {
		if (result.value) {
			$.ajax({
				url: "teamOut.do",
				data: {
					teamNo: teamNo,
					id: currUser
				},
				success: function() {
					Swal.fire({
						title: '"' + teamName + '"',
						text: 'Team에서 탈퇴되었습니다.',
						icon: 'success',
						confirmButtonText: 'OK'
					}).then((result) => {
						if (result.value) {
							location.href = 'teamMain.do?id='+currUser;
						}
					});
				},
				error: function(e) {
					console.log(e);
				}
			});
		}
	});
});

/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : 팀장이 팀 탈퇴하기
**/
$('#passLeaderOkBtn').click(function() {
	Swal.fire({
		title: '"' + teamName + '" Team에서<br>정말 탈퇴하시겠습니까?',
		text: "초대를 통해서 다시 들어올 수 있습니다.",
		icon: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Yes, delete it!',
		cancelButtonText: 'No, keep it'
	}).then((result) => {
		if (result.value) {
			$.ajax({
				url: "leaderTeamOut.do",
				data: {
					teamNo: teamNo,
					id: currUser,
					newLeader: $('input[name="passLeader"]:checked').val()
				},
				success: function() {
					Swal.fire({
						title: '"' + teamName + '"',
						text: 'Team에서 탈퇴되었습니다.',
						icon: 'success',
						confirmButtonText: 'OK'
					}).then((result) => {
						if (result.value) {
							location.href = 'teamMain.do?id='+currUser;
						}
					});
				},
				error: function(e) {
					console.log(e);
				}
			});
		}
	});
});


////////////////////////////////////////////////////////////////////////////////////////페이지 이동
/**
* @작성일 : 2020. 7. 28.
* @작성자 : 김혜린
* @설명 : board클릭시 그 페이지로 이동하는 함수
**/
$('#allBoardList').on('click', '.redirectBoard', function() {
	//console.log($(this).find('.hiddenAllBoardListNo').val());
	console.log($(this).find('i').attr('class'));
	if($(this).find('i').attr('class') == 'fas fa-table') {
		location.href = 'boardList.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	} else {
		location.href = 'kanban.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	}
});
