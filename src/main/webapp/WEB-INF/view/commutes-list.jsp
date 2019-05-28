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
		<!-- Search period (from) -->
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
		<!-- Search period (to) -->
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
		<!-- Search button -->
		<div class="row justify-content-center align-items-center">
			<div class="col-12">
				<button type="button" class="btn btn-primary btn-lg btn-block" id="btn_search_commutes">검색</button>
			</div>
		</div>
		<div class="row pt-2 pb-2"></div>
		<!-- Search list -->
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
	<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="h5_center_modal_title" aria-hidden="true">
		<input type="hidden" id="input_modal_commutes_id">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="h5_center_modal_title">Title</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
			</div>	
			<div class="modal-body">
				<div class="container-fluid">
					<div class="row">
						<label for="recipient-name" class="col-form-label">출근시간:</label>
						<div class="form-group">
							<div class="col">
								<div class="input-group mb-3">
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_year">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">년</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_month">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">월</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_date">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">일</span>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="input-group mb-3">
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_hour">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">시</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_minute">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">분</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_in_second">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">초</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<label for="recipient-name" class="col-form-label">퇴근시간:</label>
						<div class="form-group">
							<div class="col">
								<div class="input-group mb-3">
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_year">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">년</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_month">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">월</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_date">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">일</span>
									</div>
								</div>
							</div>
							<div class="col">
								<div class="input-group mb-3">
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_hour">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">시</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_minute">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">분</span>
									</div>
									<input type="number" class="form-control" aria-label="Sizing example input" aria-describedby="inputGroup-sizing-default" id="modal_input_check_out_second">
									<div class="input-group-prepend">
										<span class="input-group-text" id="inputGroup-sizing-default">초</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-12">
							<label for="recipient-name" class="col-form-label">회사명:</label>
							<input type="text" class="form-control" id="input_commutes_modal_company_name">
						</div>
						<div class="col-12">
							<label for="message-text" class="col-form-label">메모:</label>
							<textarea class="form-control" id="input_commutes_modal_memo" placeholder="특이사항 기입"></textarea>
						</div>
					</div>
				</div>
      		</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteCommutesButtonClick()">삭제</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="modifyCommutesButtonClick()">수정</button>
			</div>
			</div>
		</div>
	</div>
</body>
</html>