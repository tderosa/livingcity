$('#stories').hide();
$('#intro-btn').addClass('underline');

$('#intro-btn').bind('click', function() {
	showIntro = true;
	$('#intro-btn').addClass('underline');
	$('#stories-btn').removeClass('underline');
	$('#intro').show();
	$('#stories').hide();
});

$('#stories-btn').bind('click', function() {
	showIntro = false;
	$('#intro-btn').removeClass('underline');
	$('#stories-btn').addClass('underline');
	$('#intro').hide();
	$('#stories').show();
});