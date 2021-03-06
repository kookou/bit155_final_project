
var draggedEventIsAllDay;
var activeInactiveWeekends = true;

//마우스 호버시 보여질 시간 항목 함수
function getDisplayEventDate(event) {
  var displayEventDate;

  if (event.allDay == false) {
    var startTimeEventInfo = moment(event.start).format('HH:mm');
    var endTimeEventInfo = moment(event.end).format('HH:mm');
    displayEventDate = startTimeEventInfo + " - " + endTimeEventInfo;
  } else {
    displayEventDate = "하루종일";
  }
  return displayEventDate;
}

function filtering(event) {
	  var show_username = true;
	  var show_type = true;

	  var username = $('input:checkbox.filter:checked').map(function () {
	    return $(this).val();
	  }).get();
	  var types = $('#type_filter').val();

	  show_username = username.indexOf(event.username) >= 0;

	  if (types && types.length > 0) {
	    if (types[0] == "all") {
	      show_type = true;
	    } else {
	      show_type = types.indexOf(event.type) >= 0;
	    }
	  }

	  return show_username && show_type;
}

//캘린더 리사이즈 될때 새로운 날짜 불러오는 함수
function calDateWhenResize(event) {
//	console.log("calDateWhenResize");	  
//	console.log(event);

  var newDates = {
    startDate: '',
    endDate: ''
  };

  if (event.allDay) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  } else {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }
  return newDates;
}

//캘린더 드래그앤 드랍시 새로운 날짜 불러오는 함수
function calDateWhenDragnDrop(event) {

  // 드랍시 수정된 날짜반영
  var newDates = {
    startDate: '',
    endDate: ''
  }

  // 날짜 & 시간이 모두 같은 경우
  if(!event.end) {
    event.end = event.start;
  }

  // all day
  if (event.allDay && event.end !== null) {
    console.log('1111')
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }

  // all day가 아님
  else if (!event.allDay) {
    newDates.startDate = moment(event.start._d).format('YYYY-MM-DD HH:mm');
    newDates.endDate = moment(event.end._d).format('YYYY-MM-DD HH:mm');
  }

  return newDates;
}

