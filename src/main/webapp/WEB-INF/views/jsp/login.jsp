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
		<div id="login-content">
			<div>
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">Invalid Username or Password.</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">You have been logged out.</div>
				</c:if>
			</div>

			<div id="login">
				<form:form id="loginForm" method="post" action="./login"
					class="form-horizontal" commandName="loginUserViewModel">
					<fieldset>
						<legend><spring:message code="login.message"/></legend>
						<table>
							<tr>
								<td><label for="username"><spring:message code="profile.username"/></label></td>
								<td><input path="username" type="text" id="username"
									name="username" class="form-control" placeholder="Username" />
								</td>
							</tr>
							<tr>
								<td><label for="password"><spring:message code="signUp.password"/></label></td>
								<td><input path="password" type="password" id="password"
									name="password" class="form-control" placeholder="Password" />
								</td>
							</tr>
						</table>
						<input class="btn btn-primary btn-md" type="submit" value="<spring:message code="button.login"/>" />
					</fieldset>
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>