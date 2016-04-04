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
			<input type="date" data-date-inline-picker="true" />
			<p>Expnses size: ${expenses.size()}</p>
			<c:forEach var="expense" items="${expenses}">
				    Amount: <c:out value="${expense.amount}" />
				    Currency: <c:out value="${expense.currency}" />
				    Category: <c:out value="${expense.category.categoryName}" />
			</c:forEach>
			<h2>BGN ${moneyToSpend} Left to spend</h2>
			<img src="images/chart.jpg" width="550px" height="350px" />
			
		</div>
		<div id="column">
			<div class="subnav">
				<select class="form-control">
					<option value="">All acounts</option>
					<option value="">Savings</option>
					<option value="">Cash</option>
				</select>
				<ul>
					<li>
						<ul class="finance-operation-element">
							<li><a>March 20<span style="float: right;">BGN
										25.00</span></a></li>
							<li><a href="#">Home and Utilities...</a></li>
						</ul>
						<ul class="finance-operation-element">
							<li><a href="#">March 20<span style="float: right;">BGN
										25.00</span></a></li>
							<li><a href="#">Home and Utilities...</a></li>
						</ul>
						<ul class="finance-operation-element">
							<li><a href="#">March 20<span style="float: right;">BGN
										25.00</span></a></li>
							<li><a href="#">Home and Utilities...</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>