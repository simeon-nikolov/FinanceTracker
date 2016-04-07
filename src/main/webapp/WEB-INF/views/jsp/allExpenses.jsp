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
			<div class="month"> <h2>&lt; April &gt;</h2> </div>
			<div class="ct-chart ct-perfect-fourth"></div>
			<script src="js/chartist.js"></script>
			<script>
				drawPieGraphic(<c:out value="${categories}" escapeXml="false"></c:out>, <c:out value="${expensesAmounts}"></c:out>);
			</script>
		</div>
		<div id="column">
			<div class="subnav">
				<%@include file="partials/accountsSelect.jsp"%>
				<a href="./addExpense" class="btn btn-lg btn-default">+ Add Expense</a>
				<div id="expenses">
					<c:forEach var="expense" items="${expenses}">				
						<%@include file="partials/expenseListTemplate.jsp"%>
						<div class="operations">
							<a href="./editExpense?id=${expense.id}" class="btn btn-info btn-xs">Edit</a> 
							<a href="./verifyDeleteExpense?id=${expense.id}" class="btn btn-danger btn-xs">Delete</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>