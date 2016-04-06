<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="content">
			<div>
				<h3>
					Are you sure you want to delete Income made on ${incomeDate} for ${incomeAmount} ?
				</h3>
			</div>
			<div>
				<a href="./deleteIncome?id=${incomeId}" class="btn btn-sm btn-default">Yes</a>
				<a href="./allIncomes" class="btn btn-sm btn-default">No</a>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>