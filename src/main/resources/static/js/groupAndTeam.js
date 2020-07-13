//팀 클릭시 페이지 이동
$('#outer').on('click', '.teamBtn', function() {
	console.log($(this).attr('data-teamNo'));
	location.href = 'timeLine.do?teamNo=' + $(this).attr('data-teamNo');
});
    
	var inputGroupNameHtml =
		'<div class="row" id="inputGroupNameDiv">' + 
			'<div class="col-xl-3">' +
				'<input type="text" placeholder="그룹명을 입력하세요" class="form-control" id="inputGroupName">' +
			'</div>' +
			'<div class="col-xl-3">' +
				'<button class="btn btn-info btn-circle" id="addGroupName"><i class="fa fa-check"></i></button>' +
			'</div>' +
		'</div>';
	var editGroupNameHtml =
		'<div class="row" id="editGroupNameDiv">' + 
			'<div class="col-xl-3">' +
				'<input type="text" placeholder="그룹명을 입력하세요" class="form-control" id="editGroupName">' +
			'</div>' +
		'</div>';
		
	//addGroupBtn 눌렀을 때
	$('#addGroupBtn').click(function() {
		//다른 열려있는 input 닫아주기
		$('input').each(function(index, ele) {
			$(this).parent().parent().prev().show();
			$(this).parent().parent().remove();
		});
		$('#outer').append(inputGroupNameHtml);
		$('#addGroupBtn').hide();
		$('#inputGroupNameDiv').find('input').focus();
	});
	
	//그룹명 입력받고 체크버튼 눌렀을 때
	$(document).on('click', '#addGroupName', function() {
		if($('#inputGroupName').val() == "") {
			swal('그룹명을 입력하세요.');
			$(this).parent().prev().children('input').focus();
			return;
		}
    	var html = "";
		$.ajax({
			url: "insertGroup.do",
			data: {
				groupName: $('#inputGroupName').val(),
				id: currUser
			},
			success: function(resData) {
				html = 
					'<div class="row" ondrop="drop(event)" ondragover="allowDrop(event)">' + 
	        			'<div class="col-xl-12"  style="height: 100%;"  data-groupNo="' + resData + '">' + 
	        				'<div class="groupNameArea">' +
		        				'<h2 th:utext="${item.groupName}" style="display: inline-block; vertical-align: middle;">' + $('#inputGroupName').val() + '</h2>&nbsp;' +
		        				'<h6 style="display: inline-block; vertical-align: middle;" >' +
				        			'<a href="javascript:void(0);" class="editGroupName"><i class="fas fa-pencil-alt iconStyle iconStyle"></i></a>&nbsp;' + 
			                        '<a href="javascript:void(0);" class="delGroupName"><i class="fas fa-trash-alt iconStyle iconStyle"></i></a>' + 
		        				'</h6>' + 
		        			'</div>' +
	        			'</div>' + 
	        			'<div class="col-xl-3">' +
	        				'<div class="card" style="text-align: center;">' +
	        					'<div class="card-body collapse show newTeamBtn" data-toggle="modal" data-target="#createNewTeamModal" style="text-align: center;">' +
	        						'+ Create New Team' +
	        					'</div>' +
	        				'</div>' +
	        			'</div>' +
	        		'</div>';
				$('#addGroupDiv').before(html);
				$('#inputGroupNameDiv').remove();
				$('#addGroupBtn').show();
			},
			error: function(e) {
				console.log(e);
			}
		});
	});
	
	//그룹 수정버튼 눌렀을 때
	$('#outer').on('click', '.editGroupName', function() {
		//다른 열려있는 input 닫아주기
		$('input').each(function(index, ele) {
			$(this).parent().parent().prev().show();
			$(this).parent().parent().remove();
			$('#inputGroupNameDiv').remove();
			$('#addGroupBtn').show();
		});
			
		//그룹 번호
		let groupNo = $(this).parent().parent().parent().data('groupno');
		//원래 그룹명
		let oriGroupName = $(this).parent().prev().text();
		//groupNameArea 영역, 숨겨지는 부분
		let groupNameArea = $(this).parent().prev().parent();
		//groupNameArea의 부모. 이 밑에 input을 append
		let groupNameAreaParent = $(this).parent().prev().parent().parent();
		
		groupNameArea.hide();
		groupNameAreaParent.append(editGroupNameHtml);
		groupNameAreaParent.find('input').focus();
	});
	
	$('#outer').on('keypress', '#editGroupName', function(key) {
		//그룹 번호
		let groupNo = $(this).parent().parent().parent().data('groupno');
		if (key.keyCode == 13) {
			if($(this).val() == "") {
				swal('그룹명을 입력하세요.');
				$(this).focus();
				return;
			}
			$.ajax({
				url: "updateGroupName.do",
				data: {
					groupName: $(this).val(),
					groupNo: groupNo
				},
				error: function(e) {
					console.log(e);
				}
			});
			//groupNameArea 영역, 다시 보여지는 부분
			let groupNameArea = $(this).parent().parent().prev();
			groupNameArea.children('h2').text($(this).val());
			groupNameArea.show();
			$(this).parent().parent().remove();
		}
	});
	
	//그룹 삭제버튼 눌렀을 때
	$('#outer').on('click', '.delGroupName', function() {
		//그룹 번호
		let groupNo = $(this).parent().parent().parent().data('groupno');
		swal({
			title: "Group을 삭제하시겠습니까?",
			text: "Group을 삭제하면 그 Group에 속한 Tema List는 Personal Group으로 이동됩니다.",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
		.then((willDelete) => {
		    if(willDelete) {
		    	var promise = 
		    		$.ajax({
						url: "delGroup.do",
						data: {
							groupNo: groupNo,
							id: currUser
						},
						error: function(e) {
							console.log(e);
						}
					});
		    	promise.done(reloadListPromise);
		    	promise.fail(promiseError);
			}
		});
	});
	
	function reloadListPromise() {
		return new Promise(function(resolve, reject) {
			$.ajax({
				url: "teamList.do",
				data: {
					id: currUser
				},
				success: function(resData) {
					console.log(resData.group);
					$('#outer').empty();
					makeListHtml(resData);
				},
				error: function(e) {
					console.log(e);
				}
			});
		});
	}
	function promiseError(e) {
		console.log("프로미스 에러..");
		console.log(e);
		return false;
	}
	
	//목록 그리는 함수
	function makeListHtml(resData) {
		let html = '';
		$.each(resData.group, function(index, obj) {
			html += '<div class="row" ondrop="drop(event)" ondragover="allowDrop(event)">';
			html += 	'<div class="col-xl-12"  style="height: 100%;" data-groupNo= '+ obj.groupNo +'>';
			html += 		'<div class="groupNameArea">';
			html += 			'<h2 style="display: inline-block; vertical-align: middle;">'+ obj.groupName +'</h2>&nbsp;';
			if(obj.groupName != 'Personal') {
    			html += 		'<h6 style="display: inline-block; vertical-align: middle;">';
				html += 			'<a href="javascript:void(0);" class="editGroupName"><i class="fas fa-pencil-alt iconStyle iconStyle"></i></a>&nbsp;';
				html += 			'<a href="javascript:void(0);" class="delGroupName"><i class="fas fa-trash-alt iconStyle iconStyle"></i></a>';
				html += 		'</h6>';
			}
			html += 		'</div>';
			html += 	'</div>';
			$.each(resData.groupAndTeam, function(index2, obj2) {
				if(obj.groupName == obj2.groupName) {
				html += '<div class="col-xl-3">';
				html += 	'<div class="card" style="background-color:#'+ obj2.backgroundColor +'">';
				html += 		'<div class="card-body collapse show teamBtn" draggable="true" ondragstart="drag(event)" data-teamNo="'+ obj2.teamNo +'">';
				html += 			'<h4 class="card-title">'+ obj2.teamName +'</h4>';
				html += 		'</div>';
				html += 	'</div>';
				html += '</div>';
				}
			});
			html += 	'<div class="col-xl-3">' +
								'<div class="card">' +
									'<div class="card-body collapse show newTeamBtn" data-toggle="modal" data-target="#createNewTeamModal" style="text-align: center;">' +
										'+ Create New Team' +
									'</div>' +
								'</div>' +
							'</div>' +
						'</div>';
		});
			html +=	'<div class="row" id="addGroupDiv">' +
							'<div class="col-xl-3">' +
					            '<div class="btn waves-effect waves-light btn-outline-primary" id="addGroupBtn" >+ Add Group</div>' +
					        '</div>' +
						'</div>';
			$('#outer').append(html);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////// Team 만들기
	var appendTeam = "";
	$('.newTeamBtn').click(function() {
		$('#ffffff').prop('checked', true);
		$('#d0e1f5').prop('checked', false);
		$('#c0e4da').prop('checked', false);
		$('#fee993').prop('checked', false);
		$('#fac8bf').prop('checked', false);
		$('#fedcc1').prop('checked', false);
		$('#teamName').val("");
		$('#teamName').focus();
		$('#hiddenGroupNo').val($(this).parent().parent().parent().children().attr('data-groupno'));
		appendTeam = $(this).parent().parent();
	});
	
	$('#createTeamBtn').click(function() {
		console.log(appendTeam);
		console.log($('#teamName').val());
		console.log($('#hiddenGroupNo').val());
		console.log($('input[name="backColor"]:checked').val());
		if($('#teamName').val() == "") {
			swal("Team Name을 입력하세요");
			return;
		}
		
		$.ajax({
			url: "insertTeam.do",
			data: {
				teamName: $('#teamName').val(),
				backgroundColor: $('input[name="backColor"]:checked').val(),
				groupNo: $('#hiddenGroupNo').val()
			},
			success: function(resData) {
				let html = "";
				html += '<div class="col-xl-3">';
				html += 	'<div class="card" style="background-color:#'+ $('input[name="backColor"]:checked').val() +'">';
				html += 		'<div class="card-body collapse show teamBtn" draggable="true" ondragstart="drag(event)" data-teamNo="'+ resData +'">';
				html += 			'<h4 class="card-title">'+ $('#teamName').val() +'</h4>';
				html += 		'</div>';
				html += 	'</div>';
				html += '</div>';
				appendTeam.before(html);
				
				$.ajax({
					url: "insertTeamLeader.do",
					data: {
						teamNo: resData,
						id: currUser
					},
					error: function(e) {
						console.log(e);
					}
				});
			},
			error: function(e) {
				console.log(e);
			}
		});
	});
	
	
	////////////////////////////////////////////////////////////////////////////// drag & drop
	//ondragover='allowDrop(event)'  dragover의 기본이벤트 막기
	function allowDrop(ev) {
		ev.preventDefault();
	}

	//drag & drop 이벤트를 위한 모든 event listener method는 DataTransfer 객체를 반환합니다.
	//이렇게 반환된 DataTransfer 객체는 드래그 앤 드롭 동작에 관한 정보를 가지고 있게 됩니다.
	function drag(ev) {
		console.log($(ev.target).attr('data-teamNo'));
		console.log($(ev.target).parent().parent().parent().children().attr('data-groupno'));
	    ev.dataTransfer.setData("teamNo", $(ev.target).attr('data-teamNo'));
	    ev.dataTransfer.setData("dragGroupNo", $(ev.target).parent().parent().parent().children().attr('data-groupno'));
	}

	function drop(ev) {
	    ev.preventDefault();
	    //드랍하는 요소의 className을 DataTransfer 객체에서 가져오기
	    let dragTeamNo = ev.dataTransfer.getData("teamNo");
	    let dragGroupNo = ev.dataTransfer.getData("dragGroupNo");
	    let dropGroupNo = $(ev.target).parents('.row').children().attr('data-groupNo');
//	    if(dropListCode == undefined) {
//	    	dropListCode = $(ev.target).attr('data-code');
//	    }
	    console.log("dragTeamNo : " + dragTeamNo);
	    console.log("dragGroupNo : " + dragGroupNo);
	    console.log("dropGroupNo : " + dropGroupNo);
	    
	    if(dragGroupNo != dropGroupNo) {
	    	var promise = 
	    		$.ajax({
					url: "moveTeamFromGroup.do",
					data: {
						teamNo: dragTeamNo,
		    			groupNo: dropGroupNo,
		    			id: currUser
					},
					error: function(e) {
						console.log(e);
					}
				});
	    	promise.done(reloadListPromise);
	    	promise.fail(promiseError);
	    }
	}
