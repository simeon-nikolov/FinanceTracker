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
			<div id="register">
				<form:form action="editAccount" commandName="accountViewModel">
					<fieldset>
						<legend>Edit account</legend>
						<table>
							<tr>
								<td><label for="title">Account title:</label></td>
								<td><form:input path="title" type="text" value="" /></td>
								<td><form:errors path="title" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="balance">Balance:</label></td>
								<td><form:input path="balance" type="number" value=""/></td>
								<td>
									<form:errors path="balance" cssClass="error" /> 
								</td>
							</tr>							
						</table>
						<input type="submit" value="Submit" />
					</fieldset>
				</form:form>				
			</div>
			<div>
				<p>This is the initial balance of this account.</p>
				<p>Beware that all expenses will be subtracted</p>
				<p>and all incomes will be added to this account</p>
			</div>			
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>