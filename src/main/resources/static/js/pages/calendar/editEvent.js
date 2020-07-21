/* ****************
 *  일정 편집
 * ************** */
var editEvent = function (event, element, view) {
	console.log("대체뭔이벤트여")
	console.log(event)
    $('#deleteEvent').data('elementid', event._id); //클릭한 이벤트 ID

    $('.popover.fade.top').remove();
    $(element).popover("hide");

    if (event.allDay === true) {
        editAllDay.prop('checked', true);
    } else {
        editAllDay.prop('checked', false);
    }

    if (event.end === null) {
        event.end = event.start;
    }

    if (event.allDay === true && event.end !== event.start) {
        editEnd.val(moment(event.end).subtract(1, 'days').format('YYYY-MM-DD HH:mm'))
    } else {
        editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
    }

    modalTitle.html('일정 수정');
    
    editTitle.val(event.name);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editEnd.val(event.end.format('YYYY-MM-DD HH:mm'));
//    editType.val(event.type);
    editDesc.val(event.description);
    editColor.val(event.color).css('color', event.color);

    addBtnContainer.hide();
    modifyBtnContainer.show();
    eventModal.modal('show');

    //업데이트 버튼 클릭시
    $('#updateEvent').unbind();
    $('#updateEvent').on('click', function () {

        if (editStart.val() > editEnd.val()) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (editTitle.val() === '') {
            alert('일정명은 필수입니다.')
            return false;
        }

        var statusAllDay;
        var startDate;
        var endDate;
        var displayDate;

        if (editAllDay.is(':checked')) {
            statusAllDay = true;
            startDate = moment(editStart.val()).format('YYYY-MM-DD');
            endDate = moment(editEnd.val()).format('YYYY-MM-DD');
            displayDate = moment(editEnd.val()).add(1, 'days').format('YYYY-MM-DD');
        } else {
            statusAllDay = false;
            startDate = editStart
            endDate = editEnd
            displayDate = endDate;
        }

        eventModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.type = editType.val();
        event.backgroundColor = editColor.val();
        event.description = editDesc.val();

        $("#calendar").fullCalendar('updateEvent', event);

        //일정 업데이트
        $.ajax({
          	 url: "updatePlan.ajax",
   		         data:{ 
   		           	 name : event.title,
   		           	 description :event.description,
   		           	 start : event.start,
   		           	 end: event.end,
   		           	 color :event.backgroundColor,
   		           	 id : currUserId,
   		           	 teamNo : teamNo,
   		           	 allDay : event.allDay,
   		           	 no : event.no
   		           	 
   		            },
                  
                   success:function(response){
                   	
                     alert("일정이 수정되었습니다.");
                     //DB연동시 중복이벤트 방지를 위한
                     $('#calendar').fullCalendar('removeEvents');
                     $('#calendar').fullCalendar('refetchEvents');
                   },error:function(){ 
                       alert("일정수정에 실패하였습니다.");
                   }
               });

    });
 
};

//삭제버튼
$('#deleteEvent').on('click', function () {
    
    $('#deleteEvent').unbind();
    $("#calendar").fullCalendar('removeEvents', $(this).data('elementid'));
    eventModal.modal('hide');
    
    //삭제시
    $.ajax({
        url: "deletePlan.ajax",
        data: {
        	 no : event.no
        },
        success: function (response) {
            alert('일정이 삭제되었습니다.');
        },error:function(){ 
            alert("일정삭제에 실패하였습니다.");
        }
    });

});

