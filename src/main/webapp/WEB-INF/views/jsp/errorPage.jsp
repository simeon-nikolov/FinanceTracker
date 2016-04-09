<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="error-content">
			<h3 class="alert alert-danger"><spring:message code="error.firstRow"/></h3>
			<h3 class="alert alert-danger"><spring:message code="error.secondRow"/></h3>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>