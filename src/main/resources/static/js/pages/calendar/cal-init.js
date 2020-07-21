
! function($) {
    "use strict";
    
    var CalendarApp = function() {
    	 this.$body = $("body")
         this.$calendar = $('#calendar'),
         this.$event = ('#calendar-events div.calendar-events'),
         this.$categoryForm = $('#add-new-event form'),
         this.$extEvents = $('#calendar-events'),
         this.$modal = $('#my-event'),
         this.$saveCategoryBtn = $('.save-category'),
         this.$calendarObj = null
    }; 
   


    /* on drop */
    CalendarApp.prototype.onDrop = function(eventObj, date) {
            var $this = $(this);
            // retrieve the dropped element's stored Event Object
            var originalEventObject = eventObj.data('eventObject');
            var $categoryClass = eventObj.attr('data-class');
            // we need to copy it, so that multiple events don't have a reference to the same object
            var copiedEventObject = $.extend({}, originalEventObject);
            // assign it the date that was reported
            copiedEventObject.start = date;
            if ($categoryClass)
                copiedEventObject['className'] = [$categoryClass];
            // render the event on the calendar
            $this.$calendar.fullCalendar('renderEvent', copiedEventObject, true);
            // is the "remove after drop" checkbox checked?
            if ($('#drop-remove').is(':checked')) {
                // if so, remove the element from the "Draggable Events" list
                eventObj.remove();
            }
        },
        CalendarApp.prototype.enableDrag = function() {
            //init events
            $(this.$event).each(function() {
                // create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
                // it doesn't need to have a start or end
                var eventObject = {
                    title: $.trim($(this).text()) // use the element's text as the event title
                };
                // store the Event Object in the DOM element so we can get to it later
                $(this).data('eventObject', eventObject);
                // make the event draggable using jQuery UI
                $(this).draggable({
                    zIndex: 999,
                    revert: true, // will cause the event to go back to its
                    revertDuration: 0 //  original position after the drag
                });
            });
        }
        
    /* on select */
   CalendarApp.prototype.select = function (startDate, endDate, jsEvent, view) {

		    var eventModal = $('#eventModal');
		    var modalTitle = $('.modal-title');
		    var editAllDay = $('#edit-allDay');
		    var editTitle = $('#edit-title');
		    var editStart = $('#edit-start');
		    var editEnd = $('#edit-end');
		    var editType = $('#edit-type');
		    var editColor = $('#edit-color');
		    var editDesc = $('#edit-desc');

		    var addBtnContainer = $('.modalBtnContainer-addEvent');
		    var modifyBtnContainer = $('.modalBtnContainer-modifyEvent');

	    $(".fc-body").unbind('click');
	    $(".fc-body").on('click', 'td', function (e) {
	    	console.log("너니??")
	    	 	modalTitle.html('새로운 일정');
	    	    editType.val(editType).prop('selected', true);
//	    	    editTitle.val('');
//	    	    editStart.val(startDate);
//	    	    editEnd.val(endDate);
//	    	    editDesc.val('');
	    	    
	    	    addBtnContainer.show();
	    	    modifyBtnContainer.hide();
	    	    eventModal.modal('show');

	    	    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/
	    	    var eventId = 1 + Math.floor(Math.random() * 1000);
	    	    /******** 임시 RAMDON ID - 실제 DB 연동시 삭제 **********/

	    	    //새로운 일정 저장버튼 클릭
	    	    $('#save-event').unbind();
	    	    $('#save-event').on('click', function () {
	    	    	 console.log("타길..")
	    	    	 
	    	        var eventData = {
	    	            id: eventId,
	    	            title: editTitle.val(),
	    	            start: editStart.val(),
	    	            end: editEnd.val(),
	    	            description: editDesc.val(),
	    	           
	    	            backgroundColor: editColor.val(),
	    	           
	    	            allDay: false
	    	        };
	    	    	 console.log(eventData)
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
	    	            //render시 날짜표기수정
	    	            eventData.end = moment(eventData.end).add(1, 'days').format('YYYY-MM-DD');
	    	            //DB에 넣을때(선택)
//	    	            realEndDay = moment(eventData.end).format('YYYY-MM-DD');

	    	            eventData.allDay = true;
	    	        }

	    	        $("#calendar").fullCalendar('renderEvent', eventData, true);
	    	        eventModal.find('input, textarea').val('');
	    	        editAllDay.prop('checked', false);
	    	        eventModal.modal('hide');

	    	        //새로운 일정 저장
	    	        $.ajax({
	    	       	 url: "addPlan.ajax",
	    	                data:{ 
	    	               	 name:eventData.title,
	    	               	 description :eventData.description,
	    	               	 start : eventData.start,
	    	               	 end: eventData.end,
	    	               	 color :eventData.backgroundColor,
	    	               	 id : currUserId,
	    	               	 teamNo:teamNo
	    	                },
	    	               
	    	                success:function(response){
	    	                    alert("succes ajout");
	    	                  //DB연동시 중복이벤트 방지를 위한
	    	                  $('#calendar').fullCalendar('removeEvents');
	    	                  $('#calendar').fullCalendar('refetchEvents');
	    	                },error:function(){ 
	    	                    alert("erreur!!!!");
	    	                }
	    	            });

	    	    });
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
	        hours: today.hours() + 1,
	        minute: today.minutes()
	      });
	      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
	    } else {
	      startDate = moment(startDate).format('YYYY-MM-DD HH:mm');
	      endDate = moment(endDate).format('YYYY-MM-DD HH:mm');
	    }

	  }
		
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

        
    /* Initializing */
    CalendarApp.prototype.init = function() {
    	
    	
            this.enableDrag();
            /*  Initialize the calendar  */
            var date = new Date();
            var d = date.getDate();
            var m = date.getMonth();
            var y = date.getFullYear();
            var form = '';
            var today = new Date($.now());

            var defaultEvents = [{
                    title: 'Meeting #3',
                    start: new Date($.now() + 506800000),
                    className: 'bg-info'
                }, {
                    title: 'Submission #1',
                    start: today,
                    end: today,
                    className: 'bg-danger'
                }, {
                    title: 'Meetup #6',
                    start: new Date($.now() + 848000000),
                    className: 'bg-info'
                }, {
                    title: 'Seminar #4',
                    start: new Date($.now() - 1099000000),
                    end: new Date($.now() - 919000000),
                    className: 'bg-warning'
                }, {
                    title: 'Event Conf.',
                    start: new Date($.now() - 1199000000),
                    end: new Date($.now() - 1199000000),
                    className: 'bg-purple'
                }, {
                    title: 'Meeting #5',
                    start: new Date($.now() - 399000000),
                    end: new Date($.now() - 219000000),
                    className: 'bg-info'
                },
                {
                    title: 'Submission #2',
                    start: new Date($.now() + 868000000),
                    className: 'bg-danger'
                }, {
                    title: 'Seminar #5',
                    start: new Date($.now() + 348000000),
                    className: 'bg-success'
                }
            ];
        

            var $this = this;
            
            var calendar =$('#calendar').fullCalendar({
            		selectable: true,
            		eventRender: function (event, element, view) {
            		
            		    //일정에 hover시 요약
            		    element.popover({
            		      title: $('<div />', {
            		        class: 'popoverTitleCalendar',
            		        text: event.title
            		      }).css({
            		        'background': event.backgroundColor,
            		        'color': event.textColor
            		      }),
            		      content: $('<div />', {
            		          class: 'popoverInfoCalendar'
            		        }).append('<p><strong>등록자:</strong> ' + event.username + '</p>')
            		        .append('<p><strong>구분:</strong> ' + event.type + '</p>')
            		        .append('<p><strong>시간:</strong> ' + getDisplayEventDate(event) + '</p>')
            		        .append('<div class="popoverDescCalendar"><strong>설명:</strong> ' + event.description + '</div>'),
            		      delay: {
            		        show: "800",
            		        hide: "50"
            		      },
            		      trigger: 'hover',
            		      placement: 'top',
            		      html: true,
            		      container: 'body'
            		    });

            		    return event;

            		  },

            		  header: {
            			  left: 'prev,next today',
                          center: 'title',
                          right: 'month,agendaWeek,agendaDay'
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

            		  /* ****************
            		   *  일정 받아옴 
            		   * ************** */
            		  events: function (start, end, timezone, callback) {
            			 
            		    $.ajax({
            		      datatype: "get",
            		      url: "showCalendar.do",
            		      data: {
            		        // 실제 사용시, 날짜를 전달해 일정기간 데이터만 받아오기를 권장
            		      },
            		      success: function (response) {
            		    	console.log(response)
            		        callback(response);
            		      }
            		    });
            		  },

            		  eventAfterAllRender: function (view) {
            		    if (view.name == "month") {
            		      $(".fc-content").css('height', 'auto');
            		    }
            		  },

            		  //일정 리사이즈
            		  eventResize: function (event, delta, revertFunc, jsEvent, ui, view) {
            		    $('.popover.fade.top').remove();

            		    /** 리사이즈시 수정된 날짜반영
            		     * 하루를 빼야 정상적으로 반영됨. */
            		    var newDates = calDateWhenResize(event);

            		    //리사이즈한 일정 업데이트
            		    $.ajax({
            		      type: "get",
            		      url: "",
            		      data: {
            		        //id: event._id,
            		        //....
            		      },
            		      success: function (response) {
            		        alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
            		      }
            		    });

            		  },

            		  eventDragStart: function (event, jsEvent, ui, view) {
            		    draggedEventIsAllDay = event.allDay;
            		  },

            		  //일정 드래그앤드롭
            		  eventDrop: function (event, delta, revertFunc, jsEvent, ui, view) {
            		    $('.popover.fade.top').remove();

            		    //주,일 view일때 종일 <-> 시간 변경불가
            		    if (view.type === 'agendaWeek' || view.type === 'agendaDay') {
            		      if (draggedEventIsAllDay !== event.allDay) {
            		        alert('드래그앤드롭으로 종일<->시간 변경은 불가합니다.');
            		        location.reload();
            		        return false;
            		      }
            		    }

            		    // 드랍시 수정된 날짜반영
            		    var newDates = calDateWhenDragnDrop(event);

            		    //드롭한 일정 업데이트
            		    $.ajax({
            		      type: "get",
            		      url: "",
            		      data: {
            		        //...
            		      },
            		      success: function (response) {
            		        alert('수정: ' + newDates.startDate + ' ~ ' + newDates.endDate);
            		      }
            		    });

            		  },

            		  //이벤트 클릭시 수정이벤트
            		  eventClick: function (event, jsEvent, view) {
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
            		//  defaultDate: moment('2019-05'), //실제 사용시 삭제
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
            		  selectLongPressDelay: 0

            });
            
            //추가함 ㅠ
//            this.$saveCategoryBtn.on('click', function(){
//                var categoryName = $this.$categoryForm.find("input[name='category-name']").val();
//                var categoryColor = $this.$categoryForm.find("select[name='category-color']").val();
//                if (categoryName !== null && categoryName.length != 0) {
//                    $this.$extEvents.append('<div class="external-event bg-' + categoryColor + '" data-class="bg-' + categoryColor + '" style="position: relative;"><i class="mdi mdi-checkbox-blank-circle m-r-10 vertical-middle"></i>' + categoryName + '</div>')
//
//                }
//
//            });
            
        },

        //init CalendarApp
        $.CalendarApp = new CalendarApp, $.CalendarApp.Constructor = CalendarApp

}(window.jQuery),

//initializing CalendarApp
$(window).on('load', function() {

    $.CalendarApp.init()
});