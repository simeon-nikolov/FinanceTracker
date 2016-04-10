<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="finance-info bordered">
	<div>
		<span class="date">
			<jstl:out value="${expense.date}"></jstl:out>
		</span>
		<span class="money-amount">
			<jstl:out value="${expense.userCurrency}"></jstl:out>
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${expense.userCurrencyAmount}" />
		</span>
	</div>
	<br class="clear" />
	<div></div>
	<div>
		<span class="category">
			<jstl:out value="${expense.category}"></jstl:out>
		</span>
		<span class="tags">
			<jstl:forEach var="tag" items="${expense.tags}">
				<jstl:out value="${tag}"></jstl:out>
			</jstl:forEach>
		</span>
		<span class="actual-amount">
			<jstl:out value="${expense.currency}"></jstl:out>
			<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${expense.amount}" />
		</span>
	</div>
	<br class="clear" />
	<div></div>
</div>