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
			<div class="month"> <h2>&lt; April &gt;</h2> </div>
			<div><a href="./addBudget" class="btn btn-default btn-lg"><spring:message code="budgets.addBudget" /></a></div>
			<c:forEach var="budget" items="${allBudgets}">				
				<div class="budget">
					<p>${budget.budgetType} - ${budget.repeatType}</p>
					<p>${budget.currency} ${budget.amount}</p>
					<div class="operations">
						<a href="./editBudget?id=${budget.id}" class="btn btn-info btn-md"><spring:message code="button.edit" /></a> 
						<a href="./verifyDeleteBudget?id=${budget.id}" class="btn btn-danger btn-md"><spring:message code="button.delete"/></a>
					</div>
				</div>
			</c:forEach>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>