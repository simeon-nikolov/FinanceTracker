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
		<div class="profile-row">
			<div class="profile-left-column">
				<h4>Username: </h4>
			</div>
			<div class="profile-right-column">
				<h4><mark>${username}</mark></h4>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4>Change default currency </h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changeCurrency" commandName="changeCurrencyViewModel" method="POST">
					<form:select style="height: 33px;" id="currency" path="currency">
						<form:options items="${currencies}" />
					</form:select>
					<input type="submit" class="btn btn-default btn-md" value="Change currency" />
				</form:form>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4>Change password</h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changePassword" commandName="changePasswordViewModel">
					<div>
						<label for="old-password">Old password:</label>
						<form:input id="old-password" path="oldPassword" type="password" value="" />
						<form:errors path="oldPassword" cssClass="error" />
					</div>
					<div>
						<label for="new-password">New password:</label>
						<form:input id="new-password" path="newPassword" type="password" value="" />
						<form:errors path="newPassword" cssClass="error" />
					</div>
					<div>
						<label for="confirm-new-password">Confirm new password:</label>
						<form:input id="confirm-new-password" path="confirmNewPassword" type="password" value="" />
						<form:errors path="confirmNewPassword" cssClass="error" />
					</div>
					<input type="submit" class="btn btn-default btn-md" value="Change password" />
				</form:form>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4>Change e-mail</h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changeEmail" commandName="changeEmailViewModel">
					<div><h5>Current e-mail: <mark>${email}</mark></h5></div>
					<div>
						<label for="new-email">New e-mail:</label>
						<form:input id="new-email" path="newEmail" type="email" value="" />
						<form:errors path="newEmail" cssClass="error" />
					</div>
					<div>
						<label for="confirm-new-password">Confirm new e-mail:</label>
						<form:input id="confirm-new-password" path="confirmNewEmail" type="text" value="" />
						<form:errors path="confirmNewEmail" cssClass="error" />
					</div>
					<div>
						<label for="enter-password">Enter password:</label>
						<form:input id="enter-password" path="enterPassword" type="password" value="" />
						<form:errors path="enterPassword" cssClass="error" />
					</div>
					<input type="submit" class="btn btn-default btn-md" value="Change e-mail" />
				</form:form>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>