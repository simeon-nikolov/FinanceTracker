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
						<legend><spring:message code="signUp.message"/></legend>
						<table>
							<tr>
								<td><label for="username"><spring:message code="profile.username"/></label></td>
								<td><form:input class="form-control" path="username" type="text" value="" /></td>
								<td><form:errors path="username" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="password"><spring:message code="signUp.password"/></label></td>
								<td><form:input class="form-control" path="password" type="password" /></td>
								<td><form:errors path="password" cssClass="error" /> <jstl:if
										test="${not empty passNotStrong}">
										<jstl:out value="${passNotStrong}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="password2"><spring:message code="signUp.confirmPassword"/></label></td>
								<td><form:input class="form-control" path="confirmPassword" type="password" /></td>
								<td><form:errors path="confirmPassword" cssClass="error" />
									<jstl:if test="${not empty errorMessage}">
										<jstl:out value="${errorMessage}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="email"><spring:message code="signUp.email"/></label></td>
								<td><form:input class="form-control" path="email" type="email" /></td>
								<td><form:errors path="email" cssClass="error" /></td>
							</tr>
						</table>
						<input class="btn btn-primary btn-md" type="submit" value="<spring:message code="button.signUp"/>" />
					</fieldset>
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>