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
	$('#div_commutes_list').empty();

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
	var checkInTimeStr = checkInDate.format('HH:mm');
	var checkOutDate = !checkOutTime ? null : new Date(checkOutTime);
	var checkOutTimeStr = null;
	
	if (!!checkOutDate) {
		checkOutTimeStr = checkOutDate.format('HH:mm');
	}
	
	var workingTimeMs = checkOutTime - checkInTime;
	var workingTimeHours = parseInt(workingTimeMs / 3600000);
	var workingTimeMins = parseInt(workingTimeMs / 60000 % 60);
	var workingTimeStr = (workingTimeHours + (workingTimeMins / 60.0)).toFixed(1) + 'h';
	var badgeLevel = null;
	var badgeStr = null;

	// 근무중
	if (!checkOutTime) {
		badgeLevel = 'success';
		badgeStr = '일중';		
	}
	else {
		// 8시간 15분 이내 (칼퇴)
		if (workingTimeMs <= 3600000 * 8 + 60000 * 15) {
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
		// 10시간 이상 (심각)
		else {
			badgeLevel = 'danger';
			badgeStr = '심각';
		}
	}

	return ('<button type="button" onclick="listButtonClick(' + idx + ')" data-toggle="modal" data-target="#exampleModalCenter"\
	class="list-group-item list-group-item-action d-flex justify-content-between align-items-center" \
	id="btn_commutes_' + idx + '">\
	<span><b>' + checkInDateStr + '</b> <small>(' + checkInTimeStr + '~' + (!checkOutTimeStr ? '' : checkOutTimeStr + ' / ' + workingTimeStr) + ')</small></span>\
	<span class="badge badge-' + badgeLevel + ' badge-pill">' + badgeStr + '</span></button>');
}

function listButtonClick(idx) {
	var commutes           = commutes_list_ary[idx];
	var id                 = commutes.id;
	var checkInTime        = commutes.checkInTime;
	var checkOutTime       = commutes.checkOutTime;
	var commuteCompanyName = commutes.commuteCompanyName;
	var memo               = commutes.memo;

	$('#input_modal_commutes_id').val(id);

	if (!!checkInTime) {
		var checkInDate = new Date(checkInTime);
		$('#h5_center_modal_title').html(checkInDate.format('yyyy.MM.dd (E)'));
		$('#modal_input_check_in_year').val(checkInDate.format('yyyy'));
		$('#modal_input_check_in_month').val(checkInDate.format('MM'));
		$('#modal_input_check_in_date').val(checkInDate.format('dd'));
		$('#modal_input_check_in_hour').val(checkInDate.format('HH'));
		$('#modal_input_check_in_minute').val(checkInDate.format('mm'));
		$('#modal_input_check_in_second').val(checkInDate.format('ss'));
	}

	var checkOutDate = null;

	if (!checkOutTime) {
		checkOutDate = new Date();
	}
	else {
		checkOutDate = new Date(checkOutTime);
	}

	$('#modal_input_check_out_year').val(checkOutDate.format('yyyy'));
	$('#modal_input_check_out_month').val(checkOutDate.format('MM'));
	$('#modal_input_check_out_date').val(checkOutDate.format('dd'));
	$('#modal_input_check_out_hour').val(checkOutDate.format('HH'));
	$('#modal_input_check_out_minute').val(checkOutDate.format('mm'));
	$('#modal_input_check_out_second').val(checkOutDate.format('ss'));
	$('#input_commutes_modal_company_name').val(commuteCompanyName);
	$('#input_commutes_modal_memo').val(memo);
}

// Commutes modal function (modify)
function modifyCommutesButtonClick() {
	if (!confirm('정말 수정할까요?')) {
		return; // cancel
	}
	
	var id = $('#input_modal_commutes_id').val();
	var apiURL = GHASIX_API.apiURL.modifyById.format(id);
	var reqHeader = { userJwt : $.cookie('userJwt') };

	var checkInDate = new Date($('#modal_input_check_in_year').val(),
							   $('#modal_input_check_in_month').val() - 1,
							   $('#modal_input_check_in_date').val(),
							   $('#modal_input_check_in_hour').val(),
							   $('#modal_input_check_in_minute').val(),
							   $('#modal_input_check_in_second').val(), 0);
	var checkOutDate = new Date($('#modal_input_check_out_year').val(),
							    $('#modal_input_check_out_month').val() - 1,
							    $('#modal_input_check_out_date').val(),
							    $('#modal_input_check_out_hour').val(),
							    $('#modal_input_check_out_minute').val(),
								$('#modal_input_check_out_second').val(), 0);
	var checkInTimeStr = checkInDate.getTime();
	var checkOutTimeStr = checkOutDate.getTime();

	var reqBody = {
		commuteCompanyName : $('#input_commutes_modal_company_name').val(),
		checkInTime        : checkInTimeStr,
		checkOutTime       : checkOutTimeStr,
		memo               : $('#input_commutes_modal_memo').val()
	};
	
	GHASIX_API.apiAjaxCall('PUT', apiURL, reqHeader, reqBody, null, modifySuccess, ajaxFail);
}

function modifySuccess(apiResult) {
	if (!GHASIX_API.checkResultSuccess(apiResult)) {
		alert(apiResult.resultMsg);
		return;
	}

	$.removeCookie('lastCheckInCommutes');

	var id = $('#input_modal_commutes_id').val();

	if (!!commutes_list_ary) {
		for (var i = 0; i < commutes_list_ary.length; ++i) {
			if (commutes_list_ary[i].id == id) {
				commutes_list_ary[i] = GHASIX_API.getResultData(apiResult, 'updatedCommutes');
				break;
			}
		}
	}

	$('#input_modal_commutes_id').val('');
	updateList();
}

// Commutes modal function (delete)
function deleteCommutesButtonClick() {
	if (!confirm('정말 삭제할까요?')) {
		return; // cancel
	}

	var id = $('#input_modal_commutes_id').val();
	var apiURL = GHASIX_API.apiURL.deleteById.format(id);
	var reqHeader = { userJwt : $.cookie('userJwt') };
	
	GHASIX_API.apiAjaxCall('DELETE', apiURL, reqHeader, null, null, deleteSuccess, ajaxFail);
}

function deleteSuccess(apiResult) {
	if (!GHASIX_API.checkResultSuccess(apiResult)) {
		alert(apiResult.resultMsg);
		return;
	}

	$.removeCookie('lastCheckInCommutes');

	var id = $('#input_modal_commutes_id').val();

	if (!!commutes_list_ary) {
		for (var i = 0; i < commutes_list_ary.length; ++i) {
			if (commutes_list_ary[i].id == id) {
				commutes_list_ary.splice(i, 1);
				break;
			}
		}
	}

	$('#input_modal_commutes_id').val('');
	updateList();
}