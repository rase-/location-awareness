<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User information</title>
    </head>
    <body>
        <nav>
            <a href=<c:url value="/home" />>Site Home</a>
            <a href=<c:url value="/admin/home" />>Admin Home</a>
            <a href=<c:url value="/admin/places" />>Places</a>
            <a href=<c:url value="/admin/users" />>Users</a>
            <a href=<c:url value="/j_spring_security_logout" />>Logout</a>
        </nav>
        <h1>${user.name}</h1>
        <p>Name: ${user.name}</p>
        <h2>Roles</h2>
        <c:forEach var="role" items="${user.roles}">
            ${role.rolename} <br />
        </c:forEach>
        <h2>Friends</h2>
        <p>
            <c:forEach var="friendsFriend" items="${user.friends}">
                Username: ${friendsFriend.username}, name: ${friendsFriend.name} <br />
            </c:forEach>
        </p>
        <form:form commandName="delete" action="${pageContext.request.contextPath}/admin/users/${user.id}" method="DELETE">
            <input type="submit" value="Delete User" />
        </form:form>
        <c:if test="${not user.admin}">
        <form:form commandName="post" action="${pageContext.request.contextPath}/admin/users/${user.id}" method="POST">
            <input type="submit" value="Promote as admin" />
        </form:form>
        </c:if>

    </body>

</html>
