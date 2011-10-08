
$("a").click(function () {
	var section = $.url(document.location).attr('anchor');
	section = (section === "" ? "users" : section);
	$("#" + section).hide();
	$(event.target.hash).show();
});

$(document).ready(function () {
	var section = $.url(document.location).attr('anchor');
	section = (section === "" ? "users" : section);
	$("#" + section).show();
});
