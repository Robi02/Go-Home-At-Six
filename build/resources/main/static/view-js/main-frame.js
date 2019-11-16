// Global
var mf_userJwt           = null;
var mf_apiDomain         = null;
var mf_mainPageURL		 = null;
var mf_recordPageURL     = null;
var mf_listPageURL       = null;
var mf_statisticsPageURL = null;
var mf_loginPageURL		 = null;

// Page initializer
$(document).ready(function() {
	// Init Global
	mf_userJwt           = $.cookie('userJwt');
	mf_apiDomain         = 'http://localhost:40001';
	mf_mainPageURL		 = mf_apiDomain + '/main';
	mf_recordPageURL     = mf_apiDomain + '/record';
	mf_listPageURL       = mf_apiDomain + '/list';
	mf_statisticsPageURL = mf_apiDomain + '/statistics';
	mf_loginPageURL		 = 'http://localhost:40000/main?audience=ghasix&afterIssueParam=' + mf_mainPageURL; // auth-servers

	// Update userJwt
	if (!mf_userJwt) { // fail to find userJwt from cookie
		if (!!(mf_userJwt = getUrlParameter('userJwt'))) {
			// Need login ...
		}
		else {
			if (!!getUrlParameter('keepLoggedIn')) { // 15day
				$.cookie('userJwt', mf_userJwt, { expires: 15, path: '/' });
			}
			else {
				$.cookie('userJwt', mf_userJwt, { expires: 1, path: '/' }); // 1day
			}
		}
	}

	// Update Navi UI
	updateNaviUI();

	// Attach navi button events
	$('#button_navi_join').on('click', function(event) {
		// modal
		location.reload();
	});
	$('#button_navi_login').on('click', function(event) {
		// modal
		location.href = mf_loginPageURL;
	});
	$('#button_navi_logout').on('click', function(event) {
		$.removeCookie('userJwt');
		$.removeCookie('lastCheckInCommutes');
		location.replace(mf_mainPageURL);
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