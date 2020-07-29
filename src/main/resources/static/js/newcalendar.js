/**
파일명: newcalendar.js
    설명: 캘린더 script 파일
    작성일: 2020-07-28
    작성자: 박혜정
**/

//풀캘린더가 사용하는 변수들
	var calendarEl = document.getElementById('calendar');
	var addBtnContainer = $('.modalBtnContainer-addEvent');
	var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');
	var calendar =""; // 캘린더 전역변수화
	var draggedEventIsAllDay;
	var modalTitle = $('.modal-title');
	var editStart = $('#edit-start');
	var editEnd = $('#edit-end');
	
/**
* @함수명 : renderFullCalendar()
* @작성일 : 2020-07-28
* @작성자 : 박혜정
* @설명 : 풀캘린더를 생성하는 함수
**/	
function renderFullCalendar(){
	
	// 풀캘린더 옵션들
	calendar = new FullCalendar.Calendar(calendarEl, {
		defaultTimedEventDuration: '01:00:00',
		timeFormat: 'HH:mm',
		editable: true,
		eventResizableFromStart: true,	
		selectable: true,
		locale: 'ko',
		initialView: 'dayGridMonth',
		contentHeight: 1000,
		dayMaxEventRows: true,
		
		//캘린더 헤더 설정
		headerToolbar: {
			left: 'prev,next today',
			center: 'title',
			right: 'dayGridMonth,timeGridWeek,timeGridDay'
		},
		
		eventColor: '#E6CDED', //default 컬러 설정
		displayEventTime: true,
		
		//캘린더 내용을 셀렉트 하여 보내주는 콜백 함수
		events: function(Info, successCallback, failureCallback) {
			$.ajax({
				url: "showCalendar.ajax",
				data: { teamNo :teamNo },
				
				success: function(response) {
					var fixedDate = response.map(function(array) {
						
						//db의 allDay 컬럼은 String값으로 true/false를 저장해놓았기 때문에 fullcalendar가 인식할 수 있게 boolean 타입으로 수정해줘야 함
						if(array.allDay == 'true'){array.allDay=true}
						else{array.allDay=false};
						return array;
					});
					successCallback(fixedDate);
				}
			}); 

		},
		
		
		
		/**
		* @함수명 : select: function(start, end, allDay)
		* @작성일 : 2020-07-28
		* @작성자 : 박혜정
		* @설명 : 풀캘린더 선택시 불리는 함수
		**/	
		select: function(start, end, allDay) {
			//원본 start 데이터를 사용하기 위해 담았다 
			var instart = start;
			var inend = start;
			
			//현재 시간 불러오기
			var today = moment();
			
			//start 객체 안에 start 현재 시간 set 해주기 (안하면 00:00)
			var selectstart = instart.start.setHours(today.hours());
			selectstart = instart.start.setMinutes(today.minutes());
			start = moment(selectstart).format('YYYY-MM-DD HH:mm');
			
			//start 객체 안에 end 현재 시간 set 해주기 (안하면 00:00)
			var selectend = inend.end.setHours(today.hours());
			selectend = inend.end.setMinutes(today.minutes()+10);
			
			//start 객체 안의 end 데이터는 날짜가 +1 되어있기 때문에 하루를 잘라준다.
			selectend = moment(selectend).subtract(1, 'days');
			end = moment(selectend).format('YYYY-MM-DD HH:mm');
			
			// 모달 안 태그값 초기화
			$('#eventModal input, textarea').val("");
			$('#customCheck2').prop("checked", false);
			$('#eventModal option:eq(0)').prop("selected", true);
			$('#edit-color option:eq(0)').prop("selected", true); 
			
			//모달에 현재 시간을 세팅한 날짜 데이터 보내기
			$('#edit-start').val(start);
			$('#edit-end').val(end);
			
			// 모달 타이틀 바꾸기
			modalTitle.html('새로운 일정');

			// 모달 버튼 바꾸기
			modifyBtnContainer.hide();
			addBtnContainer.show();

			// 모달 열기
			$('#eventModal').modal('show');
			
			//중복 이벤트 방지를 위해 이벤트 unbind
			$('#save-event').unbind();
			
			
			/**
			* @함수명 : $('#save-event').on('click', function()
			* @작성일 : 2020-07-28
			* @작성자 : 박혜정
			* @설명 : 풀캘린더 일정 저장하는 이벤트
			**/	
			$('#save-event').on('click', function() {
				
				var start = $('#edit-start').val();
				var end = $('#edit-end').val();
				var titleVal = $('#edit-title').val();
				var contentVal = $('#edit-desc').val();
				var colorVal = $('#edit-color').val();
				
				// #allday 체크 여부에 따라 값 부여하기 
				var allDay = $('#customCheck2');
				var isAllDay;
	
				if(allDay.is(':checked')) {isAllDay = true;}
				else {isAllDay = false; }
	
				var eventData = {
					id: currUserId,
					title: titleVal,
					content: contentVal,
					start: start,
					end: end,
					allDay: isAllDay, 
					color: colorVal,
					teamNo : teamNo
				};

				if(eventData.title == "") {
					Swal.fire({
						  icon: 'warning',
						  text: '일정명을 입력해주세요.'
						})
					return false;
				}
		
				if(eventData.start > eventData.end) {
					Swal.fire({
						  icon: 'warning',
						  text: '끝나는 날짜가 시작하는 날짜보다 앞설 수 없습니다.'
						})
						return false;
				}
				
				if (isAllDay == true) {
					eventData.start = moment(eventData.start).format('YYYY-MM-DD');
					eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
				}
	
				// DB에 일정 넣기
				var realEndDay;
				var realStartDay;
				
				if(eventData.allDay) { // allDay=true일 때
					realStartDay = moment(eventData.start).format('YYYY-MM-DD');
					realEndDay = moment(eventData.end).format('YYYY-MM-DD');
				} else { // allDay=false일 때
					realStartDay = moment(eventData.start).format('YYYY-MM-DD HH:mm');
					realEndDay = moment(eventData.end).format('YYYY-MM-DD HH:mm');
				}

				var DBdata = {
					id: currUserId,
					title: titleVal,
					description: contentVal,
					allDay: isAllDay, 
					color: colorVal,
					start: realStartDay,
					end: realEndDay,
					teamNo : teamNo
				};
				
				$('#eventModal').modal('hide');

				$.ajax({
					data: DBdata,
					url: "addPlan.ajax",
					async: false, //eventData객체에 DB에서 가져온 sindex값을 넣기 위해서 동기식 처리 옵션 지정
					success: function(data) {
						Swal.fire({
							  icon: 'success',
							  text: '일정이 등록되었습니다.'
							})
						eventData.no=data;
						
					},
					error: function(e) {
						Swal.fire({
							  icon: 'error',
							  text: '일정 등록에 실패하였습니다.'
							})
					}
				});
				// 이벤트 추가
				calendar.addEvent(eventData);
				calendar.render();
				
			}); // /.저장하기
			
		},
		
		/**
		* @함수명 : eventMouseEnter: function(event)
		* @작성일 : 2020-07-28
		* @작성자 : 박혜정
		* @설명 : popover 로 일정 내용 띄워주는 함수
		**/	
		eventMouseEnter: function(event){
			$(event.el).popover({
				title: $('<div />', {
			        class: 'popoverTitleCalendar',
			        //일정명
			        text: event.event.title
			      }).css({
			    	  //타이틀 색상을 일정 색상에 맞게 변경
			        'background': event.event.backgroundColor,
			        'color' : '#ffffff'
			      }),
			      
			      //popover 화면에 뿌릴 데이터 
			      content: $('<div />', {
			          class: 'popoverInfoCalendar'
			        }).append('<p><strong>등록자 : </strong> ' + event.event.id+ '</p>')
			        .append('<p><strong>일정 기간 : </strong> ' + getDisplayEventDate(event) + '</p>')
			        .append('<p><strong>일정 시간 : </strong> ' + getDisplayEventTime(event) + '</p>')
			        .append('<div class="popoverDescCalendar"><strong>일정 내용 : </strong> ' + hoverdescription(event) + '</div>'),
			      delay: {
			    	  
			     //popover 창 뜨는 시간 
			        show: "200",
			        hide: "50"
			      },
			      trigger: 'hover',
			      placement: 'top',
			      html: true,
			      container: 'body'
			    });
			    return true;
			  }, 

		//일정을 클릭하면 수정창이 나와 처리하는 메서드
		eventClick: function(event, jsEvent, view) { 
			console.log("오나?")
			editEvent(event);
//			calendar.render();
		},
		
		eventDragStart: function (event, jsEvent, ui, view) {
			    draggedEventIsAllDay = event.el.fcSeg.eventRange.def.allDay;
		},
		
		//일정 드래그앤드롭으로 변경하는 메서드
		eventDrop: function (event) {
			//주,일 view일때 종일 <-> 시간 변경불가
			 if (event.view.type === 'timeGridWeek' || event.view.type === 'timeGridDay') { 
		     	if (draggedEventIsAllDay !== event.event.allDay) {
		     		Swal.fire({
						  icon: 'warning',
						  text: '드래그앤드롭으로 종일<->시간 변경은 불가합니다.'
						})
			        location.reload();  //임시로 리로드로 예외처리
		        	return false;
		        }
		     }
			dndResize(event);		
		},
		eventResize: function(event){
			dndResize(event);
		}
	});
		calendar.render(); //init
}

	
	//일정을 클릭하면 수정창이 나와 처리하는 메서드	
	var editEvent = function(event, element, view) {
		console.log(event.event.extendedProps.description)
		var title = event.event.title;
		var content = event.event.extendedProps.description;
		var no = event.event.extendedProps.no;
		var allday = event.event.allDay; 

		var start = event.event.start;
		var end = event.event.end; 
		var color = event.event.backgroundColor;
		var id = event.event._def.publicId;
		
		
		// input 태그 초기화
		$('#customCheck2').prop("checked", false);
		$('#edit-title').val('');
		$('#edit-start').val('');
		$('#edit-end').val('');
		$('#edit-desc').val('');
		
		// 모달 타이틀 바꾸기
		modalTitle.html('일정 수정');

		// 모달 버튼 바꾸기
		addBtnContainer.hide();
		modifyBtnContainer.show();

		// 기존 정보 뿌리기
		$('#edit-title').val(title);
		$('#edit-desc').val(content); 
		$('#edit-start').val(moment(start).format('YYYY-MM-DD HH:mm'));
		$('#edit-end').val(moment(end).format('YYYY-MM-DD HH:mm'));
		$('#edit-color').val(color).prop("selected", true);
		
		// 하루종일 여부 체크
		if(allday) {
			$('#customCheck2').prop("checked", true);
		} else {
			$('#customCheck2').prop("checked", false);
		}

		// 모달 열기 > 마지막에 열자
		$('#eventModal').modal('show');
		
		if($('#title').val() == "") {
			Swal.fire({
				  icon: 'warning',
				  text: '일정명을 입력해주세요.'
				})
			return false;
		}

		if($('#start').val() > $('#end').val() ) {
			Swal.fire({
				  icon: 'warning',
				  text: '끝나는 날짜가 시작하는 날짜보다 앞설 수 없습니다.'
				})
			return false;
		}
		
		$('#updateEvent').unbind();
		$('#updateEvent').on('click', function() {

			// #allday 체크 여부에 따라 값 부여하기 
			var allDay = $('#customCheck2');
			var isAllDay;
			
			if(allDay.is(':checked')) { isAllDay = true; }
			else { isAllDay = false; }
			
			
			//event 객체 업데이트 (DB는 아님)
			event.event.setProp("title", $('#edit-title').val());
			event.event.setStart($('#edit-start').val());
			event.event.setEnd($('#edit-end').val());
			event.event.setProp("backgroundColor", $('#edit-color').val());
			event.event.setAllDay(isAllDay);
			event.event.setExtendedProp("description", $('#edit-desc').val());
			
			$('#eventModal').modal('hide');

			$.ajax({
					type: "post",
					data: {
						no: no,
						id: currUserId,
						title: $('#edit-title').val(),
						start: moment($('#edit-start').val()).format('YYYY-MM-DD HH:mm:ss'),
						end: moment($('#edit-end').val()).format('YYYY-MM-DD HH:mm:ss'),
						description: $('#edit-desc').val(),
						allDay: isAllDay, 
						color: $('#edit-color').val(),
						teamNo : teamNo
					},
					url: "updatePlan.ajax",
					async: false,
					success: function(response) {
							Swal.fire({
								  icon: 'success',
								  text: '일정이 수정되었습니다.'
								})
						},
					error: function(e) {
						Swal.fire({
							  icon: 'error',
							  text: '일정 수정에 실패하였습니다.'
							})
						}

				});
			calendar.render();
			});


		//이벤트 중복 방지를 위해 unbind
		$('#deleteEvent').unbind();
		//삭제버튼 눌렀을 때 삭제 처리 함수
		$('#deleteEvent').on('click', function() {
			
			$('#eventModal').modal('hide');
			Swal.fire({
			      text: "정말로 일정을 삭제하시겠습니까?",
			      icon: 'warning',
			      showCancelButton: true,
			      confirmButtonText: '네',
			      cancelButtonText: '아니오'
			   }).then((result) => {
			      if (result.value) {
			         var promise = 
			        	 $.ajax({
								data: {
									no : no			
								},
								url: "deletePlan.ajax",
								async: false,
								success: function(response) {
									Swal.fire({
										  icon: 'success',
										  text: '일정이 삭제되었습니다.'
										})
										event.event.remove();
									},
								error: function(e) {
									Swal.fire({
										  icon: 'error',
										  text: '일정 삭제에 실패하였습니다.'
										})
									}

							});
			          promise.done(reloadListPromise);
			          promise.fail(promiseError);
			      }
			   });
			});
	}

	//재사용을 위해 모듈화
	
	/**
	* @함수명 : function dndResize(event)
	* @작성일 : 2020-07-28
	* @작성자 : 박혜정
	* @설명 : 드래그 앤 드랍, 리사이즈 구현
	**/	
	function dndResize(event){
		  // 드랍시 수정된 날짜반영
		  //var newDates = calDateWhenDragnDrop(event);  //퍼올 커스텀 함수인데 우선 보류
		  var no = event.event.extendedProps.no;
		  var newStart = event.event.start;
		  var newEnd = event.event.end;
		 
		  //드롭한 일정 업데이트
		  $.ajax({
				type: "post",
				data: {
					no: no,
					start: moment(newStart).format('YYYY-MM-DD HH:mm:ss'),
					end: moment(newEnd).format('YYYY-MM-DD HH:mm:ss'),
				},
				url: "updatePlanDrag.ajax",
				success: function(response) {
					Swal.fire({
						  icon: 'success',
						  text: '일정이 수정되었습니다.'
						})
					},
				error: function(e) {
					Swal.fire({
						  icon: 'error',
						  text: '일정 수정에 실패하였습니다.'
						})
					
					}
			});
		   	calendar.render();
		
	}
	
	//마우스 호버시 보여질 내용 항목 함수
	function hoverdescription(event){
		console.log(event.event.extendedProps.description)
		var displayeventde = $.trim(event.event.extendedProps.description)
		
		console.log(displayeventde)
		
		if(displayeventde == ""){
			displayeventde = "일정 내용이 없습니다.";
		}
//		else{
//			displayeventde = displayeventde
//		}
		return displayeventde;
	}
	
	//마우스 호버시 보여질 시간 항목 함수
	function getDisplayEventTime(event) {
	
	  var displayEventTime;

	  if (event.event.allDay == false) {
	    var startTimeEventInfo = moment(event.event.start).format('HH:mm');
	    var endTimeEventInfo = moment(event.event.end).format('HH:mm');
	    displayEventTime = startTimeEventInfo + " - " + endTimeEventInfo;
	  } else {
		displayEventTime = "하루종일";
	  }
	  return displayEventTime;
	}
	
	//마우스 호버시 보여질 날짜 항목 함수
	function getDisplayEventDate(event) {
	  var displayEventDate;

	    var startTimeEventInfo = moment(event.event.start).format('YYYY/MM/DD');
	    var endTimeEventInfo = moment(event.event.end).format('YYYY/MM/DD');
	    
	    if(event.event.allDay == true){
	    	endTimeEventInfo = moment(event.event.end).subtract(1, 'days').format('YYYY/MM/DD')
	    }
	    
	    if(startTimeEventInfo== endTimeEventInfo){
	    	displayEventDate = startTimeEventInfo
	    }else{
	    	displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
	    }
	
	  return displayEventDate;
	}
	
	
	// datetimepicker
	$("#edit-start, #edit-end").bootstrapMaterialDatePicker({
	    format: 'YYYY-MM-DD HH:mm'
	});