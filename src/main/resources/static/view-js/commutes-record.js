// Global
var commutes_record_checkInTime  = 0;
var commutes_record_checkOutTime = 0;
var commutes_record_timer_on     = false;

// Page initializer
$(document).ready(function() {
	// Check user login
	// ...

	// Get last check-in commutes (has userJwt but, no last check-in commutes)
	if (!!$.cookie('userJwt') && !$.cookie('lastCheckInCommutes')) {
		updateLastCommutes();
	}
	
	updateButtonAndRecordTime();

	// Attach events
	$('#button_check_in').on('click', function() {
		// check-in button
		var reqHeader = { 'userJwt' : $.cookie('userJwt') };
		var reqBody = { 
			commuteCompanyName : $('#input_company_name').val(),
			checkInTime        : new Date().getTime(),
			checkOutTime       : 0,
			memo               : $('#textarea_memo').val()
		};
	
		GHASIX_API.apiAjaxCall('POST', GHASIX_API.apiURL.checkIn, reqHeader, reqBody, null, checkInSuccess, ajaxFail);
	});

	$('#button_check_out').on('click', function() {
		// check-out button
		commutes_record_checkOutTime = new Date().getTime();

		var reqHeader = { 'userJwt' : $.cookie('userJwt') };
		var reqBody = {
			commuteCompanyName : $('#input_company_name').val(),
			checkInTime        : null,
			checkOutTime       : commutes_record_checkOutTime,
			memo               : $('#textarea_memo').val()
		};
		var apiURL = GHASIX_API.apiURL.checkOut.format(JSON.parse($.cookie('lastCheckInCommutes')).id);
	
		GHASIX_API.apiAjaxCall('PUT', apiURL, reqHeader, reqBody, null, checkOutSuccess, ajaxFail);
	});

	// Current time and Working timer
	updateTime();
	setInterval(updateTime, 1000);
});

// Update last commutes
function updateLastCommutes() {
	var reqHeader = { userJwt : $.cookie('userJwt') };

	GHASIX_API.apiAjaxCall('GET', GHASIX_API.apiURL.selectLastCheckIn, reqHeader, null, null, function(apiResult) {
		if (!GHASIX_API.checkResultSuccess(apiResult)) {
			return;
		}

		var lastCheckInCommutes = GHASIX_API.getResultData(apiResult, 'lastCommutes');

		if (!lastCheckInCommutes) { // no last commutes data
			return;
		}

		$.cookie('lastCheckInCommutes', JSON.stringify(lastCheckInCommutes));
		updateButtonAndRecordTime();
	}, ajaxFail);
}

// Update current time
function updateTime() {
	// current time
	var curTime = new Date();

	$('#span_year').html(curTime.format('yyyy'));
	$('#span_month').html(curTime.format('MM'));
	$('#span_date').html(curTime.format('dd'));
	$('#span_day_of_week').html(curTime.format('(E)'));
	$('#span_cur_time').html(curTime.format('HH시 mm분 ss초'));

	// working timer
	if (commutes_record_timer_on == true) {
		var workingTimeMs = (curTime.getTime() - commutes_record_checkInTime);
		var hours = parseInt(workingTimeMs / 3600000);
		var mins = parseInt(workingTimeMs / 60000 % 60);
		var secs = parseInt(workingTimeMs / 1000 % 60);
		var workingTimeStr = '{0}시간 {1}분 {2}초'.format(hours, mins, secs);

		$('#div_working_time').removeClass('d-none');
		$('#span_working_time').html(workingTimeStr);
	}		
}

// Check-in success
function checkInSuccess(apiResult) {
	if (GHASIX_API.checkResultSuccess(apiResult)) {
		var insertedCommutes = JSON.stringify(GHASIX_API.getResultData(apiResult, 'insertedCommutes'));
		
		$.cookie('lastCheckInCommutes', insertedCommutes);
		alert('출근 기록 완료!');
		updateButtonAndRecordTime();
	}
	else {
		alert(apiResult.resultMsg);
	}
}

// Check-out success
function checkOutSuccess(apiResult) {
	if (GHASIX_API.checkResultSuccess(apiResult)) {
		$.removeCookie('lastCheckInCommutes');
		alert('퇴근 기록 완료!');
		updateButtonAndRecordTime();

		var checkOutTime = new Date();
	
		$('#span_check_out_time').html(checkOutTime.format('HH시 mm분 ss초'));
		$('#div_check_out_time').removeClass('d-none');
	}
	else {
		alert(apiResult.resultMsg);
	}
}

// Ajax failure
function ajaxFail() {
	alert('서버와 통신에 실패했습니다.');
}

// Update button and record time
function updateButtonAndRecordTime() {
	var lastCheckInCommutes = $.cookie('lastCheckInCommutes');

	if (!lastCheckInCommutes) {
		// show check-in btn (lastCheckInCommutes == undefined)
		commutes_record_timer_on = false;
		$('#div_check_in').removeClass('d-none');
		$('#div_check_out').addClass('d-none');
		return;
	}

	var lastCheckInCommutesObj = JSON.parse(lastCheckInCommutes);

	// Set company and memo
	$('#input_company_name').val(lastCheckInCommutesObj.commuteCompanyName);
	$('#textarea_memo').val(lastCheckInCommutesObj.memo);

	if (lastCheckInCommutesObj.checkOutTime != 0) {
		// show check-in btn (lastCheckInCommutes == undefined)
		commutes_record_timer_on = false;
		$('#div_check_in').removeClass('d-none');
		$('#div_check_out').addClass('d-none');
		return;
	}
	
	// show check-out btn (has lastCheckInCommutes data)
	$('#div_check_in').addClass('d-none');
	$('#div_check_out').removeClass('d-none');

	// start working timer and hide last check-out record
	commutes_record_timer_on = true;
	$('#div_check_out_time').addClass('d-none');

	commutes_record_checkInTime = JSON.parse(lastCheckInCommutes).checkInTime;

	if (!!commutes_record_checkInTime) {
		var checkInTime = new Date(commutes_record_checkInTime);

		$('#span_check_in_time').html(checkInTime.format('HH시 mm분 ss초'));
		$('#div_check_in_time').removeClass('d-none');
	}
	else {
		$('#div_check_in_time').addClass('d-none');
	}
}