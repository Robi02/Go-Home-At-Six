// Global
var commutes_list_ary = null;

// Page initializer
$(document).ready(function() {
	if (!$.cookie('userJwt')) {
		alert('로그인이 필요합니다!');
		return;
	}

	// Init inputs
	initInput();

	// Attach events
	$('#btn_search_commutes').on('click', function() {
		// serch commutes button
		var reqHeader = { 'userJwt' : $.cookie('userJwt') };
		var reqBody = null;
		var fromYear = $('#input_from_year').val();
		var fromMonth = $('#input_from_month').val() - 1;
		var fromDate = $('#input_from_date').val();
		var toYear = $('#input_to_year').val();
		var toMonth = $('#input_to_month').val() - 1;
		var toDate = $('#input_to_date').val();

		var url = GHASIX_API.apiURL.selectByTime.format(new Date(fromYear, fromMonth, fromDate, 0, 0, 0, 0).getTime(),
														new Date(toYear, toMonth, toDate, 23, 59, 59, 999).getTime());
	
		GHASIX_API.apiAjaxCall('GET', url, reqHeader, reqBody, initList, searchSuccess, ajaxFail);
	});
});

// Check-in success
function searchSuccess(apiResult) {
	if (GHASIX_API.checkResultSuccess(apiResult)) {
		commutes_list_ary = GHASIX_API.getResultData(apiResult, 'selectedCommutesList');
	}
	else {
		alert(JSON.stringify(apiResult.resultMsg));
	}

	updateList();
}

// Ajax failure
function ajaxFail() {
	alert('서버와 통신에 실패했습니다.');
}

// Init input
function initInput() {
	var fromDate = new Date(new Date().getTime() - (3600000 * 24 * 7));
	var toDate = new Date();
	
	$('#input_from_year').val(fromDate.format('yyyy'));
	$('#input_from_month').val(fromDate.format('MM'));
	$('#input_from_date').val(fromDate.format('dd'));
	$('#input_to_year').val(toDate.format('yyyy'));
	$('#input_to_month').val(toDate.format('MM'));
	$('#input_to_date').val(toDate.format('dd'));
}

// Init commutes list
function initList() {
	commutes_list_ary = null;
	$('#div_commutes_list').empty();
}

// Update commutes list
function updateList() {
	if (!commutes_list_ary || commutes_list_ary.length == 0) {
		$('#span_commutes_list_count').html('검색 결과 없음!');
		$('#div_row_commutes_list').removeClass('d-none');
		return;
	}

	$('#div_row_commutes_list').removeClass('d-none');
	$('#span_commutes_list_count').html('총 ' + commutes_list_ary.length + '개의 결과');

	for (var i = 0; i < commutes_list_ary.length; ++i) {
		$('#div_commutes_list').append(listButtonTag(i, commutes_list_ary[i].checkInTime, commutes_list_ary[i].checkOutTime));
	}
}

// Make list button tag
function listButtonTag(idx, checkInTime, checkOutTime) {
	var checkInDate = new Date(checkInTime);
	var checkInDateStr = checkInDate.format('yyyy.MM.dd (E)');
	var checkInTimeStr = checkInDate.format('HH:mm:ss');
	var checkOutDate = !checkOutTime ? null : new Date(checkOutTime);
	var checkOutTimeStr = null;
	
	if (!!checkOutDate) {
		checkOutTimeStr = checkOutDate.format('HH:mm:ss');
	}
	
	var workingTimeMs = checkOutTime - checkInTime;
	var badgeLevel = null;
	var badgeStr = null;

	// 근무중
	if (!checkOutTime) {
		badgeLevel = 'success';
		badgeStr = '일중';		
	}
	else {
		// 8시간 5분 이내 (칼퇴)
		if (workingTimeMs <= 3600000 * 8 + 60000 * 5) {
			badgeLevel = 'primary';
			badgeStr = '칼퇴';
		}
		// 8시간 30분 이내 (양호)
		else if (workingTimeMs <= 3600000 * 8 + 60000 * 30) {
			badgeLevel = 'info';
			badgeStr = '양호';
		}
		// 9시간 00분 이내 (주의)
		else if (workingTimeMs <= 3600000 * 9) {
			badgeLevel = 'warning';
			badgeStr = '주의';
		}
		// 10시간 이내 (심각)
		else if (workingTimeMs <= 3600000 * 10) {
			badgeLevel = 'error';
			badgeStr = '심각';
		}
		// 그외 (야근)
		else {
			badgeLevel = 'dark';
			badgeStr = '야근';
		}
	}

	return ('<button type="button" \
	class="list-group-item list-group-item-action d-flex justify-content-between align-items-center" \
	id="btn_commutes_' + idx + '">\
	<span><b>' + checkInDateStr + '</b> <small>(' + checkInTimeStr + ' ~ ' + (!checkOutTimeStr ? '' : checkOutTimeStr) + ')</small></span>\
	<span class="badge badge-' + badgeLevel + ' badge-pill">' + badgeStr + '</span></button>');
}