//캘린더 그리기
var calendar = $('#calendar').fullCalendar({
	
 //이벤트가 랜더링 될떄의 옵션?
  eventRender: function (event, element, view) {

    // 일정에 hover시 요약
    element.popover({
      title: $('<div />', {
        class: 'popoverTitleCalendar',
        text: event.title
      }).css({
        'background': event.color,
        'color' : '#ffffff'
      }),
      content: $('<div />', {
          class: 'popoverInfoCalendar'
        }).append('<p><strong>등록자 : </strong> ' + event.id + '</p>')
        .append('<p><strong>일정 시간 : </strong> ' + getDisplayEventDate(event) + '</p>')
        .append('<div class="popoverDescCalendar"><strong>일정 설명 : </strong> ' + event.description + '</div>'),
      delay: {
        show: "800",
        hide: "50"
      },
      
      trigger: 'hover',
      placement: 'top',
      html: true,
      container: 'body'
    });
    return true;
  },

  // 주말 숨기기 & 보이기 버튼
  customButtons: {
    viewWeekends: {
      text: '주말',
      click: function () {
        activeInactiveWeekends ? activeInactiveWeekends = false : activeInactiveWeekends = true;
        $('#calendar').fullCalendar('option', {
          weekends: activeInactiveWeekends
        });
      }
    }
  },
  
  
  //헤더에 보여질 옵션
  header: {
    left: 'today, prevYear, nextYear, viewWeekends',
    center: 'prev, title, next',
    right: 'month,agendaWeek,agendaDay,listWeek'
  },
  views: {
    month: {
      columnFormat: 'dddd'
    },
    agendaWeek: {
      columnFormat: 'M/D ddd',
      titleFormat: 'YYYY년 M월 D일',
      eventLimit: false
    },
    agendaDay: {
      columnFormat: 'dddd',
      eventLimit: false
    },
    listWeek: {
      columnFormat: ''
    }
  },

  /***************************************************************************
  * 일정 받아옴 ***************/
  events: function (start, end, timezone, callback) {
	  
    $.ajax({
    	  url: "showCalendar.ajax",
      data: {
       teamNo :teamNo
      },
      success: function (response) {
        var fixedDate = response.map(function (array) {
        	//DB 에서 불러오는 allday 값이 문자열이라 불리언 으로 바꿔줌
        	if(array.allDay == "true"){
        		array.allDay = true;
        	}
          return array;
        })
        console.log("잘 받아오나?")
        callback(fixedDate);
      }
    });
  },
  
  //모든 이벤트 랜더링이 완료 되면 실행 
//  eventAfterAllRender: function (view) {
//    if (view.name == "month") {
//      $(".fc-content").css('height', 'auto');
//    }
//  },

  
  
  // 일정 리사이즈
  eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
	  console.log(event)
	$(".fc-body").unbind('click');
    $('.popover').remove();

    /**
	 * 리사이즈시 수정된 날짜반영 하루를 빼야 정상적으로 반영됨.
	 */
    var newDates = calDateWhenResize(event);

    // 리사이즈한 일정 업데이트
 $.ajax({
        
        url: "updatePlanDrag.ajax",
        data: {
          no : event.no,
          start : newDates.startDate,
          end : newDates.endDate
        },
        success: function (response) {
          alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
        }
      });

  },

  eventDragStart: function (event, jsEvent, ui, view) {
    draggedEventIsAllDay = event.allDay;
  },

  // 일정 드래그앤드롭
  eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
    $('.popover.fade.top').remove();

    // 주,일 view일때 종일 <-> 시간 변경불가
    if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
      if (draggedEventIsAllDay !== event.allDay) {
        alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
        location.reload();
        return false;
      }
    }
    // 드랍시 수정된 날짜반영
    var newDates = calDateWhenDragnDrop(event);

    // 드롭한 일정 업데이트
    $.ajax({
        url: "updatePlanDrag.ajax",
        data: {
          no : event.no,
          start : newDates.startDate,
          end : newDates.endDate
        },
        success: function (response) {
          alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
        }
      });


  },

  select: function (startDate, endDate, jsEvent, view) {
	
    $(".fc-body").unbind();
    $(".fc-body").on('click', 'td', function (e) {
    	newEvent(startDate, endDate)
    });

    var today = moment();

    if (view.name == "month") {
      startDate.set({
        hours: today.hours(),
        minute: today.minutes()
      });
      startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
      endDate = moment(endDate).subtract(1, 'days');

      endDate.set({
        hours: today.hours() +1,
        minute: today.minutes()
      });
      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
    } else {
      startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
    }



  },

  // 이벤트 클릭시 수정이벤트
  eventClick: function (event, jsEvent, view) {
	  console.log("이벤트 수정")
	  console.log(event)
	   $(".fc-body").unbind('click');
       editEvent(event);
  },

  locale: 'ko',
  timezone: "local",
  nextDayThreshold: "09:00:00",
  allDaySlot: true,
  displayEventTime: true,
  displayEventEnd: true,
  firstDay: 0, //월요일이 먼저 오게 하려면 1
  weekNumbers: false,
  selectable: true,
  weekNumberCalculation: "ISO",
  eventLimit: true,
  views: {
    month: {
      eventLimit: 12
    }
  },
  eventLimitClick: 'week', //popover
  navLinks: true,
  timeFormat: 'HH:mm',
  defaultTimedEventDuration: '01:00:00',
  editable: true,
  minTime: '00:00:00',
  maxTime: '24:00:00',
  slotLabelFormat: 'HH:mm',
  weekends: true,
  nowIndicator: true,
  dayPopoverFormat: 'MM/DD dddd',
  longPressDelay: 0,
  eventLongPressDelay: 0,
  dayMaxEventRows: true,
  selectLongPressDelay: 0
 
});



var addModal = $('#eventModal');
var editModal = $('#eventModal');

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


/*******************************************************************************
 * 새로운 일정 생성 **************
 */
