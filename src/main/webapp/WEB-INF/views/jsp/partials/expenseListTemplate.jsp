<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<p>
		<jstl:out value="${expense.date}"></jstl:out>
		<jstl:out value="${expense.amount}"></jstl:out>
		<jstl:out value="${expense.currency}"></jstl:out>
	</p>
</div>	
<div>
	<p>
		<jstl:out value="${expense.category.categoryName}"></jstl:out>
		<jstl:forEach var="tag" items="${expense.tags}">
			<jstl:out value="tag.tagName"></jstl:out>
		</jstl:forEach>
	</p>
</div>