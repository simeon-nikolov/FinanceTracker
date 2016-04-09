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
				 	<a href="./previousMonth?view=allIncomes">&lt; </a>
				 		 ${month}. ${year}
				 	 <a href="./nextMonth?view=allIncomes">&gt;</a></h2> 
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
				<a href="./addIncome" class="btn btn-lg btn-default"><spring:message code="incomes.addIncome" /></a>
				<div id="incomes">
					<c:forEach var="income" items="${incomes}">
						<%@include file="partials/incomesListTemplate.jsp"%>
						<a href="./editIncome?id=${income.id}" class="btn btn-info btn-xs"><spring:message code="button.edit" /></a> 
						<a href="./verifyDeleteIncome?id=${income.id}" class="btn btn-danger btn-xs"><spring:message code="button.delete"/></a>
					</c:forEach>
				</div>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>