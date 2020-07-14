//$("#fixedBtn").popover({
///*  title: "Notifiche",*/
//  html: true,
//  placement: "bottom",
//  content: $(".alert_list").html()
//});
//
//$(".turn_off_alert").live("click", function (event) {
//  var alert = $(this).parent();
//  var alert_id = alert.data("alert_id");
//  alert.hide("fast");
//});

(function ($, window, document) {
    // jQuery '$' is now locally scoped.
    // Listen for the jQuery 'ready' event on the document to begin 
    // execution of functions that should wait for ready.
    $(function () {
        // Now, DOM is ready and we can perform DOM functions.
        $('.btn').popover({
            trigger: "manual",
            html: true,
            content: function(){
                return $(".alert_list").html();
            }
        });
        
        $('.btn').on({
            "shown.bs.popover": function(){
                var input = $(".popover input.link-text");
                input.focus();
            },
            "hide.bs.popover": function(){
                $(this).blur();    
            },
            "click": function(){
                $(this).popover("toggle");    
            }
        });
        
       
    });

    // Other code goes here.
}(window.jQuery, window, document));