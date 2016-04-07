<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="finance-info bordered">
	<div>
		<p>
			<span class="date"> 
				<jstl:out value="${income.date}"></jstl:out>
			</span> 
			<span class="money-amount"> 
				<jstl:out value="${income.currency}"></jstl:out> 
				<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${income.amount}" />
			</span>
		</p>
	</div>
	<div>
		<p>
			<span class="category"> 
				<jstl:out value="${income.category}"></jstl:out>
			</span> 
			<span class="tags">
				<jstl:forEach var="tag" items="${income.tags}">
					<jstl:out value="${tag}"></jstl:out>
				</jstl:forEach>
			</span>
		</p>
	</div>
</div>