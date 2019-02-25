<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: anima
  Date: 2/19/2019
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>

    <style>
        .failed {
            color:red;
        }
    </style>
</head>
<body>

<!--    best practice is to use the Spring MVC form tag >form:form<   -->
<form:form action="${pageContext.request.contextPath}/authenticateTheUser" method="POST">
    <c:if test="${param.error != null}" >
        <i class="failed">Sorry! You entered an invalid username/password.</i>
    </c:if>
    <p>
    Username: <input type="text" name="username" />
    </p>

    <p>
    Password: <input type="password" name="password" />
    </p>

    <input type="submit" value="Login" />
</form:form>

</body>
</html>
