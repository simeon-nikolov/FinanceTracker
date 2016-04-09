<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="content">
			<div id="register">
				<form:form action="addAccount" commandName="accountViewModel">
					<fieldset>
						<legend><spring:message code="accounts.addAccountMessage"/></legend>
						<table>
							<tr>
								<td><label for="title"><spring:message code="accounts.accountTitleMessage"/></label></td>
								<td><form:input path="title" type="text" value="" /></td>
								<td><form:errors path="title" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="balance"><spring:message code="accounts.initialBalanceMessage"/></label></td>
								<td><form:input path="balance" type="text"/></td>
								<td><form:errors path="balance" cssClass="error" /> </td>
							</tr>							
						</table>
						<input type="submit" value="<spring:message code="button.add"/>" />
					</fieldset>
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>