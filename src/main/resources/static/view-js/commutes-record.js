// Global
var cr_userJwt             = null;
var cr_lastCheckinCommutes = null;

// Page initializer
$(document).ready(function() {
	// Init Global
	cr_userJwt             = window.parent.mf_userJwt;
	cr_lastCheckinCommutes = $.cookie('lastCheckInCommutes');

	// Get last checkin commutes
	if (!!cr_userJwt && !cr_lastCheckinCommutes) {
		// from server
		var reqHeader = { userJwt : cr_userJwt };

		GHASIX_API.apiAjaxCall('GET', GHASIX_API.apiURL.selectLastCheckIn, reqHeader, null, null, function(apiResult) {
			if (!GHASIX_API.checkResultSuccess(apiResult)) {
				return;
			}

			var lastCheckInCommutes = $('#input_last_checkin_commutes_id').val(GHASIX_API.getResultData(apiResult, 'lastCommutes'));

			if (!lastCheckInCommutes) { // no last commutes data
				return;
			}
			
			$.cookie('lastCheckInCommutes', lastCheckInCommutes);
			cr_lastCheckinCommutes = lastCheckInCommutes;
			// set timezone()
		}, ajaxFail);
	}

	// Attach events
	$('#button_check_in').on('click', function() {
		checkIn();
	});

	$('#button_check_out').on('click', function() {
		checkOut();
	});
});

// Check-in button
function checkIn() {
	var reqHeader = { 'userJwt' : cr_userJwt };
	var reqBody = { 
		commuteCompanyName : $('#input_company_name').val(),
		checkInTime        : new Date().getTime(),
		checkOutTime       : 0,
		memo               : $('#textarea_memo').val()
	};

	GHASIX_API.apiAjaxCall('POST', GHASIX_API.apiURL.checkIn, reqHeader, reqBody, null, checkInSuccess, ajaxFail);
}

// Check-in success
function checkInSuccess(apiResult) {
	if (GHASIX_API.checkResultSuccess(apiResult)) {
		alert('출근 기록 완료!');
		$('#div_check_in').addClass('d-none');
		$('#div_check_out').removeClass('d-none');
		$('#div_check_in_time').removeClass('d-none');
		$('#div_working_time').removeClass('d-none');
		$('#div_check_out_time').addClass('d-none');
	}
	else {
		alert(apiResult.resultMsg);
	}
}

// Check-out button
function checkOut() {
	var reqHeader = { 'userJwt' : cr_userJwt };
	var reqBody = {
		commuteCompanyName : $('#input_company_name').val(),
		checkInTime        : null,
		checkOutTime       : new Date().getTime(),
		memo               : $('#textarea_memo').val()
	};
	
	var lastCheckInCommutesId = cr_lastCheckinCommutes.id;
	var url = GHASIX_API.apiURL.checkOut;

	GHASIX_API.apiAjaxCall('PUT', url.format(lastCheckInCommutesId), reqHeader, reqBody, null, checkOutSuccess, ajaxFail);
}

// Check-out success
function checkOutSuccess(apiResult) {
	if (GHASIX_API.checkResultSuccess(apiResult)) {
		alert('퇴근 기록 완료!');
		$('#div_check_in').removeClass('d-none');
		$('#div_check_out').addClass('d-none');
		$('#div_check_in_time').removeClass('d-none');
		$('#div_working_time').removeClass('d-none');
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