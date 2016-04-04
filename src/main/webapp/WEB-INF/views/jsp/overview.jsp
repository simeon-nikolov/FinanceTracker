<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>	
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>IT Talents - Finance Tracker</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/layout.css" type="text/css" />
</head>
<body id="top">
	<div id="header">
		<div class="wrapper">
			<div class="fl_left">
				<h1>
					<a href="./overview">Finance Tracker</a>
				</h1>
				<p>Free Website Template</p>
			</div>
			<div class="fl_right">
				<a href="#"><img src="images/demo/468x60.gif" alt="" /></a>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div id="topbar">
		<div class="wrapper">
			<div id="topnav">
				<ul>
					<li class="active"><a href="./overview">Overview</a></li>
					<li><a href="./profile">${username}</a></li>

				</ul>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div id="container">
		<div class="wrapper">
			<div id="content">
				<input type="date" data-date-inline-picker="true" />
				<p>Expnses size: ${expenses.size()}</p>
				<c:forEach var="expense" items="${expenses}">
				    Amount: <c:out value="${expense.amount}"/>
				    Currency: <c:out value="${expense.currency}"/>
				    Category: <c:out value="${expense.category.categoryName}"/>
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
	<div id="copyright">
		<div class="wrapper">
			<p class="fl_left">
				Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain
					Name</a>
			</p>
			<p class="fl_right">
				Template by <a target="_blank" href="http://www.os-templates.com/"
					title="Free Website Templates">OS Templates</a>
			</p>
			<br class="clear" />
		</div>
	</div>
</body>
</html>