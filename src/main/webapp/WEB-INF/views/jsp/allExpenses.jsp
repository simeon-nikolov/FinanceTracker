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
			<div class="month">
				 <h2>
				 	<a href="./previousMonth?view=allExpenses">&lt; </a>
				 		 ${month}. ${year}
				 	 <a href="./nextMonth?view=allExpenses">&gt;</a></h2> 
			 </div>
			<div id="chart" class="ct-chart ct-perfect-fourth"></div>
			<script>
				var chartData = <c:out value="${chartData}" escapeXml="false"></c:out>;
				draw3dDonut("expenses", chartData);
			</script>
		</div>
		<div id="column">
			<div class="subnav">
				<%@include file="partials/accountsSelect.jsp"%>
				<a href="./addExpense" class="btn btn-lg btn-default"><spring:message code="expenses.addExpense" /></a>
				<div id="expenses">
					<c:forEach var="expense" items="${expenses}">				
						<%@include file="partials/expenseListTemplate.jsp"%>
						<div class="operations">
							<a href="./editExpense?id=${expense.id}" class="btn btn-info btn-xs"><spring:message code="button.edit" /></a> 
							<a href="./verifyDeleteExpense?id=${expense.id}" class="btn btn-danger btn-xs"><spring:message code="button.delete"/></a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>