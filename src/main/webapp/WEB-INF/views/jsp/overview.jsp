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
			<h2>BGN ${moneyToSpend} Left to spend</h2>
			<div class="ct-chart ct-perfect-fourth"></div>
			<script src="js/chartist.js"></script>
			<script>
				var data = {
						labels : ${categories},
						series : ${money}
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