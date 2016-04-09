<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div id="topbar">
	<div class="wrapper">
		<div id="topnav">
			<ul>
				<li><a href="./overview"><spring:message code="menu.overview"/></a></li>
				<li><a href="./allAccounts"><spring:message code="menu.accounts"/></a></li>
				<li><a href="./allExpenses"><spring:message code="menu.expenses"/></a></li>
				<li><a href="./allIncomes"><spring:message code="menu.incomes"/></a></li>
				<li><a href="./allBudgets"><spring:message code="menu.budgets"/></a></li>
				<li><a href="./export"><spring:message code="menu.export"/></a></li>				
				<li><a href="./logout"><spring:message code="menu.logout"/></a></li>
				<li><a href="./profile">${username}</a></li>
			</ul>
		</div>
		<br class="clear" />
	</div>
</div>