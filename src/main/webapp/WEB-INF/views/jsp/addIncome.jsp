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
			<form:form action="addIncome" commandName="incomeViewModel">
				<fieldset>
					<legend>Add Income: </legend>
					<table class="table">
						<tr>
							<td><label for="amount">Amount:</label></td>
							<td><form:input style="width: 100px;" id="amount"
									path="amount" type="number" step="0.01" value="" /></td>
							<td><form:errors path="amount" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="currency">Currency:</label></td>
							<td><form:select id="currency"
									style="width: 100px; height: 25px;" path="currency">
									<form:options items="${allCurrencies}" />
								</form:select></td>
							<td><form:errors path="currency" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="account">For account:</label></td>
							<td><form:select id="account"
									style="width: 100px; height: 25px;" path="account">
									<form:options items="${allAccounts}" />
								</form:select></td>
							<td><form:errors path="account" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="category">Category:</label></td>
							<td><form:select id="category"
									style="width: 100px; height: 25px;" path="category">
									<form:options items="${allCategories}" />
								</form:select></td>
							<td><form:errors path="category" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="date-picker">Date:</label></td>
							<td><form:input id="date-picker" style="width: 100px;"
									path="date" /></td>
							<td><form:errors path="date" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="description">Description:</label></td>
							<td><form:textarea id="description" path="description"></form:textarea></td>
							<td><form:errors path="description" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="repeatType">Repeat:</label></td>
							<td><form:select id="repeatType"
									style="width: 100px; height: 25px;" path="repeatType">
									<form:options items="${allRepeatTypes}" />
								</form:select></td>
							<td><form:errors path="repeatType" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label>Tags:</label></td>
							<td><c:forEach var="tag" items="${allTags}">
									<form:checkbox id="tag${tag}" path="tags" value="${tag}"></form:checkbox>
									<label for="tag${tag}">${tag}</label>
								</c:forEach></td>
							<td><form:errors path="tags" cssClass="error" /></td>
						</tr>
					</table>
					<input type="submit" class="btn btn-primary btn-md" value="Add" />
				</fieldset>
			</form:form>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>