<%-- 
    Document   : project
    Created on : Mar 3, 2012, 12:27:29 PM
    Author     : tonykovanen
--%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Project view</title>
    </head>
    <body>
        <h1>${project.name}</h1>
        <p>${project.description}</p>
        <h2>Change description</h2>
        <form:form commandName="projectform" action="${pageContext.request.contextPath}/customer/${project.id}" method="POST">
            Project name: <form:input path="name" /><form:errors path="name" /> <br />
            Project description: <form:input path="description" /><form:errors path="description" /> <br />
            <input type="submit"/>
        </form:form>
    </body>
</html>
