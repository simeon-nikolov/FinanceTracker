<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>IT Talents - Finance Tracker</title>
<link rel="stylesheet" href="css/layout.css" type="text/css" />
</head>
<body id="top">
	<div id="header">
		<div class="wrapper">
			<div class="fl_left">
				<h1>
					<a href="./index">Finance Tracker</a>
				</h1>
				<p>Free Website Template</p>
			</div>
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
		</div>
	</div>
</body>
</html>