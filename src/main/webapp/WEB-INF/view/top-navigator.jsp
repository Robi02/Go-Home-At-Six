<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="row pt-1 pb-1 position-fixed w-100 bg-dark rounded-bottom" style="z-index: 1">
	<div class="col-5 text-left">
		<button type="button" class="btn btn-outline-light d-none" id="button_navi_join">New</button>
		<button type="button" class="btn btn-outline-light d-none" id="button_navi_login">In</button>
		<button type="button" class="btn btn-outline-light d-none" id="button_navi_logout">Logout</button>
	</div>
	<div class="col text-center border-left border-secondary" id="div_navi">
		<div class="btn-group btn-group-toggle btn-block" data-toggle="buttons" id="div_navi_radio_btn">
			<label class="btn btn-outline-primary active">
				<input type="radio" name="options" id="input_record" value="record" autocomplete="off" checked>RC
			</label>
			<label class="btn btn-outline-info">
				<input type="radio" name="options" id="input_list" value="list" autocomplete="off">LI
			</label>
			<label class="btn btn-outline-danger">
				<input type="radio" name="options" id="input_statistics" value="statistics" autocomplete="off">ST
			</label>
		</div>
	</div>
</div>
<div class="row pt-5 pb-4"></div>