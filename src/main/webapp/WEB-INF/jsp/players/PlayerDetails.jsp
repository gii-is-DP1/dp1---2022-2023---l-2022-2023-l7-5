<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="players">

	<h2>Player Information</h2>
	
	<table class="table table-striped">
		<tr>
			<th>Username</th>
			<td><b><c:out value="${user.username}"/></b></td>
		</tr>
		<tr>
			<th>Email</th>
			<td><c:out value="${user.email}"/></td>
		</tr>
	</table>
	
	<spring:url value="{username}/edit" var="editUrl">
		<spring:param name="username" value="${user.username}"/>
	</spring:url>
	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit User</a>
	
	<br/>
	<br/>
	<br/>
	<h2>Stadistics</h2>


</petclinic:layout>