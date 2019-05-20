<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html language="ko">
<head>
	<!-- Meta -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Common Head -->
	<%@ include file="./head-include.jsp" %>
	<!-- Page Script -->
	<script type="text/javascript" src="/view-js/main-frame.js?ver=<%=System.currentTimeMillis()%>"></script>
	<!-- Page CSS -->
	<link rel="stylesheet" href="/view-css/main-frame.css?ver=<%=System.currentTimeMillis()%>">
	<!-- Title -->
	<title>6시 칼퇴근!</title>
</head>
<body class="bg-secondary">
	<div class="container-fluid">
		<!-- Top Navigator -->
		<%@ include file="./top-navigator.jsp" %>
		<!-- Main Page -->
		<div class="row" id="div_main_page">
			<div class="col">
				<h1>메인 페이지</h1>
			</div>
		</div>
	</div>
	<div id="div_commutes_page">
		<!-- Commutes Pages -->
		<iframe class="d-none" src="" id="iframe_record_page"></iframe>
		<iframe class="d-none" src="" id="iframe_list_page"></iframe>
		<iframe class="d-none" src="" id="iframe_statistics_page"></iframe>
	</div>
</body>
</html>