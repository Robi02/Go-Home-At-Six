<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html language="ko">
<head>
	<!-- Meta -->
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Common Head -->
	<%@ include file="./head-include.jsp" %>
  <!-- Title -->
  <title>출퇴근 기록</title>
</head>
<body class="bg-secondary">
	<div class="container-fluid">
		<!-- Top Navigator -->
		<%@ include file="./top-navigator.jsp" %>
		<!-- Calendar -->
		<div class="row justify-content-center">
			<div class="col-11 pt-2 pb-2 rounded">
				<h2>
					<span id="span_year">0000</span><small>년</small> 
					<span id="span_month">00</span><small>월</small> 
					<span id="span_date">00</span><small>일</small> 
					<small id="span_day_of_week">(일)</small>
				</h2>
				<h5>
					- <span id="span_cur_time">00시 00분 00초</span>
				</h5>
			</div>
		</div>
		<!-- Check-In/Out Button -->
		<div class="row justify-content-center" id="div_check_in">
			<div class="col-11 pt-2 pb-2 rounded bg-dark">
				<button type="button" class="pt-5 pb-5 w-100 btn-lg btn-success">출근!</button>
			</div>
		</div>
		<div class="row justify-content-center d-none" id="div_check_out">
			<div class="col-11 pt-2 pb-2 rounded bg-dark">
				<button type="button" class="pt-5 pb-5 w-100 btn-lg btn-danger">퇴근!</button>
			</div>
		</div>
		<!-- Records -->
		<div class="row pt-4"></div>
		<div class="row pt-2 pb-2 bg-dark justify-content-center align-items-center bg-light border-bottom border-secondary rounded d-none" id="div_check_in_time">
			<div class="col-1"></div>
			<div class="col pt-1 bg-primary rounded">
				<b>출근시간 : </b><span id="span_check_in_time">00시 00분 00초</span>
			</div>
			<div class="col-1"></div>
		</div>
		<div class="row pt-2 pb-2 bg-dark justify-content-center align-items-center bg-light border-bottom border-secondary rounded d-none" id="div_working_time">
			<div class="col-1"></div>
			<div class="col pt-1 bg-info rounded">
				<b>근무시간 : </b><span id="span_working_time">0시간 00분 00초</span>
			</div>
			<div class="col-1"></div>
		</div>
		<div class="row pt-2 pb-2 bg-dark justify-content-center align-items-center bg-light border-bottom border-secondary rounded d-none" id="div_check_out_time">
			<div class="col-1"></div>
			<div class="col pt-1 bg-warning rounded">
				<b>퇴근시간 : </b><span id="span_check_out_time">00시 00분 00초</span>
			</div>
			<div class="col-1"></div>
		</div>
	</div>
</body>
</html>