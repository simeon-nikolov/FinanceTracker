<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<c:if test="${not empty expenses}">
	<c:set var="financeOperationType" scope="session" value="expenses"></c:set>
</c:if>
<c:if test="${not empty incomes}">
	<c:set var="financeOperationType" scope="session" value="incomes"></c:set>
</c:if>
<select class="form-control" id="account" name="select" onchange="loadFinanceOperationsData('${financeOperationType}')">
	<option value="">All accounts</option>
	<c:forEach var="acc" items="${accounts}">
		<option value="${acc.title}"><c:out value="${acc.title}"></c:out></option>
	</c:forEach>	
</select>