<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<html>

<head>
	<title>luv2code Company Home Page</title>

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
		Name: ${user.firstName} ${user.lastName}
		<br>
		Email: ${user.email}
		<br>
		Address: ${user.address}
		<br>
		Zip Code: ${user.zipcode}
		<br>
		Phone Number: ${user.phoneNumber}
	</p>
	
	<security:authorize access="hasRole('MANAGER')">
	
		<!-- Add a link to point to /leaders ... this is for the managers -->
		
		<p>
			<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
			(Only for Manager peeps)
		</p>

	</security:authorize>	
	
	
	<security:authorize access="hasRole('ADMIN')">  

		<!-- Add a link to point to /systems ... this is for the admins -->
		
		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
			(Only for Admin peeps)
		</p>
	
	</security:authorize>
	
	<hr>


	<!-- Add a edit user information button -->
	<form:form action="${pageContext.request.contextPath}/editUserInfo"
			   method="GET">

		<input type="submit" value="Edit Profile" />

	</form:form>
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	</form:form>


	
</body>

</html>









