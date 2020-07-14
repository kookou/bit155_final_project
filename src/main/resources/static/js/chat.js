$("#fixedBtn").popover({
/*  title: "Notifiche",*/
  html: true,
  placement: "bottom",
  content: $(".alert_list").html()
});

$(".turn_off_alert").live("click", function (event) {
  var alert = $(this).parent();
  var alert_id = alert.data("alert_id");
  alert.hide("fast");
});