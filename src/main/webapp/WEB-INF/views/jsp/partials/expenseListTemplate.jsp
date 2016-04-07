<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="finance-info bordered">
	<div>
		<p>
			<span class="date"> 
				<jstl:out value="${expense.date}"></jstl:out>
			</span> 
			<span class="money-amount"> 
				<jstl:out value="${expense.currency}"></jstl:out> 
				<jstl:out value="${expense.amount}"></jstl:out>				
			</span>
		</p>
	</div>
	<div>
		<p>
			<span class="category"> 
				<jstl:out value="${expense.category}"></jstl:out>
			</span> 
			<span class="tags">
				<jstl:forEach var="tag" items="${expense.tags}">
					<jstl:out value="${tag}"></jstl:out>
				</jstl:forEach>
			</span>
		</p>
	</div>
</div>