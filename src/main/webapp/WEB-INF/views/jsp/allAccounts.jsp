<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="content">
			<div class="subnav account-content">
				<c:forEach var="account" items="${accounts}">
					<div class="account-info">
						<span class="title">
						 	<c:out value="${account.title}"></c:out>
						</span>
						 <span>
						 	<c:out value="${account.currency}"></c:out>
						 	<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${account.balance}" />
						</span>
						<div>
							<a href="./editAccount?id=${account.id}" class="btn btn-info btn-xs"><spring:message code="accounts.editAccount"/></a>
							<a href="./verifyDeleteAccount?id=${account.id}" class="btn btn-danger btn-xs"><spring:message code="accounts.deleteAccount"/></a>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>
		<div id="column">
			<div class="subnav">
				<a href="./addAccount" class="btn btn-lg btn-default"><spring:message code="accounts.addAccount"/></a>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>