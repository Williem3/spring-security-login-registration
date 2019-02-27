<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>

<head>
	<title>Employee List</title>

	<!-- reference our style sheet -->

	<link type="text/css"

		  rel="stylesheet"

		  href="${pageContext.request.contextPath}/resources/css/style.css"/>

</head>

<body>

<div id="wrapper">
	<div id="header">
		<h2>ERM - Employee Relationship Manager</h2>
	</div>
</div>

<div id="container">

	<div id="content">

		<!-- put new button: Add Employee -->
		<form:form action="${pageContext.request.contextPath}/employee/showRegistrationForm"
				   method="GET">

			<input type="submit" value="Add Employee" />

		</form:form>

		<!--  add our html table here -->
		<table>
			<tr>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Username</th>
				<th>Email</th>
				<th>Phone Number</th>
				<th>Address</th>
				<th>Zipcode</th>
				<th>Actions</th>
			</tr>

			<!-- loop over and print our customers -->
			<c:forEach var="tempEmployee" items="${employees}">

				<!-- construct an "update" link with Employee id -->
				<c:url var="updateLink" value="/employee/showFormForUpdate">
					<c:param name="employeeId" value="${tempEmployee.id}" />
				</c:url>

				<!-- construct a "delete" link with employee id -->
				<c:url var="deleteLink" value="/employee/delete">
					<c:param name="employeeId" value="${tempEmployee.id}" />
				</c:url>


				<tr>
					<td> ${tempEmployee.firstName} </td>
					<td> ${tempEmployee.lastName} </td>
					<td> ${tempEmployee.userName} </td>
					<td> ${tempEmployee.email} </td>
					<td> ${tempEmployee.phoneNumber} </td>
					<td> ${tempEmployee.address} </td>
					<td> ${tempEmployee.zipcode} </td>
					<td>
						<a href="${updateLink}">Update</a>
						|
						<a href="${deleteLink}"
						onclick="if (!(confirm('Are you sure you want to delete this customer?'))) return false ">Delete</a>
					</td>
				</tr>

			</c:forEach>

		</table>

		<!-- Add a logout button -->
		<form:form action="${pageContext.request.contextPath}/logout"
				   method="POST">

			<input type="submit" value="Logout" />

		</form:form>

	</div>

</div>


</body>

</html>


















