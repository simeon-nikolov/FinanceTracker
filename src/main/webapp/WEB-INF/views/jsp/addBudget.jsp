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
		<div id="budget-content">
			<form:form action="addBudget" commandName="budgetViewModel">
				<fieldset>
					<legend>Add Budget: </legend>
					<table class="table">
						<tr>
							<td><label for="amount">Initial amount:</label></td>
							<td><form:input style="width: 100px;" id="amount" path="amount" type="number" step="0.01" value="" /></td>
							<td><form:errors path="amount" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="currency">Currency:</label></td>
							<td>
								<form:select id="currency" style="width: 100px; height: 25px;" path="currency">
									<form:options items="${currencies}" />
								</form:select>
							</td>
							<td><form:errors path="currency" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="budgetType">Type for:</label></td>
							<td>
								<form:select id="budgetType" style="width: 100px; height: 25px;" path="budgetType">
									<form:options items="${budgetTypes}" />
								</form:select>
							</td>
							<td><form:errors path="budgetType" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="repeatType">Repeat:</label></td>
							<td>
								<form:select id="repeatType" style="width: 100px; height: 25px;" path="repeatType">
									<form:options items="${repeatTypes}" />
								</form:select>
							</td>
							<td><form:errors path="repeatType" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="date-picker">Begin date:</label></td>
							<td><form:input class="date-picker" style="width: 100px;" path="beginDate" /></td>
							<td><form:errors path="beginDate" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="date-picker">End date:</label></td>
							<td><form:input class="date-picker" style="width: 100px;" path="endDate" /></td>
							<td><form:errors path="endDate" cssClass="error" /></td>
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