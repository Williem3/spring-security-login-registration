<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<head>
	<title>Pie Puller Home Page</title>

	<!-- link to css file in resources folder -->
	<link href="${pageContext.request.contextPath}/resources/css/navigation.css" rel="stylesheet" type="text/css"  >

</head>
<ul>
	<li><a class="active" href="home.jsp">Home</a></li>
	<li><a href="deals.jsp">News</a></li>
	<li><a href="contact.jsp">Contact</a></li>
	<li><a href="about.jsp">About</a></li>
</ul>
<body>
	<h2>Pie Puller Home Page</h2>
	<hr>
	
	<p>
		Welcome to Pie Puller, where you can find the best pizza deals around you!
	</p>
	
	<hr>

	<!-- display user name and role -->
	<p>
		User: <security:authentication property="principal.username" />
		<br><br>
		Role(s): <security:authentication property="principal.authorities" />
		<br><br>
		<!-- user.variable declared earlier -->
		ID: ${customer.id} ${employee.id}
		<br>
		Username: ${customer.userName} ${employee.userName}
		<br>
		Email: ${customer.email} ${employee.email}
		<br>
		Address: ${customer.address} ${employee.address}
		<br>
		Zip Code: ${customer.zipcode} ${employee.zipcode}
		<br>
		Phone Number: ${customer.phoneNumber} ${employee.phoneNumber}
	</p>

	<security:authorize access="hasRole('MANAGER')">

		<!-- Add a link to point to /leaders ... this is for the managers -->

		<p>
			<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
			(Only for Managers)
		</p>

	</security:authorize>


	<security:authorize access="hasRole('ADMIN')">

		<!-- Add a link to point to /systems ... this is for the admins -->

		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
			(Only for Admins)
		</p>

	</security:authorize>

	<security:authorize access="hasAnyRole('EMPLOYEE', 'ADMIN', 'MANAGER')">

		<!-- Add a link to point to /customer/list ... this is for the admins and managers to manage -->

		<p>
			<a href="${pageContext.request.contextPath}/customer/list">Customer List</a>
			(Only for Admins and Managers)
		</p>

	</security:authorize>

	<security:authorize access = "hasAnyRole('ADMIN', 'MANAGER')">
		<!-- Add a link to point to /employee/list, this will be accessible only by Managers and Admins -->

		<p>
			<a href="${pageContext.request.contextPath}/employee/list">Employee List</a>
		</p>
	</security:authorize>

	<hr>

	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	</form:form>



</body>

</html>









