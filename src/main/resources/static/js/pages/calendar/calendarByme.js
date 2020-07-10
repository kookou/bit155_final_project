	
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
	
	var calendarData = $("#calendarData").serializeObject();
		console.log(calendarData);
	$.ajax({
		data: JSON.stringify(calendarData),
		url: "addPlan.ajax",
		type: 'POST',
		dataType: "json",
		contentType: "application/json; charset=UTF-8",
		success: function(result){
			//$('#centermodal').modal('show');
			//opener.parent.location.reload();
			console.log(result);
			window.close();
			//$('#centermodal').modal("hide");
		},
		error: function(request,status,error){
			console.log("실패"+request.status+request.responseText);
			console.log(error);
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
















