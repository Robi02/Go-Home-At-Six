// Global
var mf_userJwt           = null;
var mf_apiDomain         = null;
var mf_recordPageURL     = null;
var mf_listPageURL       = null;
var mf_statisticsPageURL = null;

// Page initializer
$(document).ready(function() {
	// Init Global
	mf_userJwt           = $.cookie('userJwt');
	mf_apiDomain         = 'http://localhost:8080';
	mf_recordPageURL     = mf_apiDomain + '/record';
	mf_listPageURL       = mf_apiDomain + '/list';
	mf_statisticsPageURL = mf_apiDomain + '/statistics';

	// Update Navi UI
	updateNaviUI();

	// Attach navi button events
	$('#button_navi_join').on('click', function(event) {
		// modal
		location.reload();
	});
	$('#button_navi_login').on('click', function(event) {
		// modal
		// test
		mf_userJwt = $.cookie('userJwt', '3JrIyoEO1rC4CPv5bMBjhkonx+DTHObbNTjEu3aHR6p9VJ0jFrDk7UzoJdQ4_28_t56PuWl+we5I2QyMFKkmWlBKG4PbZp1OvYPRZmSMeWf5AhptywJwqQ0eDNbCL7CplShxDWqe8QVBlexy6_Ki6_gTuMpnq49MKxqd3MVvOVxPnITsLJ1PTG4nXtS6Er4h3ALcbu+BSD+HeBrVfjywhErk9mu8Ny8d3YvEHg5BN42Obs_fYti0o+ejYssXikwS8djbTBV+ET2RGZIt6hubvpqfZJ0nZDxN8Qs4FZUpPA8-');
		// test
		location.reload();
	});
	$('#button_navi_logout').on('click', function(event) {
		$.removeCookie('userJwt');
		$.removeCookie('lastCheckInCommutes');
		location.reload();
	});

	$('#div_navi_radio_btn .btn').on('click', function(event) {
		var btnValue = $(this).find('input').val();
		var iframeId = null;
		var iframeUrl = null;

		if (btnValue == 'record') {
			iframeId = 'iframe_record_page';
			iframeUrl = mf_recordPageURL;
		}
		else if (btnValue == 'list') {
			iframeId = 'iframe_list_page';
			iframeUrl = mf_listPageURL;
		}
		else if (btnValue == 'statistics') {
			iframeId = 'iframe_statistics_page';
			iframeUrl = mf_statisticsPageURL;
		}

		hideIframeAll();
		setIframeSrc(iframeId, iframeUrl);
		showIframe(iframeId);
	});

	// Initial iframe page
	setIframeSrc('iframe_record_page', mf_recordPageURL);
	showIframe('iframe_record_page');
});

// Update navi UI
function updateNaviUI() {
	// Show navi buttons
	if (!mf_userJwt) { // need login
		$('#div_main_page').removeClass('d-none');
		$('#button_navi_join').removeClass('d-none');
		$('#button_navi_login').removeClass('d-none');
		$('#div_navi').addClass('d-none');
		$('#button_navi_logout').addClass('d-none');
		$('#div_commutes_page').addClass('d-none');
	}
	else { // logined
		$('#div_main_page').addClass('d-none');
		$('#button_navi_join').addClass('d-none');
		$('#button_navi_login').addClass('d-none');
		$('#div_navi').removeClass('d-none');
		$('#button_navi_logout').removeClass('d-none');
		$('#div_commutes_page').removeClass('d-none');
	}
}

// Custom <iframe> function
function setIframeSrc(iframeId, srcUrl) {
	iframeId = '#' + iframeId;
	var iframeTag = $(iframeId);
	iframeTag.attr('src', srcUrl);
}

function showIframe(iframeId) {
	iframeId = '#' + iframeId;
	$(iframeId).removeClass('d-none');
}

function hideIframe(iframeId) {
	iframeId = '#' + iframeId;
	$(iframeId).addClass('d-none');
}

function hideIframeAll() {
	$('#iframe_record_page').addClass('d-none');
	$('#iframe_list_page').addClass('d-none');
	$('#iframe_statistics_page').addClass('d-none');
}