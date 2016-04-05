<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="signup-content">
			<div class="dberror">
				<jstl:if test="${not empty signUpError}">
					<jstl:out value="${signUpError}"></jstl:out>
				</jstl:if>
			</div>
			<div id="register">
				<form:form action="signUp" commandName="userViewModel">
					<fieldset>
						<legend>Sign up: </legend>
						<table>
							<tr>
								<td><label for="username">Username:</label></td>
								<td><form:input class="form-control" path="username" type="text" value="" /></td>
								<td><form:errors path="username" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="password">Password:</label></td>
								<td><form:input class="form-control" path="password" type="password" /></td>
								<td><form:errors path="password" cssClass="error" /> <jstl:if
										test="${not empty passNotStrong}">
										<jstl:out value="${passNotStrong}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="password2">Confirm password:</label></td>
								<td><form:input class="form-control" path="confirmPassword" type="password" /></td>
								<td><form:errors path="confirmPassword" cssClass="error" />
									<jstl:if test="${not empty errorMessage}">
										<jstl:out value="${errorMessage}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="email">E-mail:</label></td>
								<td><form:input class="form-control" path="email" type="email" /></td>
								<td><form:errors path="email" cssClass="error" /></td>
							</tr>
						</table>
						<input class="btn btn-primary btn-md" type="submit" value="Sign Up" />
					</fieldset>
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>