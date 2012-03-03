<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Le home de customer.</title>
    </head>
    <body>
        <h1>Add a project</h1>
        <form:form commandName="projectform" action="${pageContext.request.contextPath}/customer/home" method="POST">
            Project name: <form:input path="name" /><form:errors path="name" /> <br />
            Project description: <form:input path="description" /><form:errors path="description" /> <br />
            <input type="submit"/>
        </form:form>
        <h1>My Projects</h1>
        <c:forEach var="project" items="${projects}">
            <a href="${pageContext.request.contextPath}/customer/${project.id}">${project.name}, likes: ${project.likes}</a> <br />
        </c:forEach>
        <p><a href="<c:url value="/j_spring_security_logout" />" > Logout</a></p>
    </body>
</html>
