var win;
$(function() {
    $('#_login').click(function() {
		$('#objForm').submit();
			});
	win = $('#win').window({
		draggable : false,
		minimizable : false,
		maximizable : false,
		resizable : false,
		collapsible : false,
		left : 460,
		top : 180,
		width:360,
		height:235
	});	
	win.window('open');
});

