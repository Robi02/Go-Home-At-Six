// Page initializer
$(document).ready(function() {
	// Get last check-in commutes (has userJwt but, no last check-in commutes)
	if (!!$.cookie('userJwt') && !$.cookie('lastCheckInCommutes')) {
		// from server
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
		var reqHeader = { 'userJwt' : $.cookie('userJwt') };
		var reqBody = {
			commuteCompanyName : $('#input_company_name').val(),
			checkInTime        : null,
			checkOutTime       : new Date().getTime(),
			memo               : $('#textarea_memo').val()
		};
		var apiURL = GHASIX_API.apiURL.checkOut.format(JSON.parse($.cookie('lastCheckInCommutes')).id);
	
		GHASIX_API.apiAjaxCall('PUT', apiURL, reqHeader, reqBody, null, checkOutSuccess, ajaxFail);
	});
});

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
	// 이제 파싱하자! [@]
	// 왜 새로운 퇴근기록 정상 업뎃후에 ui표시가 이상한지도 확인 [@]

	if (!lastCheckInCommutes) {
		// show check-out
		$('#div_check_in').removeClass('d-none');
		$('#div_check_out').addClass('d-none');
		$('#div_check_in_time').removeClass('d-none');
		$('#div_working_time').removeClass('d-none');
		$('#div_check_out_time').removeClass('d-none');
	}
	else {
		// show check-in
		$('#div_check_in').addClass('d-none');
		$('#div_check_out').removeClass('d-none');
		$('#div_check_in_time').removeClass('d-none');
		$('#div_working_time').removeClass('d-none');
		$('#div_check_out_time').addClass('d-none');
	}
}