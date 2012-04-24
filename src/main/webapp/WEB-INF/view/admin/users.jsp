<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User management page</title>
    </head>
    <body>
        <h1>Users</h1>
        <c:forEach var="user" items="${users}">
            <p><a href=<c:url value="/admin/users/${user.id}" />>${user.username}</a>: ${user.name}</p>
        </c:forEach>
        
    </body>
</html>
