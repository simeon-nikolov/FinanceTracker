<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="content">
			<h4 class="alert alert-danger">Error: ${errorMessage}</h4>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>