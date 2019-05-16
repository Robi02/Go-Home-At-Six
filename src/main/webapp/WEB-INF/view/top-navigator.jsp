<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="row pt-1 pb-1 position-fixed w-100 bg-dark border border-secondary rounded-bottom">
	<div class="col-5 text-left">
		<button type="button" class="btn btn-outline-light">New</button>
		<button type="button" class="btn btn-outline-light">In</button>
		<button type="button" class="btn btn-outline-light d-none">Out</button>
	</div>
	<div class="col text-center border-left border-secondary">
		<div class="btn-group btn-group-toggle btn-block" data-toggle="buttons">
			<label class="btn btn-outline-primary active">
				<input type="radio" name="options" id="option1" autocomplete="off" checked>RC
			</label>
			<label class="btn btn-outline-info">
				<input type="radio" name="options" id="option2" autocomplete="off">LI
			</label>
			<label class="btn btn-outline-danger">
				<input type="radio" name="options" id="option3" autocomplete="off">ST
			</label>
		</div>
	</div>
</div>
<div class="row pt-5 pb-4"></div>