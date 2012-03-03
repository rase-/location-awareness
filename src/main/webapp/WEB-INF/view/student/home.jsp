<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le home de student</title>
    </head>
    <body>
        <h1>Projects</h1>
        <c:forEach var="project" items="${projects}">
            <a href="${pageContext.request.contextPath}/student/${project.id}">${project.name}, likes: ${project.likes}</a> <a href="${pageContext.request.contextPath}/student/likes/${project.id}">+</a><br />
        </c:forEach>
        <p><a href="<c:url value="/j_spring_security_logout" />" > Logout</a></p>
    </body>
</html>
