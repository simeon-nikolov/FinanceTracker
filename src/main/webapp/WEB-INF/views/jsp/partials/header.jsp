<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page errorPage="./errorPage.jsp" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>	
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>IT Talents - Finance Tracker</title>
<link rel="stylesheet" href="css/layout.css" type="text/css" />
<link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
<sec:authorize access="isAuthenticated()">
<script src="./js/jquery-2.2.2.min.js" type="text/javascript"></script>
<script src="./js/jquery-ui.min.js" type="text/javascript"></script>
<script src="./js/custom.js" type="text/javascript"></script>
<link rel="stylesheet" href="./css/jquery-ui.min.css">
<link rel="stylesheet" href="./css/chartist.css">
</sec:authorize>
</head>
<body id="top">
	<div id="header">
		<div class="wrapper">
			<div class="fl_left">
				<h1>
					<a href="./index">Finance Tracker</a>
				</h1>
				<p>IT Talents Season 5 Final Project</p>
			</div>
			<div class="fl_right">
				<a href="#"><img src="images/demo/468x60.gif" alt="" /></a>
			</div>
			<br class="clear" />
		</div>
	</div>
	<sec:authorize access="isAuthenticated()">
		<%@include file="loggedInMenu.jsp"%>
	</sec:authorize>
	<sec:authorize access="not isAuthenticated()">
		<%@include file="menu.jsp"%>
	</sec:authorize>