<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="finance-info bordered">
	<div>
		<p>
			<span class="date"> 
				<jstl:out value="${income.date}"></jstl:out>
			</span> 
			<span class="money-amount"> 
				<jstl:out value="${income.currency}"></jstl:out> 
				<jstl:out value="${income.amount / 100.0}"></jstl:out>
			</span>
		</p>
	</div>
	<div>
		<p>
			<span class="category"> 
				<jstl:out value="${income.category.categoryName}"></jstl:out>
			</span> 
			<span class="tags">
				<jstl:forEach var="tag" items="${income.tags}">
					<jstl:out value="${tag.tagName}"></jstl:out>
				</jstl:forEach>
			</span>
		</p>
	</div>
</div>