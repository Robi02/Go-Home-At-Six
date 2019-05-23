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
	<script type="text/javascript" src="/view-js/commutes-list.js?ver=<%=System.currentTimeMillis()%>"></script>
  <!-- Title -->
  <title>출퇴근 목록</title>
</head>
<body class="bg-secondary">
	<div class="container-fluid">
		<div class="row justify-content-center align-items-center no-gutters">
			<div class="col-4 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_from_year">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">년</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col-3 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_from_month">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">월</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col-3 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_from_date">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">일</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col align-self-center text-center">
				<b>부터</b>
			</div>
		</div>
		<div class="row justify-content-center align-items-center no-gutters">
			<div class="col-4 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_to_year">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">년</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col-3 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_to_month">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">월</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col-3 align-self-center">
				<div class="input-group mb-3">
					<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="input_to_date">
					<div class="input-group-prepend">
						<span class="input-group-text" id="inputGroup-sizing-default">일</span>
					</div>
				</div>
			</div><pre> </pre>
			<div class="col align-self-center text-center">
				<b>까지</b>
			</div>
		</div>
		<div class="row justify-content-center align-items-center">
			<div class="col-12">
				<button type="button" class="btn btn-primary btn-lg btn-block" id="btn_search_commutes">검색</button>
			</div>
		</div>
		<div class="row pt-2 pb-2"></div>
		<div class="row justify-content-center align-items-center d-none" id="div_row_commutes_list">
			<div class="col-12">
				<span class="badge badge-dark badge w-100" id="span_commutes_list_count">총 0개의 결과</span>
			</div>
			<div class="col-12">
				<div class="list-group" id="div_commutes_list">
					<!-- Append commutes list here -->
				</div>
			</div>
		</div>
		<div class="row pt-3 pb-3"></div>
	</div>
</body>
</html>