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
			<div class="col-md-6 col-md-offset-2">
				<c:if test="${param.error != null}">
					<div class="alert alert-danger">Invalid Username or Password.
					</div>
				</c:if>
				<c:if test="${param.logout != null}">
					<div class="alert alert-success">You have been logged out.</div>
				</c:if>
			</div>
			<div id="login">
				<form:form id="loginForm" method="post" action="./login"
					class="form-horizontal" commandName="loginUserViewModel">
					<fieldset>
						<legend>Login</legend>
						<table>
							<tr>
								<td><label for="username">Username:</label></td>
								<td><input path="username" type="text" id="username"
									name="username" class="form-control" placeholder="Username" />
								</td>
							</tr>
							<tr>
								<td><label for="password">Password:</label></td>
								<td><input path="password" type="password" id="password"
									name="password" class="form-control" placeholder="Password" />
								</td>
							</tr>
						</table>
						<input type="submit" value="Login" />
					</fieldset>
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>