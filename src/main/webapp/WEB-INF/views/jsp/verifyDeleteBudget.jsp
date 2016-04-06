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
		<div id="budget-content">
			<div>
				<h3>
					<p>Are you sure you want to delete budget?</p>
					<p>${budgetViewModel.budgetType} - ${budgetViewModel.repeatType}</p>
					<p>${budgetViewModel.currency} ${budgetViewModel.amount}</p>
				</h3>
			</div>
			<div>
				<a href="./deleteBudget?id=${budgetViewModel.id}" class="btn btn-sm btn-default">Yes</a>
				<a href="./allBudgets" class="btn btn-sm btn-default">No</a>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>