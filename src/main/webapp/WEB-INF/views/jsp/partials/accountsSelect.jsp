<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<select class="form-control" id="account" name="select" onchange="loadExpenses()">
	<option value="all">All accounts</option>
	<c:forEach var="acc" items="${accounts}">
		<option value="${acc.title}"><c:out value="${acc.title}"></c:out></option>
	</c:forEach>		
</select>