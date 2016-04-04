<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="partials/header.jsp"%>
<div id="container">
	<div class="wrapper">
		<div id="content">
			<div id="register">
				<form:form action="signUp" commandName="userViewModel">
					<fieldset>
						<legend>Sign up: </legend>
						<table>
							<tr>
								<td><label for="username">Username:</label></td>
								<td><form:input path="username" type="text" value="" /></td>
								<td><form:errors path="username" cssClass="error" /></td>
							</tr>
							<tr>
								<td><label for="password">Password:</label></td>
								<td><form:input path="password" type="password" /></td>
								<td><form:errors path="password" cssClass="error" /> <jstl:if
										test="${not empty passNotStrong}">
										<jstl:out value="${passNotStrong}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="password2">Confirm password:</label></td>
								<td><form:input path="confirmPassword" type="password" /></td>
								<td><form:errors path="confirmPassword" cssClass="error" />
									<jstl:if test="${not empty errorMessage}">
										<jstl:out value="${errorMessage}"></jstl:out>
									</jstl:if></td>
							</tr>
							<tr>
								<td><label for="email">E-mail:</label></td>
								<td><form:input path="email" type="email" /></td>
								<td><form:errors path="email" cssClass="error" /></td>
							</tr>
						</table>
						<input type="submit" value="Submit" />
					</fieldset>
				</form:form>
			</div>
<<<<<<< HEAD
=======
			<div class="fl_right">
				<a href="#"><img src="images/demo/468x60.gif" alt="" /></a>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div id="topbar">
		<div class="wrapper">
			<div id="topnav">
				<ul>
					<li><a href="./index">Home</a></li>
					<li><a href="./login">Login</a></li>
					<li class="active right"><a href="./signUp">Sign Up</a></li>
				</ul>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div id="container">
		<div class="wrapper">
			<div id="content">
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
									<td><form:input path="username" type="text" value="" /></td>
									<td><form:errors path="username" cssClass="error" /></td>
								</tr>
								<tr>
									<td><label for="password">Password:</label></td>
									<td><form:input path="password" type="password" /></td>
									<td><form:errors path="password" cssClass="error" />
										<jstl:if test="${not empty passNotStrong}">
											<jstl:out value="${passNotStrong}"></jstl:out>
										</jstl:if>	
									</td>
								</tr>
								<tr>
									<td><label for="password2">Confirm password:</label></td>
									<td><form:input path="confirmPassword" type="password" /></td>
									<td><form:errors path="confirmPassword" cssClass="error" />
										<jstl:if test="${not empty errorMessage}">
											<jstl:out value="${errorMessage}"></jstl:out>
										</jstl:if></td>
								</tr>
								<tr>
									<td><label for="email">E-mail:</label></td>
									<td><form:input path="email" type="email" /></td>
									<td><form:errors path="email" cssClass="error" /></td>
								</tr>
							</table>
							<input type="submit" value="Submit" />
						</fieldset>
					</form:form>
				</div>
			</div>
			<br class="clear" />
		</div>
	</div>
	<div id="copyright">
		<div class="wrapper">
			<p class="fl_left">
				Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain
					Name</a>
			</p>
			<p class="fl_right">
				Template by <a target="_blank" href="http://www.os-templates.com/"
					title="Free Website Templates">OS Templates</a>
			</p>
			<br class="clear" />
>>>>>>> f2f26ba132ccebeff0b94bf420ed38d072d6ac16
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>