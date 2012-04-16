<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registration</title>
    </head>
    <body>
        <h1>Register as a new user</h1>
        <form:form commandName="userForm" action="${pageContext.request.contextPath}/registration" method = "POST">
            Name: <form:input path="name" /><form:errors path="name" /> <br />
            Username: <form:input path="username" /><form:errors path="username" /> <br />
            Password: <form:password path="password" /><form:errors path="password" /> <br />
            Password confirmation: <form:password path="confirmation" /><form:errors path="confirmation" /> <br />
            <input type="submit" />
        </form:form>
    </body>
</html>
