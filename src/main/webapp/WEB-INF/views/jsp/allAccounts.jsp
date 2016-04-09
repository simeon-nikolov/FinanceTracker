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
			<div class="subnav">
				<c:forEach var="account" items="${accounts}">
					<div class="finance-info bordered">
						<p>
							<span class="date">
							 	<c:out value="${account.title}"></c:out>
							</span>
							 <span class="money-amount">
							 	<c:out value="${account.currency}"></c:out>
							 	 <c:out value="${account.balance}"></c:out>
							</span>
						</p>
					</div>
					<div>
						<a href="./editAccount?id=${account.id}" class="btn btn-info btn-xs"><spring:message code="accounts.editAccount"/></a>
						<a href="./verifyDeleteAccount?id=${account.id}" class="btn btn-danger btn-xs"><spring:message code="accounts.deleteAccount"/></a>
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