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
				var data = {
						labels :<c:out value="${categories}" escapeXml="false"></c:out>,
						series : <c:out value="${expensesAmounts}"></c:out>
				};

				var options = {
					labelInterpolationFnc : function(value) {
						return value[0]
					}
				};

				var responsiveOptions = [ [ 'screen and (min-width: 640px)', {
					chartPadding : 30,
					labelOffset : 100,
					labelDirection : 'explode',
					labelInterpolationFnc : function(value) {
						return value;
					}
				} ], [ 'screen and (min-width: 1024px)', {
					labelOffset : 80,
					chartPadding : 20
				} ] ];

				new Chartist.Pie('.ct-chart', data, options, responsiveOptions);
			</script>
		</div>
		<div id="column">
			<div class="subnav">
				<%@include file="partials/accountsSelect.jsp"%>
				<a href="./addExpense" class="btn btn-lg btn-default">+ Add Expense</a>
				<c:forEach var="expense" items="${expenses}">				
					<%@include file="partials/expenseListTemplate.jsp"%>
				</c:forEach>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>