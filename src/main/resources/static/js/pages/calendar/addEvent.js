var teamNo = $('#teamNo').val();

var eventModal = $('#eventModal');
var modalTitle = $('.modal-title');
var editAllDay = $('#customCheck2');
var editTitle = $('#edit-title');
var editStart = $('#edit-start');
var editEnd = $('#edit-end');
var editType = $('#edit-type');
var editColor = $('#edit-color');
var editDesc = $('#edit-desc');

var addBtnContainer = $('.modalBtnContainer-addEvent');
var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');


/* ****************
 *  새로운 일정 생성
 * ************** */
var newEvent = function (start, end, eventType) {

//    $("#contextMenu").hide(); //메뉴 숨김
	modalTitle.html('새로운 일정');
//    editType.val(eventType).prop('selected', true);
    
    editTitle.val('');
    editStart.val(start);
    editEnd.val(end);
    editDesc.val('');
    
    addBtnContainer.show();
    modifyBtnContainer.hide();
    eventModal.modal('show');


    //새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {
    	 console.log(currUserId)
    	 
        var eventData = {
            id: currUserId,
            name: editTitle.val(),
            start: editStart.val(),
            end: editEnd.val(),
            description: editDesc.val(),
            teamNo:teamNo,
            backgroundColor: editColor.val(),
            allDay: true
        };

        if (eventData.start > eventData.end) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (eventData.name === '') {
            alert('일정명은 필수입니다.');
            return false;
        }
        var realEndDay;
        
        console.log(eventData)
        
        if (editAllDay.is(':checked')) {
        	
            eventData.start = moment(eventData.start).format('YYYY-MM-DD');
            //render시 날짜표기수정
            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
            //DB에 넣을때(선택)
            realEndDay = moment(eventData.end).format('YYYY-MM-DD');

            eventData.allDay = true;
        }


        $("#calendar").fullCalendar('renderEvent', eventData, true);
        eventModal.find('input, textarea').val('');
        editAllDay.prop('checked', true);
        eventModal.modal('hide');
        console.log(eventData.allDay)
        //새로운 일정 저장
        $.ajax({
       	 url: "addPlan.ajax",
		         data:{ 
		           	 name : eventData.name,
		           	 description :eventData.description,
		           	 start : eventData.start,
		           	 end: eventData.end,
		           	 color :eventData.backgroundColor,
		           	 id : currUserId,
		           	 teamNo : teamNo,
		           	 allDay : eventData.allDay
		           	 
		            },
               
                success:function(response){
                	
                  alert("일정이 등록되었습니다.");
                  //DB연동시 중복이벤트 방지를 위한
                  $('#calendar').fullCalendar('removeEvents');
                  $('#calendar').fullCalendar('refetchEvents');
                },error:function(){ 
                    alert("일정등록에 실패하였습니다.");
                }
            });

    });
};