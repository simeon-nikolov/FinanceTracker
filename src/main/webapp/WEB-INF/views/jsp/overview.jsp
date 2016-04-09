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
				 	<a href="./previousMonth?view=overview">&lt; </a>
				 		 ${month}. ${year}
				 	 <a href="./nextMonth?view=overview">&gt;</a></h2> 
			 </div>
			<h2>BGN ${moneyToSpend} Left to spend</h2>
			<div id="chart" class="ct-chart ct-perfect-fourth"></div>
			<script>
				var chartData = <c:out value="${chartData}" escapeXml="false"></c:out>;
				//var chartData = [{name:"Expenses", data:[1,5,3]}, {name:"Incomes", data:[1,2,1]}];
				var dates = <c:out value="${dates}" escapeXml="false"></c:out>;
				draw3dGroupedColumn(chartData, dates);
			</script>
		</div>
		<div id="column">
			<div class="subnav">
				<%@include file="partials/accountsSelect.jsp"%>
				<c:forEach var="expense" items="${expenses}">				
					<%@include file="partials/expenseListTemplate.jsp"%>
				</c:forEach>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>