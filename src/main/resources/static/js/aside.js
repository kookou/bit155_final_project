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

////////////////////////////////////////////////////////////////////////////////////////

$('#allBoardList').on('click', '.redirectBoard', function() {
//	console.log($(this).find('.hiddenAllBoardListNo').val());
	console.log($(this).find('i').attr('class'));
	if($(this).find('i').attr('class') == 'fas fa-table') {
		location.href = 'boardList.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	} else {
		location.href = 'kanban.do?allBoardListNo=' + $(this).find('.hiddenAllBoardListNo').val() + '&teamNo=' + teamNo;
	}
});