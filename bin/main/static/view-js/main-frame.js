// Global
var mf_userJwt					 = $.cookie('userJwt');
var mf_apiDomain         = 'http://localhost:8080';
var mf_recordPageURL     = mf_apiDomain + '/record';
var mf_listPageURL       = mf_apiDomain + '/list';
var mf_statisticsPageURL = mf_apiDomain + '/statistics';

// Page initializer
$(document).ready(function() {
	// Update Navi UI
	updateNaviUI();

	// Attach navi button events
	$('#button_navi_join').on('click', function(event) {
		// modal
		updateNaviUI();
	});
	$('#button_navi_login').on('click', function(event) {
		// modal
		// test
		mf_userJwt = $.cookie('userJwt', '78SnY6OqbInOmw38sUWa_gsk4lFGnd8BUCqHFrZ_Na1687+EqPvphzBr_eKpksZR0fEjNGpP4no4TOtvmleex2y50COSbJzKchKVPQBP51g4hXh8r4eDna4FkqXyttfQKcjfcYDk5imv5SlWmwomBrrjuuvW5wecGtVfysQI2eNzZTZdHF2gJ3QRNdCuaA5Hu8aeaoBaxSDRkbIU51IQrG9HR_ikVlEMzB3bomcDx8cWwJRXF+Rr7uNRRijvXMWiMg0b1YsGcs8H95kRSz3QvsEuBVx2RS6LHCjcZOkLXZM-');
		// test
		updateNaviUI();
	});
	$('#button_navi_logout').on('click', function(event) {
		$.removeCookie("userJwt");
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
	if (iframeTag.attr('src') != srcUrl) iframeTag.attr('src', srcUrl);
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