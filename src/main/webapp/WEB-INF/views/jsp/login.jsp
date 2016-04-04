<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix = "form" uri = "http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>	
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>IT Talents - Finance Tracker</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="css/layout.css" type="text/css" />
</head>
<body id="top">
<div id="header">
  <div class="wrapper">
    <div class="fl_left">
      <h1><a href="./index">Finance Tracker</a></h1>
      <p>Free Website Template</p>
    </div>
    <div class="fl_right"> <a href="#"><img src="images/demo/468x60.gif" alt="" /></a> </div>
    <br class="clear" />
  </div>
</div>
<div id="topbar">
  <div class="wrapper">
    <div id="topnav">
      <ul>
        <li><a href="./index">Home</a></li>
        <li class="active"><a href="./login">Login</a></li>
		<li class="last"><a href="./signUp">Sign Up</a></li>
      </ul>
    </div>
    <br class="clear" />
  </div>
</div>
<div id="container">
  <div class="wrapper">
    <div id="content">
    	<div class="col-md-6 col-md-offset-2">	
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">
					Invalid UserName and Password.
				</div>
			</c:if>
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">
					You have been logged out.
				</div>
			</c:if>	
		</div>  
		<div id="login">
			<form:form id="loginForm" method="post" action="./login" modelAttribute="loginUser" 
				class="form-horizontal" role="form" cssStyle="width: 800px; margin: 0 auto;">
				<fieldset>
					<legend>Login</legend>
					<table>
						<tr>
							<td>
								<label for="username">Username:</label>
							</td>
							<td>
								 <input type="text" id="username" name="username" class="form-control" placeholder="Username" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="password">Password:</label>
							</td>
							<td>
								<input type="password" id="password" name="password" class="form-control" placeholder="Password" />
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
<div id="copyright">
  <div class="wrapper">
    <p class="fl_left">Copyright &copy; 2014 - All Rights Reserved - <a href="#">Domain Name</a></p>
    <p class="fl_right">Template by <a target="_blank" href="http://www.os-templates.com/" title="Free Website Templates">OS Templates</a></p>
    <br class="clear" />
  </div>
</div>
</body>
</html>