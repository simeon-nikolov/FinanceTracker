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
				<h4><spring:message code="profile.username"/></h4>
			</div>
			<div class="profile-right-column">
				<h4><mark>${username}</mark></h4>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4><spring:message code="profile.changeCurrency"/></h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changeCurrency" commandName="changeCurrencyViewModel" method="POST">
					<form:select style="height: 33px;" id="currency" path="currency">
						<form:options items="${currencies}" />
					</form:select>
					<input type="submit" class="btn btn-default btn-md" value="<spring:message code="profile.changeCurrencyButton"/>" />
				</form:form>
				<c:if test="${param.changeCurrency != null}">
					<div class="alert alert-success">Currency successfully changed from ${param.c1} to ${param.c2}.</div>
				</c:if>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4><spring:message code="profile.changePassword"/></h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changePassword" commandName="changePasswordViewModel">
					<table style="text-align:right; margin-left:25%;">
						<tr>
							<td><label for="old-password"><spring:message code="profile.oldPassword"/></label></td>
							<td><form:input id="old-password" path="oldPassword" type="password" value="" /></td>
							<td><form:errors path="oldPassword" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="new-password"><spring:message code="profile.newPassword"/></label></td>
							<td><form:input id="new-password" path="newPassword" type="password" value="" /></td>
							<td><form:errors path="newPassword" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="confirm-new-password"><spring:message code="profile.confirmNewPassword"/></label></td>
							<td><form:input id="confirm-new-password" path="confirmNewPassword" type="password" value="" /></td>
							<td><form:errors path="confirmNewPassword" cssClass="error" /></td>
						</tr>
						<tr>
							<td colspan="3"><input type="submit" class="btn btn-default btn-md" value="<spring:message code="profile.changePassword"/>" /></td>
						</tr>
					</table>
				</form:form>
				<c:if test="${param.changePassowrd != null}">
					<div class="alert alert-success">Password successfully changed!</div>
				</c:if>
				<c:if test="${param.passwordsMatchError != null}">
					<div class="alert alert-danger">Passwords do not match!</div>
				</c:if>
				<c:if test="${param.passwordIncorrect != null}">
					<div class="alert alert-danger">Password is incorrect!</div>
				</c:if>
			</div>
		</div>
		<hr class="clear" />
		<div class="profile-row">
			<div class="profile-left-column">
				<h4><spring:message code="profile.changeEmail"/></h4>
			</div>
			<div class="profile-right-column">
				<form:form action="changeEmail" commandName="changeEmailViewModel">
					<table style="text-align:right; margin-left:25%;">
						<tr><td colspan="3"><h5><spring:message code="profile.currentEmail"/><mark>${email}</mark></h5></td></tr>
						<tr>
							<td><label for="new-email"><spring:message code="profile.newEmail"/></label></td>
							<td><form:input id="new-email" path="newEmail" type="email" value="" /></td>
							<td><form:errors path="newEmail" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="confirm-new-password"><spring:message code="profile.confirmNewEmail"/></label></td>
							<td><form:input id="confirm-new-password" path="confirmNewEmail" type="email" value="" /></td>
							<td><form:errors path="confirmNewEmail" cssClass="error" /></td>
						</tr>
						<tr>
							<td><label for="enter-password"><spring:message code="profile.enterPassword"/></label></td>
							<td><form:input id="enter-password" path="enterPassword" type="password" value="" /></td>
							<td><form:errors path="enterPassword" cssClass="error" /></td>
						</tr>
						<tr><td colspan="3"><input type="submit" class="btn btn-default btn-md" value="<spring:message code="profile.changeEmail"/>" /></td></tr>
					</table>
				</form:form>
				<c:if test="${param.changeEmail != null}">
					<div class="alert alert-success">E-mail successfully changed!</div>
				</c:if>
				<c:if test="${param.emailMatchError != null}">
					<div class="alert alert-danger">E-mails do not match!</div>
				</c:if>
				<c:if test="${param.passwordEmailIncorrect != null}">
					<div class="alert alert-danger">Password is incorrect!</div>
				</c:if>
			</div>
		</div>
		<br class="clear" />
	</div>
</div>
<%@include file="partials/footer.jsp"%>