var newEvent = function (start, end, eventType) {

// $("#contextMenu").hide(); //메뉴 숨김

    modalTitle.html('새로운 일정');
// editType.val(eventType).prop('selected', true);
// console.log("이벤트타입")
// console.log(eventType)
    editTitle.val('');
    editStart.val(start);
    editEnd.val(end);
    editDesc.val('');
    editColor.val('#D25565');
    editAllDay.val('');
    
    addBtnContainer.show();
    modifyBtnContainer.hide();
    addModal.modal('show');

    // 새로운 일정 저장버튼 클릭
    $('#save-event').unbind();
    $('#save-event').on('click', function () {

    	  var eventData = {
    	            id: currUserId,
    	            title: editTitle.val(),
    	            start: editStart.val(),
    	            end: editEnd.val(),
    	            description: editDesc.val(),
    	            teamNo:teamNo,
    	            backgroundColor: editColor.val(),
    	            allDay: false,
    	            textColor: '#ffffff'
    	        };

        if (eventData.start > eventData.end) {
            alert('끝나는 날짜가 앞설 수 없습니다.');
            return false;
        }

        if (eventData.title === '') {
            alert('일정명은 필수입니다.');
            return false;
        }

        var realEndDay;

        if (editAllDay.is(':checked')) {
            eventData.start = moment(eventData.start).format('YYYY-MM-DD');
            // render시 날짜표기수정
            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
            // DB에 넣을때(선택)
            realEndDay = eventData.end
            eventData.allDay = true;
        }else{
        	realEndDay = eventData.end
        }
        
        
// $("#calendar").fullCalendar('renderEvent', eventData, true);
        
//        console.log("eventData");
//        console.log(eventData);
        
        addModal.find('input, textarea').val('');
        editAllDay.prop('checked', false);
        addModal.modal('hide');
      

        // 새로운 일정 저장
        $.ajax({
          	 url: "addPlan.ajax",
   		         data:{ 
   		        	 title : eventData.title,
   		           	 description :eventData.description,
   		           	 start : eventData.start,
   		           	 end: realEndDay,
   		           	 color :eventData.backgroundColor,
   		           	 id : currUserId,
   		           	 teamNo : teamNo,
   		           	 allDay : eventData.allDay
   		           	 
   		            },
                   
                   success:function(response){
                     alert("일정이 등록되었습니다.");
                     // DB연동시 중복이벤트 방지를 위한
                     $('#calendar').fullCalendar('removeEvents');
                     $('#calendar').fullCalendar('refetchEvents');
                     
                   },error:function(){ 
                       alert("일정등록에 실패하였습니다.");
                   }
               });
        
        
    });
};
$('#eventModal').on('hide.bs.modal', function () {
	console.log("와아아앙")
    $('#calendar').fullCalendar('refetchEvents');
})

/*******************************************************************************
 * 일정 편집 **************
 */
var editEvent = function (event, element, view) {
	
	
	
//    $('#deleteEvent').data('elementid', event._id); // 클릭한 이벤트 ID

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
    editTitle.val(event.title);
    editStart.val(event.start.format('YYYY-MM-DD HH:mm'));
    editDesc.val(event.description);
    editColor.val(event.color).css('color', event.color);

    addBtnContainer.hide();
    modifyBtnContainer.show();                     
    editModal.modal('show');

    // 업데이트 버튼 클릭시
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
            startDate = editStart.val();
            endDate = editEnd.val();
            displayDate = endDate;
        }

        editModal.modal('hide');

        event.allDay = statusAllDay;
        event.title = editTitle.val();
        event.start = startDate;
        event.end = displayDate;
        event.backgroundColor = editColor.val();
        event.description = editDesc.val();

 $("#calendar").fullCalendar('updateEvent', event);
 
//        console.log("updateEvent")
//        console.log(event)
        // 일정 업데이트
        $.ajax({
         	 url: "updatePlan.ajax",
  		         data:{ 
  		        	title : event.title,
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
                    // DB연동시 중복이벤트 방지를 위한
                    $('#calendar').fullCalendar('removeEvents');
                    $('#calendar').fullCalendar('refetchEvents');
                  },error:function(){ 
                      alert("일정수정에 실패하였습니다.");
                  }
              });
    });
    
    
   
  //삭제버튼
  $('#deleteEvent').unbind();
  $('#deleteEvent').on('click', function () {

      $("#calendar").fullCalendar('removeEvents', $(this).data('elementid'));
     
      var deleteevent = $(this)
//       console.log(deleteevent)
      
      editModal.modal('hide');
      
      // 삭제시
      $.ajax({
          url: "deletePlan.ajax",
          data: {
          	 no : event.no
          },
          success: function (response) {
              alert('일정이 삭제되었습니다.');
              $('#calendar').fullCalendar('removeEvents');
              $('#calendar').fullCalendar('refetchEvents');
          },error:function(){ 
              alert("일정삭제에 실패하였습니다.");
          }
      });

  });


};






// SELECT 색 변경
$('#edit-color').change(function () {
    $(this).css('color', $(this).val());
});

// 필터
// $('.filter').on('change', function () {
// $('#calendar').fullCalendar('rerenderEvents');
// });

//$("#type_filter").select2({
//    placeholder: "선택..",
//    allowClear: true
//});

// datetimepicker
$("#edit-start, #edit-end").bootstrapMaterialDatePicker({
    format: 'YYYY-MM-DD HH:mm'
});