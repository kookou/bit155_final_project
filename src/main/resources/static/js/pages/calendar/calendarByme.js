		
//serializeObject함수
$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};

$('#plan-add').on('click', function(){
	var calendarData = JSON.stringify($('#calendarData').serializeObject());
	$.ajax({
		data: calendarData,
		url: "addPlan.do",
		type: 'POST',
		dataType: "json",
		contentType: "application/json; charset=UTF-8",
		success: function(result){
			$('#centermodal').modal('show');
			opener.parent.location.reload();
			console.log("결과는"+result);
			window.close();
		}
	})
	
});

! function($) {
	"use strict";
	//alert("엥");
/*		 
		 CalendarApp.prototype.onSelect = function (start, end, allDay) {
		 
		 
			 
		 },
		 CalendarApp.prototype.init = function() {
		 
		 
		 
		 },
	
	 $.CalendarApp = new CalendarApp, $.CalendarApp.Constructor = CalendarApp
	*/
}(window.jQuery),

//initializing CalendarApp
$(window).on('load', function() {
	//alert("허거걱");
    $.CalendarApp.init()
});
















